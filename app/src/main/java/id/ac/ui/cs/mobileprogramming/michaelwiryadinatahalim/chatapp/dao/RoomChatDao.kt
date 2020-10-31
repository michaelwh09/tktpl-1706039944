package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.RoomChat

@Dao
interface RoomChatDao {

    @Query("SELECT * FROM room_chat_table ORDER BY last_message_timestamp DESC")
    fun getAllRoomsChat() : PagingSource<Int, RoomChat>
}
