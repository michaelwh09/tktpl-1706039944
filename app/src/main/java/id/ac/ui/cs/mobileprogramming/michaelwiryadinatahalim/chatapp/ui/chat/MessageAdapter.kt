package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.Message

class MessageAdapter: PagingDataAdapter<Message, MessageAdapter.MessageViewHolder>(diffCallback) {

    class MessageViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
    ) {
        private val messageView = itemView.findViewById<TextView>(R.id.message_item_value)
        private var message: Message? = null

        fun bindTo(messageModel: Message?) {
            message = messageModel
            messageView.text = message?.message
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder = MessageViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem.uid == newItem.uid

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem == newItem
        }
    }
}
