package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.MessageDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.Message
import java.time.Instant
import java.util.*
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val messageDao: MessageDao
): IMessageRepository {

    override fun getAllMessagesByRoomUid(roomUid: Long): PagingSource<Int, Message> {
        return messageDao.getAllMessagesByRoomUid(roomUid)
    }

    override fun sendMessage(roomUid: Long, message: String) {
        val messageModel = Message(
            UUID.randomUUID().toString(),
            Instant.now().epochSecond,
            message,
            false,
            roomUid
        )
        messageDao.insertMessage(messageModel)
    }

}
