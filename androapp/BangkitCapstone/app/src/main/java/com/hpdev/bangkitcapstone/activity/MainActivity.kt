package com.hpdev.bangkitcapstone.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hpdev.bangkitcapstone.R
import com.hpdev.bangkitcapstone.data.MenuEntity
import com.hpdev.bangkitcapstone.ui.channel.ChannelActivity
import com.hpdev.bangkitcapstone.ui.forum.ForumActivity
import com.hpdev.bangkitcapstone.ui.info.InfoActivity
import com.hpdev.bangkitcapstone.ui.notifications.NotificationsActivity
import com.hpdev.bangkitcapstone.ui.professional.ProfessionalActivity


class MainActivity : AppCompatActivity() {

    private var imageList = ArrayList<MenuEntity>()
    private lateinit var rv: RecyclerView
    private lateinit var adapter: MenuAdapter

    companion object {
        const val MENU_CHATBOT = "menu_chatbot"
        const val MENU_FORUM = "menu_forum"
        const val MENU_PSYCHOLOG = "menu_psycholog"
        const val MENU_INFO = "menu_info"
        const val MENU_PROFILE = "menu_profile"
        const val MENU_SETTINGS = "menu_settings"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById<View>(R.id.rec) as RecyclerView
        rv.setHasFixedSize(true)
        rv.layoutManager = GridLayoutManager(this, 2)

        getData()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val i = Intent(this@MainActivity, ChannelActivity::class.java)
                .apply {
                    putExtra(ChannelActivity.EXTRA_CHANNEL_USER_ID, ChannelActivity.CHATBOT_USER_ID)
                }
            // start activity, to chatbot page
            startActivity(i)
        }
    }

    private fun getData() {
        imageList.add(MenuEntity(image = R.drawable.menu_chatbot, type = MENU_CHATBOT))
        imageList.add(MenuEntity(image = R.drawable.menu_forum, type = MENU_FORUM))
        imageList.add(MenuEntity(image = R.drawable.menu_psycholog, type = MENU_PSYCHOLOG))
        imageList.add(MenuEntity(image = R.drawable.menu_info, type = MENU_INFO))
        imageList.add(MenuEntity(image = R.drawable.menu_profile, type = MENU_PROFILE))
        imageList.add(MenuEntity(image = R.drawable.menu_settings, type = MENU_SETTINGS))

        adapter = MenuAdapter(imageList, this)
        rv.adapter = adapter

        adapter.onItemClickCallback = (object : MenuAdapter.OnItemClickCallback {
            override fun onItemClick(typeMenu: String) {
                runOnUiThread {
                    when (typeMenu) {
                        MENU_CHATBOT -> {
                            val i = Intent(this@MainActivity, ChannelActivity::class.java)
                                .apply {
                                    putExtra(ChannelActivity.EXTRA_CHANNEL_USER_ID, ChannelActivity.CHATBOT_USER_ID)
                                }
                            // start activity, to chatbot page
                            startActivity(i)
                        }
                        MENU_FORUM -> {
                            val i = Intent(this@MainActivity, ForumActivity::class.java)

                            // start activity, to forum page
                            startActivity(i)
                        }
                        MENU_PSYCHOLOG -> {
                            val i = Intent(this@MainActivity, ProfessionalActivity::class.java)

                            // start activity, to professional page
                            startActivity(i)
                        }
                        MENU_INFO -> {
                            val i = Intent(this@MainActivity, InfoActivity::class.java)

                            // start activity, to info page
                            startActivity(i)
                        }
                        else -> {
                            // show toast message
                            Toast.makeText(this@MainActivity, "Fitur sedang dalam pengembangan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu2 -> {
                val i = Intent(this, NotificationsActivity::class.java)
                startActivity(i)
                true
            }
            R.id.menu3 -> {
                Toast.makeText(
                    this@MainActivity,
                    "Log out feature isn't available yet.",
                    Toast.LENGTH_SHORT
                ).show()
                println("Logging out...")
                true
            }
            else -> true
        }
    }
}