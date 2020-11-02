package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.activity.MainActivity
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IMessageRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IRoomChatRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.UserFirestoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
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
            if (message != null && sender!= null) {
                Log.d("PESAN MASUK", message)
                scope.launch {
                    try {
                        val room = roomRepository.getUserRoom(sender)
                        if (room?.roomChat == null) {
                            val roomId = roomRepository.createRoom(message, sender)
                            messageRepository.receiveMessage(roomId, message, sender)
//                        sendNotification(message, roomId, sender)
                        } else {
                            val roomId = room.roomChat.uid
                            roomRepository.updateRoom(roomId, message)
                            messageRepository.receiveMessage(roomId, message, sender)
//                        sendNotification(message, roomId, room.user?.displayName ?: sender)
                        }
                    } catch (e: Exception) {
                        Log.e("SERVICE FCM", e.message?:"")
                    }
                }
            }
        }
    }

    private fun sendNotification(messageBody: String, roomUid: Long, sender: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, roomUid.toInt(), intent,
            PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(sender)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(roomUid.toInt(), notificationBuilder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
