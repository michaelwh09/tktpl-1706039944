package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IFriendRepository
import kotlinx.coroutines.flow.Flow

class FriendsViewModel @ViewModelInject constructor(
    private val friendRepository: IFriendRepository
) : ViewModel() {

    val friends: Flow<PagingData<User>> = Pager(
        PagingConfig(
            pageSize = 30,
            enablePlaceholders = true,
            maxSize = 100
        )
    ) {
        friendRepository.getAllAddedFriends()
    }.flow
        .cachedIn(viewModelScope)
}
