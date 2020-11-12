package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IMessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class SendMessageViewModel @AssistedInject constructor(
    private val messageRepository: IMessageRepository,
    @Assisted private val roomUid: Long
): ViewModel() {

    @AssistedInject.Factory
    interface AssistedSendMessageViewModelFactory {
        fun create(roomUid: Long): SendMessageViewModel
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

    fun sendPicture(photoUri: Uri, receiverUid: String) {
        viewModelScope.launch(Dispatchers.IO) {

            messageRepository.sendPicture(roomUid, photoUri, receiverUid)
        }
    }
}
