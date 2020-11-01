package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import kotlinx.android.synthetic.main.chat_fragment.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private val args: ChatFragmentArgs by navArgs()

    @Inject
    lateinit var chatMessageViewModelAssistedFactory: ChatMessageViewModel.AssistedChatMessageViewModelFactory

    @Inject
    lateinit var roomInfoViewModelAssistedFactory: RoomInfoViewModel.AssistedRoomInfoViewModelFactory

    @Inject
    lateinit var sendMessageViewModelAssistedFactory: SendMessageViewModel.AssistedSendMessageViewModelFactory

    private val sendMessageViewModel: SendMessageViewModel by navGraphViewModels(R.id.ChatFragment) {
        SendMessageViewModel.provideFactory(
            sendMessageViewModelAssistedFactory, args.roomUid
        )
    }

    private val chatMessageViewModel: ChatMessageViewModel by navGraphViewModels(R.id.ChatFragment) {
        ChatMessageViewModel.provideFactory(
            chatMessageViewModelAssistedFactory, args.roomUid
        )
    }

    private val roomInfoViewModel: RoomInfoViewModel by navGraphViewModels(R.id.ChatFragment) {
        RoomInfoViewModel.provideFactory(
            roomInfoViewModelAssistedFactory, args.roomUid
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val messageAdapter = MessageAdapter()
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        rv_chat_message.layoutManager = linearLayout
        rv_chat_message.adapter = messageAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            chatMessageViewModel.messages.collectLatest {
                messageAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            messageAdapter.loadStateFlow.collectLatest {
                message_progress_bar.isVisible = it.refresh is LoadState.Loading
                if (it.refresh is LoadState.Error) {
                    Snackbar.make(chat_fragment, (it.refresh as LoadState.Error).error.message?: "Unknown error",
                        Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        roomInfoViewModel.roomInfo.observe(viewLifecycleOwner, {
            if (it != null) {
                toolbar_chat.title = it.user?.displayName?:it.roomChat?.userUid
                button_send_message.setOnClickListener { _: View? ->
                    add_message_field.text?.let { text ->
                        sendMessageViewModel.sendMessage(text.trim().toString(),
                            (it.user?.uid ?: it.roomChat?.userUid)!!
                        )
                        add_message_field.text = null
                    }
                }
            }
        })
    }
}
