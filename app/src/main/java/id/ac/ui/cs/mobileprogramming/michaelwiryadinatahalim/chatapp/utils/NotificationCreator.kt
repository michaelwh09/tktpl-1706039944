package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.navigation.NavDeepLinkBuilder
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.activity.MainActivity
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.receiver.ReplyReceiver

fun createNotification(
    context: Context, roomUid: Long,
    style: NotificationCompat.Style, senderName: String, messageBody: String, timestamp: Long, senderUid: String,
    repliedTexts: ArrayList<String>? = ArrayList(), repliedTimestamp: ArrayList<String>? = ArrayList()
): Notification {

    val replyLabel = "reply pesan"
    val remoteInput: RemoteInput = RemoteInput.Builder(ReplyReceiver.KEY_TEXT_REPLY).run {
        setLabel(replyLabel)
        build()
    }
    val replyIntent = Intent(context, ReplyReceiver::class.java).also {
        it.action = "id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.REPLY_NOTIFICATION"
        it.putExtra("roomUid", roomUid)
        it.putExtra("senderName", senderName)
        it.putExtra("message", messageBody)
        it.putExtra("timestamp", timestamp)
        it.putExtra("senderUid", senderUid)
        if (repliedTexts != null && repliedTimestamp != null) {
            it.putStringArrayListExtra("replied", repliedTexts)
            it.putStringArrayListExtra("repliedTimestamp", repliedTimestamp)
        }
    }
    val replyPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(
            context,
            roomUid.toInt(),
            replyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    val action: NotificationCompat.Action =
        NotificationCompat.Action.Builder(
            R.drawable.ic_baseline_reply_24,
            "Reply", replyPendingIntent
        )
            .addRemoteInput(remoteInput)
            .setAllowGeneratedReplies(true)
            .build()
    val channelId = context.getString(R.string.default_notification_channel_id)
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    val bundle = Bundle()
    bundle.putLong("roomUid", roomUid)
    val pendingIntent = NavDeepLinkBuilder(context)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.ChatFragment)
        .setArguments(bundle)
        .setComponentName(MainActivity::class.java)
        .createPendingIntent()

    return NotificationCompat.Builder(context, channelId)
        .setStyle(
            style
        )
        .setSmallIcon(R.drawable.ic_baseline_message_24)
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
        .setGroup(roomUid.toString())
        .addAction(action)
        .setContentIntent(pendingIntent)
        .build()
}
