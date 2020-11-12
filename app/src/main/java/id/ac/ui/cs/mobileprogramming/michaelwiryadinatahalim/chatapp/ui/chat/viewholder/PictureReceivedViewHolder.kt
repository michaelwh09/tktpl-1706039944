package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.Message
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.GlideApp
import java.time.Instant

class PictureReceivedViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.picture_item_received, parent, false)
) {
    private val pictureView = itemView.findViewById<ImageView>(R.id.picture_message_body)
    private val messageTimestamp = itemView.findViewById<TextView>(R.id.text_message_time)
    private var message: Message? = null
    private val storage = Firebase.storage

    fun bindTo(messageModel: Message?) {
        message = messageModel
        message?.let {
            it.uriPhoto?.let {
                    uriPhoto ->
                val gs = storage.getReferenceFromUrl(uriPhoto)
                val context = pictureView.context
                GlideApp.with(context)
                    .load(gs)
                    .centerCrop()
                    .into(pictureView)
            }
            messageTimestamp.text = java.time.format.DateTimeFormatter.ISO_INSTANT.format(
                Instant.ofEpochSecond(it.timestamp))
        }
    }
}
