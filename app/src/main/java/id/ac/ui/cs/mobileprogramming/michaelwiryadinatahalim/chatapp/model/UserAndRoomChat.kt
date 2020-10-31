package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndRoomChat(
    @Embedded val roomChat: RoomChat,

    @Relation(
        parentColumn = "uid",
        entityColumn = "room_uid"
    )
    val user: User?
)
