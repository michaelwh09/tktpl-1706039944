package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
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
            toolbar_chat.title = it.user.displayName
        })

    }
}
