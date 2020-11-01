package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.Message

interface IMessageRepository {

    fun getAllMessagesByRoomUid(roomUid: Long) : PagingSource<Int, Message>

    fun sendMessage(roomUid: Long, message: String)
}
