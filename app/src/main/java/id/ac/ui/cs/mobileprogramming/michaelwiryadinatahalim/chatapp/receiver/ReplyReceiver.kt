package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.receiver

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.NotificationModel
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.ReplyMessage
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IMessageRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.createNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@AndroidEntryPoint
class ReplyReceiver : BroadcastReceiver() {

    @Inject
    lateinit var messageRepository: IMessageRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && context != null) {
            val replyText = getMessageText(intent).toString()
            val data = intent.getParcelableExtra<NotificationModel>(
                "id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.notification.data")!!
            Log.d(TAG, replyText)
            val timestamp = Instant.now().epochSecond
            data.repliedMessage.add(ReplyMessage(replyText, timestamp))
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    messageRepository.sendMessage(data.roomUid, replyText, data.senderUid, timestamp)
                    return@launch
                } catch (e: Exception) {
                    Log.e(TAG, e.message?:"")
                }
            }
            val notification = createNotificationReply(
                context,
                data
            )
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            NotificationManagerCompat.from(context).apply {
                notificationManager.notify(data.roomUid.toInt(), notification)
            }
        }
    }


    companion object {
        const val KEY_TEXT_REPLY = "key_text_reply_message"
        const val TAG = "REPLY RECEIVER"
    }

    private fun getMessageText(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT_REPLY)
    }

    private fun createNotificationReply(
        context: Context,
        data: NotificationModel
    ): Notification {
        val currentUserName = Firebase.auth.currentUser?.displayName
        val replier = Person.Builder().setName(currentUserName).build()
        val senderName = data.senderName
        val style = NotificationCompat.MessagingStyle(
            Person.Builder().setName(senderName)
                .build()
        )
            .addMessage(
                data.message,
                data.timestamp,
                Person.Builder().setName(senderName).build()
            )
        for (reply in data.repliedMessage) {
            style.addMessage(reply.message, reply.timestamp, replier)
        }
       return createNotification(
           context, style, data
       )
    }
}
