package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.MessageDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.Message
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val messageDao: MessageDao
): IMessageRepository {

    override fun getAllMessagesByRoomUid(roomUid: Int): PagingSource<Int, Message> {
        return messageDao.getAllMessagesByRoomUid(roomUid)
    }

}
