package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.RoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChat
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomChatDao {

    @Transaction
    @Query("SELECT U.* FROM user_table AS U INNER JOIN room_chat_table AS R ON U.uid = R.user_uid ORDER BY R.last_message_timestamp DESC")
    fun getAllRoomsChatAndFriend() : PagingSource<Int, UserAndRoomChat>

    @Insert
    fun insertSingleRoom(room: RoomChat): Long

    @Transaction
    @Query("SELECT U.* FROM user_table AS U INNER JOIN room_chat_table AS R ON U.uid = R.user_uid WHERE R.uid == :uid")
    fun getRoomByUid(uid: Long): Flow<UserAndRoomChat>
}
