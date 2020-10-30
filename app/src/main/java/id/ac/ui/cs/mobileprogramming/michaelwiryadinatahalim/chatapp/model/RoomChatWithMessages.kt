package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class RoomChatWithMessages(
    @Embedded val roomChat: RoomChat,
    @Relation(
        parentColumn = "uid",
        entityColumn = "room_chat_uid"
    )
    val messages: List<Message>
)
