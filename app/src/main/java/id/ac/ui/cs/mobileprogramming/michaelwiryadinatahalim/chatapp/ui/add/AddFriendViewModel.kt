package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.add

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.IFriendRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.UserRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class AddFriendViewModel
@ViewModelInject constructor(
    private val repository: UserRepository,
    private val friendRepository: IFriendRepository
): ViewModel() {

    companion object {
        private const val TAG = "AddFriendViewModel"
    }

    private val _user = MutableLiveData<State<User>>()
    var user: LiveData<State<User>> = _user

    private val _added = MutableLiveData<Boolean>()
    val added: LiveData<Boolean> = _added

    private var jobInsert: Job? = null
    private var job: Job? = null

    init {
        _user.value = State.init()
        _added.value = true
    }

    fun addUserToFriend(user: User) {
        _added.value = true
        jobInsert?.cancel()
        jobInsert = viewModelScope.launch(Dispatchers.IO) {
            try {
                friendRepository.addFriend(user)
            } catch (e: Exception) {
                Log.d(TAG, e.message?: "unknown")
            }
        }
    }

    fun searchUserWithEmail(email: String) {
        job?.cancel()
        job = viewModelScope.launch {
            searchUserWithEmailDb(email)
        }
    }

    private suspend fun searchUserWithEmailDb(email: String) {
        friendRepository.getFriendByEmail(email).collect {
            Log.d(TAG, "Masuk1")
            _added.value = it != null
            if (it != null) {
                _user.value = State.success(it)
            } else {
                searchUserWithEmailFirebase(email)
            }
        }
    }

    private suspend fun searchUserWithEmailFirebase(email: String) {
        repository.getUserByEmail(email).collect {
            _user.value = it
        }
    }
}