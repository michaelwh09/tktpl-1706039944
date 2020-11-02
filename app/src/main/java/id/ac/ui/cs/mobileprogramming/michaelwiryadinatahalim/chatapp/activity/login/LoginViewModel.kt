package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.activity.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.FcmRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.LoginRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val fcmRepository: FcmRepository,
) : ViewModel() {


    private val _authResult = MutableLiveData<State<AuthResult>>()

    val authResult : LiveData<State<AuthResult>> = _authResult

    private var job: Job? = null

    init {
        _authResult.value = State.init()
    }

    fun signIn(credential: AuthCredential) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            loginRepository.signInWithCredentialGoogle(credential).collect {
                when (it) {
                    is State.Success -> {
                        fcmRepository.enableFcm()
                    }
                    else -> {}
                }
                _authResult.postValue(it)
            }
        }
    }

}

