package service

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService

class MessageFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val db = Firebase.firestore
        val auth = Firebase.auth
        val data = hashMapOf(
            "fcm_token" to token
        )
        auth.currentUser?.let {
            db.collection("users").document(it.uid).set(data)
            Log.d(TAG, "Refreshed token FCM: $token")
        }
    }
}
