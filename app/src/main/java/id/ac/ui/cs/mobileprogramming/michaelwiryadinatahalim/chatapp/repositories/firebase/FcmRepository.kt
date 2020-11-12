package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

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
