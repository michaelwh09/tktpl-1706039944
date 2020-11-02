package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFirestoreRepository {
    private val db = Firebase.firestore

    fun addTokenToUserFirestore(token: String, user: FirebaseUser)  {
        val data = hashMapOf(
            "fcm_token" to token
        )
        db.collection("users").document(user.uid).set(data)
    }
}
