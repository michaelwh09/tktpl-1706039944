package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import kotlinx.android.synthetic.main.main_activity.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (navFragment != null) {
            val navController = navFragment.findNavController()
            navController.addOnDestinationChangedListener { _, destination, _ ->
                bottomNavigation.isVisible = (destination.id == R.id.first_fragment
                        || destination.id == R.id.room_fragment)
            }
            NavigationUI.setupWithNavController(bottomNavigation, navController)
        }
    }
}
