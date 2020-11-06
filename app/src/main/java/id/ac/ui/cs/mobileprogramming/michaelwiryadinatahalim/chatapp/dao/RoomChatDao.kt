package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.RoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.RoomChatUpdateLastMessage
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.UserAndRoomChatNullable
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomChatDao {

    @Query("""SELECT U.*,
        R.uid AS room_uid,
        R.last_message AS room_last_message,
        R.last_message_timestamp AS room_last_message_timestamp,
        R.unread_message AS room_unread_message,
        R.user_uid AS room_user_uid
        FROM ROOM_CHAT_TABLE AS R LEFT JOIN user_table AS U ORDER BY R.last_message_timestamp DESC""")
    fun getAllRoomsChatAndFriend() : PagingSource<Int, UserAndRoomChatNullable>

    @Insert
    suspend fun insertSingleRoom(room: RoomChat): Long

    @Query("""SELECT U.*,
        R.uid AS room_uid,
        R.last_message AS room_last_message,
        R.last_message_timestamp AS room_last_message_timestamp,
        R.unread_message AS room_unread_message,
        R.user_uid AS room_user_uid
        FROM ROOM_CHAT_TABLE AS R LEFT JOIN user_table AS U WHERE R.uid == :uid""")
    fun getRoomByUid(uid: Long): Flow<UserAndRoomChatNullable?>

    @Update(entity = RoomChat::class)
    fun updateLastMessageWithoutUnread(roomUpdate: RoomChatUpdateLastMessage)

    @Query("""SELECT U.*,
        R.uid AS room_uid,
        R.last_message AS room_last_message,
        R.last_message_timestamp AS room_last_message_timestamp,
        R.unread_message AS room_unread_message,
        R.user_uid AS room_user_uid FROM ROOM_CHAT_TABLE AS R LEFT JOIN user_table AS U ON U.uid = R.user_uid WHERE R.user_uid == :userUid""")
    suspend fun getRoomByUserUid(userUid: String): UserAndRoomChatNullable?
}
