package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Entity(tableName = "user_table", indices= [Index(value = ["email"], unique = true)])
@Serializable
data class User (
    @SerialName("uid") @PrimaryKey @ColumnInfo(name = "uid") val uid: String,
    @SerialName("email") @ColumnInfo(name = "email") val email: String,
    @SerialName("displayName") @ColumnInfo(name = "display_name") val displayName: String,
    @Transient @ColumnInfo(name = "room_uid") val roomUid: Int? = null,
)
