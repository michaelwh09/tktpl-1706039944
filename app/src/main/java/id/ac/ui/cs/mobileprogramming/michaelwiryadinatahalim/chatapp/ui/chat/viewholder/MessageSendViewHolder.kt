package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.Message
import java.time.Instant

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
