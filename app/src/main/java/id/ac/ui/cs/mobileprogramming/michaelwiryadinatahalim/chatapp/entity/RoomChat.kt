package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity

import androidx.room.*

@Entity(tableName = "room_chat_table", foreignKeys = [ForeignKey(entity = User::class,
    parentColumns = ["uid"], childColumns = ["user_uid"], onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["user_email_temp"], unique = true)])
data class RoomChat(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "uid") val uid: Long,
    @ColumnInfo(name = "last_message") val lastMessage: String?,
    @ColumnInfo(name = "last_message_timestamp") val lastMessageTimestamp: Long?,
    @ColumnInfo(name = "unread_message") val unreadMessage: Long?,
    @ColumnInfo(name = "user_uid", index = true) val userUid: String?,
    @ColumnInfo(name = "user_email_temp") val userEmailTemp: String?,
    @ColumnInfo(name = "is_last_message_picture")  val isLastMessagePicture: Boolean?
)
