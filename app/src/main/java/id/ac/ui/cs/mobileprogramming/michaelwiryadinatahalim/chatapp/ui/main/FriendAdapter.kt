package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.RecyclerViewOnClickListener

class FriendAdapter : PagingDataAdapter<User, FriendAdapter.FriendViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bindTo(getItem(position), itemClickListener)
    }

    var itemClickListener: RecyclerViewOnClickListener<User>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder = FriendViewHolder(parent)

    class FriendViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)) {

        private val nameView = itemView.findViewById<TextView>(R.id.friend_name_view)
        private var friend: User? = null

        fun bindTo(user: User?, itemClickListener: RecyclerViewOnClickListener<User>?) {
            friend = user
            nameView.text = friend?.displayName
            nameView.setOnClickListener {
                friend?.let { friend -> itemClickListener?.onItemClicked(it, friend) }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.uid == newItem.uid

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}
