package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase

import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.User
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json

@ExperimentalCoroutinesApi
class FunctionRepository {
    private var functions: FirebaseFunctions = Firebase.functions("asia-southeast2")

    fun getUserByEmail(email : String): Flow<State<User>> = flow {
        emit(State.loading())
        val data = hashMapOf(
            "email" to email
        )
        val userString = functions
            .getHttpsCallable("getUserByEmail")
            .call(data)
            .continueWith { task -> task.result?.data as String? }
            .await()

        if (userString == null) {
            emit(State.failed(RuntimeException("User not found")))
            return@flow
        }
        val user = Json{ignoreUnknownKeys = true}.decodeFromString(User.serializer(), userString)

        emit(State.success(user))
    }.catch {
        emit(State.failed(it))
    }.flowOn(Dispatchers.IO)

    suspend fun sendMessageToUser(receiverUid: String, message: String): Boolean {
        val data = hashMapOf(
            "receiverUid" to receiverUid,
            "message" to message,
            "isImage" to "false"
        )
        return functions
            .getHttpsCallable("sendMessageToUser")
            .call(data)
            .continueWith {task -> task.result?.data as Boolean}
            .await()
    }

    suspend fun sendPictureToUser(receiverUid: String, path: String): Boolean {
        val data = hashMapOf(
            "receiverUid" to receiverUid,
            "message" to path,
            "isImage" to "true"
        )
        return functions
            .getHttpsCallable("sendMessageToUser")
            .call(data)
            .continueWith {task -> task.result?.data as Boolean}
            .await()
    }
}
