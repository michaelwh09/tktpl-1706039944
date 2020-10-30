package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.hideKeyboard
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.isValidEmail
import kotlinx.android.synthetic.main.add_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class AddFragment : Fragment() {

    companion object {
        private const val TAG = "AddFragment"
    }

    private val searchEmailViewModel: SearchEmailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.add_fragment, container, false)
    }

    private fun showLoading() {
        hideFriendCard()
        add_friend_progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        add_friend_progress_bar.visibility = View.INVISIBLE
    }

    private fun showFriendCard(user: User) {
        friend_name.text = user.displayName
        friend_email.text = user.email
        hideLoading()
        card_friend.visibility = View.VISIBLE
    }

    private fun hideFriendCard() {
        card_friend.visibility = View.INVISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_add.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_AddFragment_to_FirstFragment)
        }

        add_email_field.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val email = add_email_field.text?.trim().toString()
                if (email.isValidEmail()) {
                    Log.d("Add Fragment", "Email input: $email")
                    add_email_field?.clearFocus()
                    add_email_field.hideKeyboard()
                    add_email_input_layout.isErrorEnabled = false
                    searchEmailViewModel.searchUserWithEmail(email)
                } else {
                    add_email_input_layout.isErrorEnabled = true
                    add_email_input_layout.error = "Email not valid"
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        searchEmailViewModel.user.observe(viewLifecycleOwner, {
            when (it) {
                is State.Success -> showFriendCard(it.data)
                is State.Loading -> showLoading()
                is State.Failed -> {
                    hideLoading()
                    Log.d(TAG, it.throwable.message?: "unknown")
                }
                is State.Initialized -> hideLoading()
            }
        })
    }
}
