package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.Message
import kotlinx.coroutines.flow.Flow

interface IMessageRepository {

    fun getAllMessagesByRoomUid(roomUid: Long) : PagingSource<Int, Message>

    fun sendMessage(roomUid: Long, message: String, receiverUid: String)

    fun receivedMessage(message: String, senderUid: String): Flow<Long>
}
