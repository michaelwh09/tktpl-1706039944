package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.Message
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IMessageRepository
import kotlinx.coroutines.flow.Flow

class ChatMessageViewModel @AssistedInject constructor(
    private val messageRepository: IMessageRepository,
    @Assisted private val roomUid: Long
): ViewModel() {

    val messages: Flow<PagingData<Message>> = Pager(
        PagingConfig(
            pageSize = 30,
            enablePlaceholders = true,
            maxSize = 100
        )
    ) {
     messageRepository.getAllMessagesByRoomUid(roomUid)
    }.flow
        .cachedIn(viewModelScope)

    @AssistedInject.Factory
    interface AssistedChatMessageViewModelFactory {
        fun create(roomUid: Long): ChatMessageViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedChatMessageViewModelFactory,
            roomUid: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(roomUid) as T
            }
        }
    }
}
