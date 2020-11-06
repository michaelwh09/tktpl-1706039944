package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationModel(
    val roomUid: Long,
    val senderName: String,
    val message: String,
    val timestamp: Long,
    val senderUid: String,
    val repliedMessage: ArrayList<ReplyMessage>,
) : Parcelable
