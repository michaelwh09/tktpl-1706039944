package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class Message(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "uid") val uid: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "is_received") val isReceived: Boolean,
    @ColumnInfo(name = "room_chat_uid") val roomChatUid: Int,
)
