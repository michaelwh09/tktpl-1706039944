package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.room

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.RecyclerViewOnClickListener

class RoomChatAdapter: PagingDataAdapter<UserAndRoomChat, RoomChatAdapter.RoomChatViewHolder>(diffCallback) {

    class RoomChatViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.room_item, parent, false)
    ) {
        private val nameView = itemView.findViewById<TextView>(R.id.room_friend_name)
        private val lastMessage = itemView.findViewById<TextView>(R.id.last_message)
        private val lastMessageTimestamp = itemView.findViewById<TextView>(R.id.last_message_timestamp)
        private var userAndRoomChat: UserAndRoomChat? = null

        fun bindTo(user: UserAndRoomChat?, itemClickListener: RecyclerViewOnClickListener<UserAndRoomChat>?) {
            userAndRoomChat = user
            nameView.text = userAndRoomChat?.user?.displayName
            lastMessage.text = userAndRoomChat?.roomChat?.lastMessage?: ""
            userAndRoomChat?.roomChat?.lastMessageTimestamp?.let {
                lastMessageTimestamp.text = java.time.format.DateTimeFormatter.ISO_INSTANT
                    .format(java.time.Instant.ofEpochSecond(it))
            }
            itemView.setOnClickListener {
                userAndRoomChat?.let { roomChat -> itemClickListener?.onItemClicked(it, roomChat) }
            }
        }
    }

    var itemClickListener: RecyclerViewOnClickListener<UserAndRoomChat>? = null

    override fun onBindViewHolder(holder: RoomChatViewHolder, position: Int) {
        holder.bindTo(getItem(position), itemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomChatViewHolder =
        RoomChatViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<UserAndRoomChat>() {
            override fun areItemsTheSame(oldItem: UserAndRoomChat, newItem: UserAndRoomChat): Boolean =
                oldItem.user?.uid == newItem.user?.uid && oldItem.roomChat?.uid == newItem.roomChat?.uid

            override fun areContentsTheSame(oldItem: UserAndRoomChat, newItem: UserAndRoomChat): Boolean =
                oldItem == newItem
        }
    }
}
