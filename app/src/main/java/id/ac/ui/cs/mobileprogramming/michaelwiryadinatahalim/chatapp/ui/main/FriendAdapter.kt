package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.main


import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User

class FriendAdapter : PagingDataAdapter<User, FriendViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder = FriendViewHolder(parent)


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.uid == newItem.uid

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}