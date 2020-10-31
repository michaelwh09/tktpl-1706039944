package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.RoomChatDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.RoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChat
import javax.inject.Inject

class RoomChatRepository @Inject constructor(
    private val roomChatDao: RoomChatDao
): IRoomChatRepository {

    override fun getAllRoomsChat(): PagingSource<Int, UserAndRoomChat> {
        return roomChatDao.getAllRoomsChatAndFriend()
    }

}
