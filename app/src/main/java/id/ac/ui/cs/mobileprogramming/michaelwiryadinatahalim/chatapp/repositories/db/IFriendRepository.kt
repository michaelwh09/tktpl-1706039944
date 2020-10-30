package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User
import kotlinx.coroutines.flow.Flow

interface IFriendRepository {
    fun getAllAddedFriends() : PagingSource<Int, User>

    fun addFriend(vararg friends: User)

    fun getFriendByUid(uid: String) : Flow<User?>

    fun getFriendByEmail(email: String) : Flow<User?>
}
