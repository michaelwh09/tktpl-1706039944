package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
