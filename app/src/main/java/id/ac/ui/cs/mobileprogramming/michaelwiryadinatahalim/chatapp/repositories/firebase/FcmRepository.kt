package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class FcmRepository {
    private val fcm = Firebase.messaging

    fun getFcmToken() : Flow<State<String>> = flow {
        emit(State.loading())
        emit(State.success(fcm.token.await()))
    }.catch {
        emit(State.failed(it))
    }.flowOn(Dispatchers.IO)
}
