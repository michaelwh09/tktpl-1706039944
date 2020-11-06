package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.User
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.UserAndRoomChat
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {

    @Transaction
    @Query("SELECT * FROM user_table ORDER BY display_name DESC")
    fun getAllFriends(): PagingSource<Int, UserAndRoomChat>

    @Insert
    fun insertAll(vararg users: User)

    @Query("SELECT * FROM user_table WHERE uid == :uid")
    fun getFriendByUid(uid: String): Flow<User?>

    @Query("SELECT * FROM user_table WHERE email == :email")
    fun getFriendByEmail(email: String): Flow<User?>
}
