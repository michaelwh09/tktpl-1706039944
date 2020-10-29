package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.UserRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchEmailViewModel @ViewModelInject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    private val _user = MutableLiveData<State<User>>()
    var user: LiveData<State<User>> = _user

    private var job: Job? = null

    init {
        _user.value = State.init()
    }

    fun searchUserWithEmail(email: String) {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getUserByEmail(email).collect {
                _user.value = it
            }
        }
    }
}
