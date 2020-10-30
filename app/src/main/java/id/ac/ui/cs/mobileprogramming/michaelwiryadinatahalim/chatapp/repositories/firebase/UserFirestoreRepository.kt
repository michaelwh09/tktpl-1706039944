package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class UserFirestoreRepository {
    private val db = Firebase.firestore

    fun addTokenToUserFirestore(token: String, user: FirebaseUser) : Flow<State<Void>> = flow {
        emit(State.loading())
        val data = hashMapOf(
            "fcm_token" to token
        )
        emit(State.success(db.collection("users").document(user.uid).set(data).await()))
    }.catch {
        emit(State.failed(it))
    }.flowOn(Dispatchers.IO)
}
