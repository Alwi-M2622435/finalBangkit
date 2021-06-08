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
import com.hpdev.bangkitcapstone.R
import com.hpdev.bangkitcapstone.data.ImageEntity
import com.hpdev.bangkitcapstone.ui.notifications.NotificationsActivity


class MainActivity : AppCompatActivity() {

    private var imageList = ArrayList<ImageEntity>()
    private lateinit var rv: RecyclerView
    private lateinit var adapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById<View>(R.id.rec) as RecyclerView
        rv.setHasFixedSize(true)
        rv.layoutManager = GridLayoutManager(this, 2)

        getData()
    }

    private fun getData() {
        imageList.add(ImageEntity(image = R.drawable.menu_chatbot))
        imageList.add(ImageEntity(image = R.drawable.menu_forum))
        imageList.add(ImageEntity(image = R.drawable.menu_psycholog))
        imageList.add(ImageEntity(image = R.drawable.menu_info))

        adapter = MenuAdapter(imageList, this)
        rv.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.menu1 -> {
//                val i = Intent(this, ChannelActivity::class.java)
//                i.putExtra(ChannelActivity.EXTRA_CHANNEL_USER_ID, ChannelActivity.CHATBOT_USER_ID)
//                startActivity(i)
//                true
//            }
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