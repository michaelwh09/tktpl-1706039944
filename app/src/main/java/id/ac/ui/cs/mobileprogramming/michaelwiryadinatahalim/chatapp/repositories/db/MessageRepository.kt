package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.MessageDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.RoomChatDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.Message
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.RoomChatUpdateLastMessage
import java.time.Instant
import java.util.*
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val roomDao: RoomChatDao
): IMessageRepository {

    override fun getAllMessagesByRoomUid(roomUid: Long): PagingSource<Int, Message> {
        return messageDao.getAllMessagesByRoomUid(roomUid)
    }

    override fun sendMessage(roomUid: Long, message: String) {
        val timestamp = Instant.now().epochSecond
        val messageModel = Message(
            UUID.randomUUID().toString(),
            timestamp,
            message,
            false,
            roomUid
        )
        val roomUpdate = RoomChatUpdateLastMessage(roomUid, message, timestamp)
        messageDao.insertMessage(messageModel)
        roomDao.updateLastMessageWithoutUnread(roomUpdate)
    }

}
