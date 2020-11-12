package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.RoomChatDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.RoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.RoomChatUpdateLastMessage
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.UserAndRoomChatNullable
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import javax.inject.Inject

class RoomChatRepository @Inject constructor(
    private val roomChatDao: RoomChatDao
): IRoomChatRepository {

    override fun getAllRoomsChat(): PagingSource<Int, UserAndRoomChatNullable> {
        return roomChatDao.getAllRoomsChatAndFriend()
    }

    override suspend fun createSingleEmptyRoom(userUid: String): Long {
        return roomChatDao.insertSingleRoom(RoomChat(0, null, null, null,
            userUid, null))
    }

    override fun getDetailRoomChatByUid(uid: Long): Flow<UserAndRoomChatNullable?> {
        return roomChatDao.getRoomByUid(uid)
    }

    override suspend fun getUserRoom(senderUid: String): UserAndRoomChatNullable? {
        return roomChatDao.getRoomByUserUid(senderUid)
    }

    override suspend fun createRoom(latestMessage: String, senderUid: String, senderEmail: String?): Long {
        val timestamp = Instant.now().epochSecond
        return roomChatDao.insertSingleRoom(
            RoomChat(0, latestMessage, timestamp, 1,
                senderUid, senderEmail))
    }

    override fun updateRoom(roomUid: Long, latestMessage: String) {
        val timestamp = Instant.now().epochSecond
        roomChatDao.updateLastMessageWithoutUnread(
            RoomChatUpdateLastMessage(
                roomUid,
                latestMessage,
                timestamp
            )
        )
    }
}
