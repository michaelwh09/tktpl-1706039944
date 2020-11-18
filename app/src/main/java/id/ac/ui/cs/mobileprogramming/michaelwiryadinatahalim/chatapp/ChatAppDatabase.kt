package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.FriendDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.MessageDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.RoomChatDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.Message
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.RoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.User

@Database(entities = [User::class, RoomChat::class, Message::class], version = 1)
abstract class ChatAppDatabase : RoomDatabase() {

    abstract fun friendDao() : FriendDao

    abstract fun roomChatDao() : RoomChatDao

    abstract fun messageDao() : MessageDao
}
