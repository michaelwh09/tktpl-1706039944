package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.FriendDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FriendRepository @Inject constructor(
    private val friendDao: FriendDao
): IFriendRepository {

    override fun getAllAddedFriends(): PagingSource<Int, UserAndRoomChat> {
        return friendDao.getAllFriends()
    }

    override fun addFriend(vararg friends: User) {
        friendDao.insertAll(*friends)
    }

    override fun getFriendByUid(uid: String): Flow<User?> {
        return friendDao.getFriendByUid(uid)
    }

    override fun getFriendByEmail(email: String): Flow<User?> {
        return friendDao.getFriendByEmail(email)
    }

}
