package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.service

import android.app.ActivityManager
import android.app.KeyguardManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.NotificationModel
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IMessageRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IRoomChatRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.UserFirestoreRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.createNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject


@AndroidEntryPoint
class MessageFirebaseMessagingService : FirebaseMessagingService() {


    private val job = SupervisorJob()
    private val scope = CoroutineScope(context = Dispatchers.IO + job)

    @Inject
    lateinit var userFirestoreRepository: UserFirestoreRepository

    @Inject
    lateinit var messageRepository: IMessageRepository

    @Inject
    lateinit var roomRepository: IRoomChatRepository

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val auth = Firebase.auth
        auth.currentUser?.let {
            saveTokenToFirestore(it, token)
            Log.d(TAG, "Refreshed token FCM: $token")
        }
    }

    private fun saveTokenToFirestore(
        user: FirebaseUser,
        token: String
    ) {
        userFirestoreRepository.addTokenToUserFirestore(token, user)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            val message = remoteMessage.data["message"]
            val sender = remoteMessage.data["sender"]
            val emailSender = remoteMessage.data["senderEmail"]
            val isImage = remoteMessage.data["isImage"].toBoolean()
            if (message != null && sender!= null) {
                Log.d("PESAN MASUK PESAN", message)
                val messageClean = if (isImage) getString(R.string.image_wording_received) else message
                scope.launch {
                    try {
                        val timestamp = Instant.now().epochSecond
                        val room = roomRepository.getUserRoom(sender)
                        if (room?.roomChat == null) {
                            val roomId = roomRepository.createRoom(messageClean, sender, emailSender)
                            messageRepository.receiveMessage(roomId, message, sender, timestamp, isImage)
                            sendNotification(messageClean, roomId, emailSender?:sender, sender, timestamp)
                            return@launch
                        } else {
                            val roomId = room.roomChat.uid
                            messageRepository.receiveMessage(roomId, message, sender, timestamp, isImage)
                            sendNotification(messageClean, roomId, room.user?.displayName ?:
                            emailSender?:sender, sender, timestamp)
                            return@launch
                        }
                    } catch (e: Exception) {
                        Log.e("SERVICE FCM", e.message ?: "")
                        return@launch
                    }
                }
            }
        }
    }

    private fun shouldShowNotification(): Boolean {
        val process = ActivityManager.RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(process)
        if (process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            return true
        }
        // app is in foreground, but if screen is locked show notification anyway
        return (applicationContext.getSystemService(KEYGUARD_SERVICE) as KeyguardManager).isKeyguardLocked
    }

    private fun sendNotification(messageBody: String, roomUid: Long, senderName: String, senderUid: String, timestamp: Long) {
        if (! shouldShowNotification()) return
        val style = NotificationCompat.MessagingStyle(
            Person.Builder().setName(senderName)
                .build()
        )
            .addMessage(
                messageBody,
                timestamp,
                Person.Builder().setName(senderName).build()
            )
        val notification = createNotification(
            applicationContext, style,
            NotificationModel(roomUid, senderName,
                messageBody, timestamp, senderUid, ArrayList())
        )
        val channelId = getString(R.string.default_notification_channel_id)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Chatapp channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        NotificationManagerCompat.from(this).apply {
            notificationManager.notify(roomUid.toInt(), notification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
