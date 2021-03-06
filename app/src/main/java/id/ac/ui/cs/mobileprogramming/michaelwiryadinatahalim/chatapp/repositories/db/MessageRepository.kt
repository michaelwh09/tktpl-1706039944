package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.MessageDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.Message
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.FunctionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.Instant
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MessageRepository
@Inject constructor(
    private val messageDao: MessageDao,
    private val roomChatRepository: IRoomChatRepository,
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
            roomUid,
            false,
            null,
        )
        messageDao.insertMessage(messageModel)
        roomChatRepository.updateRoom(roomUid, message, timestamp, false)
        functionRepository.sendMessageToUser(receiverUid, message)
    }

    override suspend fun receiveMessage(roomUid: Long, message: String, senderUid: String, timestamp: Long,
    isImage: Boolean) {
        val messageModel = Message(
            UUID.randomUUID().toString(),
            timestamp,
            if (isImage) null else message,
            true,
            roomUid,
            isImage,
            if (isImage) message else null,
        )
        messageDao.insertMessage(messageModel)
        roomChatRepository.updateRoom(roomUid, message, timestamp, isImage)
    }
}
