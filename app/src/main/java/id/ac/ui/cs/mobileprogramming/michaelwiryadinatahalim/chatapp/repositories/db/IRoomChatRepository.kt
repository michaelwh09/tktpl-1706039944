package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChat
import kotlinx.coroutines.flow.Flow

interface IRoomChatRepository {

    fun getAllRoomsChat() : PagingSource<Int, UserAndRoomChat>

    fun createSingleEmptyRoom(userUid: String) : Long

    fun getDetailRoomChatByUid(uid: Long): Flow<UserAndRoomChat>
}
