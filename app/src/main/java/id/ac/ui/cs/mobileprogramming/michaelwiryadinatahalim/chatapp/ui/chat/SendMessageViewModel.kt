package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IMessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream

class SendMessageViewModel @AssistedInject constructor(
    private val messageRepository: IMessageRepository,
    @Assisted private val roomUid: Long
): ViewModel() {

    @AssistedInject.Factory
    interface AssistedSendMessageViewModelFactory {
        fun create(roomUid: Long): SendMessageViewModel
    }

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    init {
        _error.value = false
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedSendMessageViewModelFactory,
            roomUid: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(roomUid) as T
            }
        }
    }

    fun sendMessage(message: String, receiverUid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Send message", "$message, $receiverUid")
            messageRepository.sendMessage(roomUid, message, receiverUid)
        }
    }

    fun sendPicture(photoUri: Uri, receiverUid: String, InputStream: InputStream) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                messageRepository.sendPicture(roomUid, photoUri, receiverUid, InputStream)
            } catch (_: Exception) {
                _error.postValue(true)
                _error.postValue(false)
            }
        }
    }
}
