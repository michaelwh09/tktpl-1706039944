package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

class SearchEmailViewModel : ViewModel() {

    private var functions: FirebaseFunctions = Firebase.functions("asia-southeast2")

    private val _user = MutableLiveData<HashMap<String, String>?>()
    val user: LiveData<HashMap<String, String>?> = _user

    companion object {
        private const val TAG = "SearchEmailViewModel"
    }

    fun searchUserWithEmail(email: String) {
        getUserByEmail(email).addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                val e = task.exception

                // [START_EXCLUDE]
                Log.w(TAG, "getUserByEmail:onFailure", e)
                return@OnCompleteListener
                // [END_EXCLUDE]
            }

            // [START_EXCLUDE]
            val result = task.result
            if (result != null) {
                _user.value = result as HashMap<String, String>?
            }
        })
    }

    private fun getUserByEmail(email: String): Task<HashMap<*, *>?> {
        val data = hashMapOf(
            "email" to email
        )
        return functions
            .getHttpsCallable("getUserByEmail")
            .call(data)
            .continueWith { task -> task.result?.data as HashMap<*, *>? }
    }
}