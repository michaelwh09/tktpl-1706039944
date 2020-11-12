package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class FcmRepository {
    private val fcm = Firebase.messaging
    private val analytic = Firebase.analytics

    fun enableFcm() {
        fcm.isAutoInitEnabled = true
        analytic.setAnalyticsCollectionEnabled(true)
    }

    fun disableFcm() {
        fcm.isAutoInitEnabled = false
    }

    fun deleteInstanceId() {
        FirebaseInstanceId.getInstance().deleteInstanceId()
    }
}
