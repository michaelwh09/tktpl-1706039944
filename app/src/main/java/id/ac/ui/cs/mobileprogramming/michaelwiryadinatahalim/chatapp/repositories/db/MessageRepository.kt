package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.MessageDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.RoomChatDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.Message
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.RoomChatUpdateLastMessage
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.FunctionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.Instant
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MessageRepository
@Inject constructor(
    private val messageDao: MessageDao,
    private val roomDao: RoomChatDao,
    private val functionRepository: FunctionRepository,
): IMessageRepository {

    override fun getAllMessagesByRoomUid(roomUid: Long): PagingSource<Int, Message> {
        return messageDao.getAllMessagesByRoomUid(roomUid)
    }

    override suspend fun sendMessage(roomUid: Long, message: String, receiverUid: String) {
        val timestamp = Instant.now().epochSecond
        sendMessage(roomUid, message, receiverUid, timestamp)
    }

    override suspend fun sendMessage(roomUid: Long, message: String, receiverUid: String, timestamp: Long) {
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
        functionRepository.sendMessageToUser(receiverUid, message)
    }

    override suspend fun receiveMessage(roomUid: Long, message: String, senderUid: String, timestamp: Long) {
        val messageModel = Message(
            UUID.randomUUID().toString(),
            timestamp,
            message,
            true,
            roomUid
        )
        messageDao.insertMessage(messageModel)
    }
}
