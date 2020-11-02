package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao

import androidx.paging.PagingSource
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.RoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.RoomChatUpdateLastMessage
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChatNullable
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomChatDao {

    @Query("""SELECT U.*,
        R.uid AS room_uid,
        R.last_message AS room_last_message,
        R.last_message_timestamp AS room_last_message_timestamp,
        R.unread_message AS room_unread_message,
        R.user_uid AS room_user_uid
        FROM user_table AS U INNER JOIN room_chat_table AS R ON U.uid = R.user_uid ORDER BY R.last_message_timestamp DESC""")
    fun getAllRoomsChatAndFriend() : PagingSource<Int, UserAndRoomChatNullable>

    @Insert
    suspend fun insertSingleRoom(room: RoomChat): Long

    @Query("""SELECT U.*,
        R.uid AS room_uid,
        R.last_message AS room_last_message,
        R.last_message_timestamp AS room_last_message_timestamp,
        R.unread_message AS room_unread_message,
        R.user_uid AS room_user_uid
        FROM user_table AS U INNER JOIN room_chat_table AS R ON U.uid = R.user_uid WHERE R.uid == :uid""")
    fun getRoomByUid(uid: Long): Flow<UserAndRoomChatNullable>

    @Update(entity = RoomChat::class)
    fun updateLastMessageWithoutUnread(roomUpdate: RoomChatUpdateLastMessage)

    @Query("""SELECT U.*,
        R.uid AS room_uid,
        R.last_message AS room_last_message,
        R.last_message_timestamp AS room_last_message_timestamp,
        R.unread_message AS room_unread_message,
        R.user_uid AS room_user_uid
        FROM user_table AS U INNER JOIN room_chat_table AS R ON U.uid = R.user_uid WHERE U.uid == :userUid""")
    suspend fun getRoomByUserUid(userUid: String): UserAndRoomChatNullable
}
