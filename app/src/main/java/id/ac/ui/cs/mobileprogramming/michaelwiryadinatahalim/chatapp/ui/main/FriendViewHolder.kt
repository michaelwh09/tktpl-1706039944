package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User

class FriendViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)) {

    private val nameView = itemView.findViewById<TextView>(R.id.friend_name_view)
    private var friend: User? = null

    fun bindTo(user: User?) {
        friend = user
        nameView.text = friend?.displayName
    }
}
