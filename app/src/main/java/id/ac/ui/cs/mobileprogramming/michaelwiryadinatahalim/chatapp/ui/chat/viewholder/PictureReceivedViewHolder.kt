package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat.viewholder

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.Message
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.decodeSampledBitmapFromResource
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
            val contentResolver = pictureView.context.contentResolver
            val filePicture = contentResolver.openInputStream(Uri.parse(it.uriPhoto))
            val image = filePicture?.let { inputStream ->
                decodeSampledBitmapFromResource(inputStream, 100, 100) }
            pictureView.setImageBitmap(image)
            messageTimestamp.text = java.time.format.DateTimeFormatter.ISO_INSTANT.format(
                Instant.ofEpochSecond(it.timestamp))
        }
    }
}
