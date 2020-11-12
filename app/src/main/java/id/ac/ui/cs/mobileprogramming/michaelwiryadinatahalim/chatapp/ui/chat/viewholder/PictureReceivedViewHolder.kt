package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat.viewholder

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.Message
import java.time.Instant

class PictureReceivedViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.picture_item_received, parent, false)
) {
    private val pictureView = itemView.findViewById<ImageView>(R.id.picture_message_body)
    private val messageTimestamp = itemView.findViewById<TextView>(R.id.picture_message_time)
    private var message: Message? = null

    fun bindTo(messageModel: Message?) {
        message = messageModel
        message?.let {
            val context = pictureView.context
            Glide.with(context)
                .load(Uri.parse(it.uriPhoto))
                .centerCrop()
                .into(pictureView)
            messageTimestamp.text = java.time.format.DateTimeFormatter.ISO_INSTANT.format(
                Instant.ofEpochSecond(it.timestamp))
        }
    }
}
