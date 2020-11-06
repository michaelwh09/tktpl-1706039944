package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity

import androidx.room.Embedded

data class UserAndRoomChatNullable(
    @Embedded val user: User?,
    @Embedded(prefix = "room_") val roomChat: RoomChat?
)
