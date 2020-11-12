package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.Message
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat.viewholder.MessageReceivedViewHolder
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat.viewholder.MessageSendViewHolder
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat.viewholder.PictureReceivedViewHolder
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat.viewholder.PictureSendViewHolder

class MessageAdapter: PagingDataAdapter<Message, RecyclerView.ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        item?.let {
            if (it.isReceived) {
                if (it.isImage) {
                    return VIEW_TYPE_PICTURE_RECEIVED
                }
                return VIEW_TYPE_MESSAGE_RECEIVED
            }
            if (it.isImage) {
                return VIEW_TYPE_PICTURE_SENT
            }
            return VIEW_TYPE_MESSAGE_SENT
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as MessageSendViewHolder).bindTo(getItem(position))
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as MessageReceivedViewHolder).bindTo(getItem(position))
            VIEW_TYPE_PICTURE_SENT -> (holder as PictureSendViewHolder).bindTo(getItem(position))
            VIEW_TYPE_PICTURE_RECEIVED -> (holder as PictureReceivedViewHolder).bindTo(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_MESSAGE_SENT -> MessageSendViewHolder(parent)
            VIEW_TYPE_PICTURE_SENT -> PictureSendViewHolder(parent)
            VIEW_TYPE_PICTURE_RECEIVED -> PictureReceivedViewHolder(parent)
            else -> MessageReceivedViewHolder(parent)
        }
    }

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
        private const val VIEW_TYPE_PICTURE_SENT = 3
        private const val VIEW_TYPE_PICTURE_RECEIVED = 4

        private val diffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem.uid == newItem.uid

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem == newItem
        }
    }
}
