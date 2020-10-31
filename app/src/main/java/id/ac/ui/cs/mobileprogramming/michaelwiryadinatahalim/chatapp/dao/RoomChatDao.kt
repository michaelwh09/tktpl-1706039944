package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChat

@Dao
interface RoomChatDao {

    @Transaction
    @Query("SELECT * FROM room_chat_table ORDER BY last_message_timestamp DESC")
    fun getAllRoomsChatAndFriend() : PagingSource<Int, UserAndRoomChat>
}
