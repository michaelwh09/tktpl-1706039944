package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.room

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChat

class RoomChatAdapter: PagingDataAdapter<UserAndRoomChat, RoomChatAdapter.RoomChatViewHolder>(diffCallback) {

    class RoomChatViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.room_item, parent, false)
    ) {
        private val nameView = itemView.findViewById<TextView>(R.id.room_chat_view)
        private var userAndRoomChat: UserAndRoomChat? = null

        fun bindTo(user: UserAndRoomChat?) {
            userAndRoomChat = user
            nameView.text = userAndRoomChat?.user?.displayName
        }
    }

    override fun onBindViewHolder(holder: RoomChatViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomChatViewHolder =
        RoomChatViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<UserAndRoomChat>() {
            override fun areItemsTheSame(oldItem: UserAndRoomChat, newItem: UserAndRoomChat): Boolean =
                oldItem.roomChat.uid == newItem.roomChat.uid && oldItem.user.uid == newItem.user.uid

            override fun areContentsTheSame(oldItem: UserAndRoomChat, newItem: UserAndRoomChat): Boolean =
                oldItem == newItem
        }
    }
}
