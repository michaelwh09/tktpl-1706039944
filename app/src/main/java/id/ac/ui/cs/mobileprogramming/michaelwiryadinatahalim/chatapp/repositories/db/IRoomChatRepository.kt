package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.UserAndRoomChatNullable
import kotlinx.coroutines.flow.Flow

interface IRoomChatRepository {

    fun getAllRoomsChat() : PagingSource<Int, UserAndRoomChatNullable>

    suspend fun createSingleEmptyRoom(userUid: String) : Long

    fun getDetailRoomChatByUid(uid: Long): Flow<UserAndRoomChatNullable?>

    suspend fun getUserRoom(senderUid: String): UserAndRoomChatNullable?

    suspend fun createRoom(latestMessage: String, senderUid: String, senderEmail: String?) : Long

    fun updateRoom(roomUid: Long, latestMessage: String)
}
