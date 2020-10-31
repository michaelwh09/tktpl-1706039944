package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IMessageRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IRoomChatRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FriendRoomViewModel @ViewModelInject constructor(
    private val roomRepository: IRoomChatRepository
): ViewModel() {
    private val _roomUid = MutableLiveData<Long?>()
    var roomUid: LiveData<Long?> = _roomUid

    private var job: Job? = null

    fun createNewRoomForUser(uidUser: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            val roomUid = roomRepository.createSingleEmptyRoom(uidUser)
            _roomUid.postValue(roomUid)
        }
        _roomUid.value = null
    }
}
