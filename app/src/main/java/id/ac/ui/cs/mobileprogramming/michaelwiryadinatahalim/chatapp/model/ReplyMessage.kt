package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReplyMessage(
    val message: String,
    val timestamp: Long,
) : Parcelable