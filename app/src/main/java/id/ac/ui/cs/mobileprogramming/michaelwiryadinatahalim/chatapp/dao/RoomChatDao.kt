package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.RoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChat

@Dao
interface RoomChatDao {

    @Transaction
    @Query("SELECT U.* FROM user_table AS U INNER JOIN room_chat_table AS R ON U.uid = R.user_uid ORDER BY R.last_message_timestamp DESC")
    fun getAllRoomsChatAndFriend() : PagingSource<Int, UserAndRoomChat>
}
