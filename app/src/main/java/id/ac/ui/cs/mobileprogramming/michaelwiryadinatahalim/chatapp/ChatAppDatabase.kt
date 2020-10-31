package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.FriendDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.RoomChatDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.Message
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.RoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User

@Database(entities = [User::class, RoomChat::class, Message::class], version = 1)
abstract class ChatAppDatabase : RoomDatabase() {

    abstract fun friendDao() : FriendDao

    abstract fun roomChatDao() : RoomChatDao
}
