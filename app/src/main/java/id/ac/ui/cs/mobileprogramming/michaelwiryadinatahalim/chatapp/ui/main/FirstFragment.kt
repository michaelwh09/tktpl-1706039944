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
import androidx.paging.LoadState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.RecyclerViewOnClickListener
import kotlinx.android.synthetic.main.first_fragment.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstFragment : Fragment(), RecyclerViewOnClickListener<User> {

    private val friendsViewModel: FriendsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.first_fragment, container, false)
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

    override fun onItemClicked(view: View, data: User) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(data.displayName)
                .setMessage(data.email)
                .setPositiveButton("Chat") { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton("Close") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}
