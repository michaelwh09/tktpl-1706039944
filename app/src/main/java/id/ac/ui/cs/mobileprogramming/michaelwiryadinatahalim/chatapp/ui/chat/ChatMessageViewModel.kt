package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.Message
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IMessageRepository
import kotlinx.coroutines.flow.Flow

class ChatMessageViewModel @ViewModelInject constructor(
    private val messageRepository: IMessageRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val messages: Flow<PagingData<Message>> = Pager(
        PagingConfig(
            pageSize = 30,
            enablePlaceholders = true,
            maxSize = 100
        )
    ) {
     messageRepository.getAllMessagesByRoomUid(savedStateHandle.get<Int>("roomUid")!!)
    }.flow
        .cachedIn(viewModelScope)
}
