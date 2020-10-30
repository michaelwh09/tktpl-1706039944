package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {

    @Query("SELECT * FROM user_table")
    fun getAllFriends(): Flow<List<User>>

    @Insert
    fun insertAll(vararg users: User)

    @Query("SELECT * FROM user_table WHERE uid == :uid")
    fun getFriendByUid(uid: String): Flow<User?>

    @Query("SELECT * FROM user_table WHERE email == :email")
    fun getFriendByEmail(email: String): Flow<User?>
}