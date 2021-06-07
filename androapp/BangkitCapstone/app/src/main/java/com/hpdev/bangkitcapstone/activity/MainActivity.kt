package com.hpdev.bangkitcapstone.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.hpdev.bangkitcapstone.ui.messages.ChannelActivity
import com.hpdev.bangkitcapstone.R
import com.hpdev.bangkitcapstone.ui.notifications.NotificationsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard, R.id.navigation_messages, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                val i = Intent(this, ChannelActivity::class.java)
                i.putExtra(ChannelActivity.EXTRA_CHANNEL_USER_ID, ChannelActivity.CHATBOT_USER_ID)
                startActivity(i)
                true
            }
            R.id.menu2 -> {
                val i = Intent(this, NotificationsActivity::class.java)
                startActivity(i)
                true
            }
            R.id.menu3 -> {
                // TODO: Logout user
                println("Logging out...")
                true
            }
            else -> true
        }
    }
}