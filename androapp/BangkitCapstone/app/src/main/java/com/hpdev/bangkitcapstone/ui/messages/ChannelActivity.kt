package com.hpdev.bangkitcapstone.ui.messages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hpdev.bangkitcapstone.data.MessageEntity
import com.hpdev.bangkitcapstone.data.UserEntity
import com.hpdev.bangkitcapstone.databinding.ActivityChannelBinding

class ChannelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChannelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chatBot = UserEntity(
            nickname = "Chatbot",
            profileUrl = "https://www.automate.org/userAssets/a3/a3Uploads/emerging-markets/images/robotics/humanoid.jpg"
        )

        val user = UserEntity(
            nickname = "Hendry",
            profileUrl = "https://i.mydramalist.com/1kymd_5f.jpg"
        )

        val messageList = ArrayList<MessageEntity>()
        messageList.add(
            MessageEntity(
                message = "Halo",
                sender = chatBot,
                createdAt = 1622958900000,
                type = 1
            )
        )
        messageList.add(
            MessageEntity(
                message = "Halo juga",
                sender = user,
                createdAt = 1622958960000,
                type = 0
            )
        )

        val adapter = MessageListAdapter()
        adapter.setMessages(messageList)

        binding.recyclerGchat.layoutManager = LinearLayoutManager(this)
        binding.recyclerGchat.adapter = adapter

    }
}
