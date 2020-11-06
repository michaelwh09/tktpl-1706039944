package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.room

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.UserAndRoomChatNullable
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IRoomChatRepository
import kotlinx.coroutines.flow.Flow

class RoomsChatViewModel @ViewModelInject constructor(
    private val roomChatRepository: IRoomChatRepository
): ViewModel() {

    val roomsChat: Flow<PagingData<UserAndRoomChatNullable>> = Pager(
        PagingConfig(
            pageSize = 30,
            enablePlaceholders = true,
            maxSize = 100
        )
    ) {
        roomChatRepository.getAllRoomsChat()
    }.flow
        .cachedIn(viewModelScope)
}
