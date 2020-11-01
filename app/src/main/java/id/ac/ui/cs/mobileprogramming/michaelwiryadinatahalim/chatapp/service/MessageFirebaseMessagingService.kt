package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.service

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.UserFirestoreRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MessageFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    private lateinit var userFirestoreRepository: UserFirestoreRepository

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val auth = Firebase.auth
        auth.currentUser?.let {
            saveTokenToFirestore(it, token)
            Log.d(TAG, "Refreshed token FCM: $token")
        }
    }

    private fun saveTokenToFirestore(
        user: FirebaseUser,
        token: String
    ) {
        userFirestoreRepository.addTokenToUserFirestore(token, user)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
    }
}
