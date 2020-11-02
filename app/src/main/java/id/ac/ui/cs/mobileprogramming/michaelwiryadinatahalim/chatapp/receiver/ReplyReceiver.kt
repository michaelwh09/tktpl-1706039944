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
            Log.d(TAG, replyText)
            val timestamp = Instant.now().epochSecond
            val roomUid = intent.getLongExtra("roomUid", -1)
            val senderUid = intent.getStringExtra("senderUid")?:""
            val repliedTexts = intent.getStringArrayListExtra("replied")
            val repliedTimestamp = intent.getStringArrayListExtra("repliedTimestamp")
            repliedTexts?.add(replyText)
            repliedTimestamp?.add(timestamp.toString())
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    messageRepository.sendMessage(roomUid, replyText, senderUid, timestamp)
                    return@launch
                } catch (e: Exception) {
                    Log.e(TAG, e.message?:"")
                }
            }
            val notification = createNotificationReply(
                context,
                intent.getLongExtra("roomUid", -1),
                intent.getStringExtra("message")?:"",
                intent.getLongExtra("timestamp", -1),
                intent.getStringExtra("senderName")?:"",
                timestamp,
                replyText,
                senderUid,
                repliedTexts,
                repliedTimestamp
            )
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            NotificationManagerCompat.from(context).apply {
                notificationManager.notify(roomUid.toInt(), notification)
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
        context: Context, roomUid: Long,
        messageBody: String, timestamp: Long, senderName: String, timestampReply: Long,
        messageBodyReply: String, senderUid: String, repliedText: ArrayList<String>?,
        repliedTimestamp: ArrayList<String>?
    ): Notification {
        val currentUserName = Firebase.auth.currentUser?.displayName
        val replier = Person.Builder().setName(currentUserName).build()
        val style = NotificationCompat.MessagingStyle(
            Person.Builder().setName(senderName)
                .build()
        )
            .addMessage(
                messageBody,
                timestamp,
                Person.Builder().setName(senderName).build()
            )
            .addMessage(
                messageBodyReply,
                timestampReply,
                replier
            )
        if (repliedText != null && repliedTimestamp != null) {
            for (x in 0 until repliedText.size) {
                style.addMessage(repliedText[x], repliedTimestamp[x].toLong(), replier)
            }
        }
       return createNotification(
           context, roomUid, style,
           senderName,
           messageBody, timestamp,
           senderUid,
           repliedText,
           repliedTimestamp
       )
    }
}
