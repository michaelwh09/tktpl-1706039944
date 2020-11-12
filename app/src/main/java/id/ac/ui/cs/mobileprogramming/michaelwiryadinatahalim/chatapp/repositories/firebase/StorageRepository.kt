package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.InputStream
import java.lang.RuntimeException
import java.util.*

class StorageRepository {
    private val storage = Firebase.storage

    suspend fun uploadImage(receiverId: String, inputStream: InputStream): String {
        val storageReference = storage.reference
        val uuid = UUID.randomUUID().toString()
        val imageRef = storageReference.child("users/$receiverId/${uuid}.jpg")
        val task = imageRef.putStream(inputStream).await()
        task.error?.let {
            throw RuntimeException(it.message)
        }
        return imageRef.path
    }
}
