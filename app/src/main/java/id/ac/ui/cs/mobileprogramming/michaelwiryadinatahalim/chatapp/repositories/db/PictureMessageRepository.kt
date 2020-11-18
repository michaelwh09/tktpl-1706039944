package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import android.net.Uri
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.MessageDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.RoomChatDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.Message
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.RoomChatUpdateLastMessage
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.FunctionRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.StorageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.InputStream
import java.time.Instant
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PictureMessageRepository
    @Inject constructor
        (private val storageRepository: StorageRepository,
         private val messageDao: MessageDao,
         private val roomDao: RoomChatDao,
         private val functionRepository: FunctionRepository
    ): IPictureMessageRepository {

    override suspend fun sendPicture(roomUid: Long, photoUri: Uri, receiverUid: String,
                                     inputStream: InputStream
    ) {
        val timestamp = Instant.now().epochSecond
        val messageModel = Message(
            UUID.randomUUID().toString(),
            timestamp,
            null,
            false,
            roomUid,
            true,
            photoUri.toString(),
        )
        messageDao.insertMessage(messageModel)
        val roomUpdate = RoomChatUpdateLastMessage(roomUid, "Photo sent", timestamp, true)
        roomDao.updateLastMessageWithoutUnread(roomUpdate)
        val url = storageRepository.uploadImage(receiverUid, inputStream)
        functionRepository.sendPictureToUser(receiverUid, url)
    }
}
