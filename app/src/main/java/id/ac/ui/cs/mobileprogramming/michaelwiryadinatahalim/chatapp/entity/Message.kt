package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table", foreignKeys = [androidx.room.ForeignKey(
    entity = RoomChat::class,
    parentColumns = ["uid"], childColumns = ["room_chat_uid"], onDelete = androidx.room.ForeignKey.CASCADE
)])
data class Message(
    @PrimaryKey @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "message") val message: String?,
    @ColumnInfo(name = "is_received") val isReceived: Boolean,
    @ColumnInfo(name = "room_chat_uid", index = true) val roomChatUid: Long,
    @ColumnInfo(name = "is_image", defaultValue = "0") val isImage: Boolean,
    @ColumnInfo(name = "photo") val uriPhoto: String?
)
