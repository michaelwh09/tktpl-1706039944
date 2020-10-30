package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IFriendRepository

class FriendsViewModel @ViewModelInject constructor(
    private val friendRepository: IFriendRepository
) : ViewModel() {
    private val _friends = friendRepository.getAllAddedFriends()
    val friends = _friends.asLiveData()
}
