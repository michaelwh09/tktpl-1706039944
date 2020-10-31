package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.activity.MainActivity
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils.State
import kotlinx.android.synthetic.main.login_activity.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
        loggedIn(auth.currentUser)
        setContentView(R.layout.login_activity)
        loginViewModel.authResult.observe(this, {
            when (it) {
                is State.Loading -> showProgressBar()
                is State.Success -> {
                    hideProgressBar()
                    loggedIn(auth.currentUser)
                }
                is State.Failed -> {
                    Log.w(TAG, "signInWithCredential:failure", it.throwable)
                    Snackbar.make(login_activity, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                }
                is State.Initialized -> hideProgressBar()
            }
        })
    }

    private fun loggedIn(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun showProgressBar() {
        login_button.visibility = View.INVISIBLE
        loginProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        login_button.visibility = View.VISIBLE
        loginProgressBar.visibility = View.INVISIBLE
    }

    private val signInResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                loginViewModel.signIn(credential)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    fun signIn(view: View?) {
        val signInIntent = googleSignInClient.signInIntent
        signInResult.launch(signInIntent)
    }
}
