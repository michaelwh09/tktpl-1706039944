package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.activity.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.FcmRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.LoginRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.UserFirestoreRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val fcmRepository: FcmRepository,
    private val userFirestoreRepository: UserFirestoreRepository
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
                        getFcmToken(it)
                    }
                    else -> _authResult.postValue(it)
                }
            }
        }
    }

    private suspend fun getFcmToken(authResult: State.Success<AuthResult>) {
        val user = authResult.data.user
        if (user != null) {
            fcmRepository.getFcmToken().collect {
                when (it) {
                    is State.Success -> {
                        saveTokenToFirestore(it, user, authResult)
                    }
                    is State.Failed -> _authResult.postValue(State.failed(it.throwable))
                    else -> {}
                }
            }
        } else {
            _authResult.postValue(State.failed(RuntimeException("User null")))
        }
    }

    private suspend fun saveTokenToFirestore(
        it1: State.Success<String>,
        user: FirebaseUser,
        authResult: State.Success<AuthResult>
    ) {
        userFirestoreRepository.addTokenToUserFirestore(it1.data, user).collect {
            when (it) {
                is State.Success -> {
                    _authResult.postValue(authResult)
                }
                is State.Failed -> _authResult.postValue(State.failed(it.throwable))
                else -> {}
            }
        }
    }
}

