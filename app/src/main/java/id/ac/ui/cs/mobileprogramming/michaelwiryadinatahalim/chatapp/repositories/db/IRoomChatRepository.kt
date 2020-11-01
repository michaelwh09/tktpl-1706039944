package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChat
import kotlinx.coroutines.flow.Flow

interface IRoomChatRepository {

    fun getAllRoomsChat() : PagingSource<Int, UserAndRoomChat>

    suspend fun createSingleEmptyRoom(userUid: String) : Long

    fun getDetailRoomChatByUid(uid: Long): Flow<UserAndRoomChat?>

    fun getUserRoom(senderUid: String): Flow<UserAndRoomChat?>

    suspend fun createRoom(latestMessage: String, senderUid: String) : Long

    fun updateRoom(roomUid: Long, latestMessage: String)
}
