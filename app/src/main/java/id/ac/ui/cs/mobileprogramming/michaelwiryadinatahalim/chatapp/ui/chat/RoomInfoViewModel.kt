package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.UserAndRoomChatNullable
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IRoomChatRepository
import kotlinx.coroutines.Dispatchers

class RoomInfoViewModel @AssistedInject constructor(
    roomRepository: IRoomChatRepository,
    @Assisted private val roomUid: Long
): ViewModel() {
    @AssistedInject.Factory
    interface AssistedRoomInfoViewModelFactory {
        fun create(roomUid: Long): RoomInfoViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedRoomInfoViewModelFactory,
            roomUid: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(roomUid) as T
            }
        }
    }

    val roomInfo: LiveData<UserAndRoomChatNullable?> = roomRepository.getDetailRoomChatByUid(roomUid)
        .asLiveData(Dispatchers.IO)
}
