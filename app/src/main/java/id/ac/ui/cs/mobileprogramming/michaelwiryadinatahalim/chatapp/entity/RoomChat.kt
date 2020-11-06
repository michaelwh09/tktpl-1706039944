package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_chat_table")
data class RoomChat(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "uid") val uid: Long,
    @ColumnInfo(name = "last_message") val lastMessage: String?,
    @ColumnInfo(name = "last_message_timestamp") val lastMessageTimestamp: Long?,
    @ColumnInfo(name = "unread_message") val unreadMessage: Long?,
    @ColumnInfo(name = "user_uid") val userUid: String?,
)
