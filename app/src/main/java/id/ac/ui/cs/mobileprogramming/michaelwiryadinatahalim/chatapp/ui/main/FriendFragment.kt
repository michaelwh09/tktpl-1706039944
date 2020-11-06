package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.UserAndRoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.RecyclerViewOnClickListener
import kotlinx.android.synthetic.main.friend_fragment.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FriendFragment : Fragment(), RecyclerViewOnClickListener<UserAndRoomChat> {

    private val friendsViewModel: FriendsViewModel by activityViewModels()

    private val friendRoomViewModel: FriendRoomViewModel by navGraphViewModels(R.id.FirstFragment) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.friend_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val friendAdapter = FriendAdapter()
        friendAdapter.itemClickListener = this
        rv_friends.adapter = friendAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            friendsViewModel.friends.collectLatest {
                friendAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            friendAdapter.loadStateFlow.collectLatest {
                friends_progress_bar.isVisible = it.refresh is LoadState.Loading
                if (it.refresh is LoadState.Error) {
                    Snackbar.make(first_fragment, (it.refresh as LoadState.Error).error.message?: "Unknown error",
                        Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        fab_add.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_AddFragment)
        }
    }

    private fun navigateToChatRoom(roomUid: Long) {
        val action = FriendFragmentDirections.actionFirstFragmentToChat(roomUid)
        findNavController().navigate(action)
    }

    override fun onItemClicked(view: View, data: UserAndRoomChat) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(data.user.displayName)
                .setMessage(data.user.email)
                .setPositiveButton("Chat") { dialog, _ ->
                    dialog.dismiss()
                    if (data.roomChat == null) {
                        friendRoomViewModel.createNewRoomForUser(data.user.uid)
                        friendRoomViewModel.roomUid.observe(viewLifecycleOwner, { roomUid ->
                            if (roomUid != null) {
                                navigateToChatRoom(roomUid)
                            }
                        })
                        return@setPositiveButton
                    }
                    navigateToChatRoom(data.roomChat.uid)
                }
                .setNegativeButton("Close") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}
