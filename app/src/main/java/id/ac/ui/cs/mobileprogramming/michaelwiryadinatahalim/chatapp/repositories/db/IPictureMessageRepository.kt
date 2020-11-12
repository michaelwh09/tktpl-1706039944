package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db

import android.net.Uri
import java.io.InputStream

interface IPictureMessageRepository {
    suspend fun sendPicture(roomUid: Long, photoUri: Uri, receiverUid: String, inputStream: InputStream)
}
