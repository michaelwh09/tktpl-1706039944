package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.UserAndRoomChat
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IFriendRepository
import kotlinx.coroutines.flow.Flow

class FriendsViewModel @ViewModelInject constructor(
    private val friendRepository: IFriendRepository
) : ViewModel() {

    val friends: Flow<PagingData<UserAndRoomChat>> = Pager(
        PagingConfig(
            pageSize = 30,
            enablePlaceholders = true,
            maxSize = 90
        )
    ) {
        friendRepository.getAllAddedFriends()
    }.flow
        .cachedIn(viewModelScope)
}
