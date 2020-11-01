package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.Message
import java.time.Instant

class MessageAdapter: PagingDataAdapter<Message, RecyclerView.ViewHolder>(diffCallback) {

    class MessageReceivedViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.message_item_received, parent, false)
    ) {
        private val messageView = itemView.findViewById<TextView>(R.id.text_message_body)
        private val messageTimestamp = itemView.findViewById<TextView>(R.id.text_message_time)
        private var message: Message? = null

        fun bindTo(messageModel: Message?) {
            message = messageModel
            message?.let {
                messageView.text = it.message
                messageTimestamp.text = java.time.format.DateTimeFormatter.ISO_INSTANT.format(
                    Instant.ofEpochSecond(it.timestamp))
            }
        }
    }

    class MessageSendViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.message_item_send, parent, false)
    ) {
        private val messageView = itemView.findViewById<TextView>(R.id.text_message_body)
        private val messageTimestamp = itemView.findViewById<TextView>(R.id.text_message_time)
        private var message: Message? = null

        fun bindTo(messageModel: Message?) {
            message = messageModel
            message?.let {
                messageView.text = it.message
                messageTimestamp.text = java.time.format.DateTimeFormatter.ISO_INSTANT.format(
                    Instant.ofEpochSecond(it.timestamp))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        item?.let {
            if (it.isReceived) {
                return VIEW_TYPE_MESSAGE_RECEIVED
            }
            return VIEW_TYPE_MESSAGE_SENT
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as MessageSendViewHolder).bindTo(getItem(position))
            else -> (holder as MessageReceivedViewHolder).bindTo(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_MESSAGE_SENT -> MessageSendViewHolder(parent)
            else -> MessageReceivedViewHolder(parent)
        }
    }

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2

        private val diffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem.uid == newItem.uid

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem == newItem
        }
    }
}
