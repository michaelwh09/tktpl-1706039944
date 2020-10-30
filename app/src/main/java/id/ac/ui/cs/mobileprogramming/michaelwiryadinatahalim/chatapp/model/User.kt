package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "user_table")
@Serializable
data class User (
    @SerialName("uid") @PrimaryKey @ColumnInfo(name = "uid") val uid: String,
    @SerialName("email") @ColumnInfo(name = "email") val email: String,
    @SerialName("displayName") @ColumnInfo(name = "display_name") val displayName: String
)
