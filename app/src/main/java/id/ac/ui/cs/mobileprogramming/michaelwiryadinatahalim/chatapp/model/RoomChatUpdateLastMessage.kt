package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class RoomChatUpdateLastMessage(
    @ColumnInfo(name = "uid") val uid: Long,
    @ColumnInfo(name = "last_message") val lastMessage: String?,
    @ColumnInfo(name = "last_message_timestamp") val lastMessageTimestamp: Long?,
)
