package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import kotlinx.android.synthetic.main.room_fragment.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RoomFragment : Fragment() {

    private val roomsChatViewModel: RoomsChatViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.room_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val roomChatAdapter = RoomChatAdapter()
        rv_rooms_chat.adapter = roomChatAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            roomsChatViewModel.roomsChat.collectLatest {
                roomChatAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            roomChatAdapter.loadStateFlow.collectLatest {
                rooms_chat_progress_bar.isVisible = it.refresh is LoadState.Loading
                if (it.refresh is LoadState.Error) {
                    Snackbar.make(room_fragment, (it.refresh as LoadState.Error).error.message?: "Unknown error",
                        Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}
