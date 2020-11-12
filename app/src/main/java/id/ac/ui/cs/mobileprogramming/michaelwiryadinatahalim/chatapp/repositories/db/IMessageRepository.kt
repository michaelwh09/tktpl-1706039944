package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import android.net.Uri
import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.Message

interface IMessageRepository {

    fun getAllMessagesByRoomUid(roomUid: Long) : PagingSource<Int, Message>

    suspend fun sendMessage(roomUid: Long, message: String, receiverUid: String)

    suspend fun receiveMessage(roomUid: Long, message: String, senderUid: String, timestamp: Long)

    suspend fun sendMessage(roomUid: Long, message: String, receiverUid: String, timestamp: Long)

    suspend fun sendPicture(roomUid: Long, photoUri: Uri, receiverUid: String)

    suspend fun sendPicture(roomUid: Long, photoUri: Uri, receiverUid: String, timestamp: Long)
}
