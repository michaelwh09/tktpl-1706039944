package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User (
    @SerialName("uid") val uid: String,
    @SerialName("email") val email: String,
    @SerialName("displayName") val displayName: String
)
