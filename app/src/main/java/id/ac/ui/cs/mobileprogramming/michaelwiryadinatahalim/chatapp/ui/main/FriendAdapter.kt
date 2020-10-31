package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.UserAndRoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.RecyclerViewOnClickListener

class FriendAdapter : PagingDataAdapter<UserAndRoomChat, FriendAdapter.FriendViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bindTo(getItem(position), itemClickListener)
    }

    var itemClickListener: RecyclerViewOnClickListener<UserAndRoomChat>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder = FriendViewHolder(parent)

    class FriendViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)) {

        private val nameView = itemView.findViewById<TextView>(R.id.friend_name_view)
        private var friend: UserAndRoomChat? = null

        fun bindTo(user: UserAndRoomChat?, itemClickListener: RecyclerViewOnClickListener<UserAndRoomChat>?) {
            friend = user
            nameView.text = friend?.user?.displayName
            nameView.setOnClickListener {
                friend?.let { friend -> itemClickListener?.onItemClicked(it, friend) }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<UserAndRoomChat>() {
            override fun areItemsTheSame(oldItem: UserAndRoomChat, newItem: UserAndRoomChat): Boolean =
                oldItem.user.uid == newItem.user.uid && oldItem.roomChat?.uid == newItem.roomChat?.uid

            override fun areContentsTheSame(oldItem: UserAndRoomChat, newItem: UserAndRoomChat): Boolean =
                oldItem == newItem
        }
    }
}
