package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase

import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json

@ExperimentalCoroutinesApi
class UserRepository {
    private var functions: FirebaseFunctions = Firebase.functions("asia-southeast2")

    suspend fun getUserByEmail(email : String): User {
        val data = hashMapOf(
            "email" to email
        )
        val userString = functions
            .getHttpsCallable("getUserByEmail")
            .call(data)
            .continueWith { task -> task.result?.data as String }
            .await()

        return Json{ignoreUnknownKeys = true}.decodeFromString(User.serializer(), userString)
    }
}
