package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.Message

@Dao
interface MessageDao {

    @Query("SELECT * FROM message_table WHERE room_chat_uid == :roomUid ORDER BY timestamp DESC")
    fun getAllMessagesByRoomUid(roomUid: Long) : PagingSource<Int, Message>

    @Insert
    suspend fun insertMessage(vararg message: Message)
}
