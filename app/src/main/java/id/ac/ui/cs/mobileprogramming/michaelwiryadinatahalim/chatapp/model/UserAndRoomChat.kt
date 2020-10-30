package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndRoomChat(
    @Embedded val user: User,

    @Relation(
        parentColumn = "uid",
        entityColumn = "user_uid"
    )
    val roomChat: RoomChat
)
