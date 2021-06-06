package com.hpdev.bangkitcapstone.ui.messages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hpdev.bangkitcapstone.data.MessageEntity
import com.hpdev.bangkitcapstone.data.UserEntity
import com.hpdev.bangkitcapstone.databinding.ActivityChannelBinding

class ChannelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChannelBinding

    companion object {
        const val EXTRA_IS_CHATBOT = "extra_is_chatbot"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        var isChatbot = false
        if (extras != null) {
            isChatbot = extras.getBoolean(EXTRA_IS_CHATBOT)
        }

        // prepare message list
        val messageList = ArrayList<MessageEntity>()

        // if it's a chatbot, do this
        if (isChatbot) {
            val newMessageList = getChatbotHistory()
            messageList.addAll(newMessageList)
        }

        val adapter = MessageListAdapter()
        adapter.setMessages(messageList)

        binding.recyclerGchat.layoutManager = LinearLayoutManager(this)
        binding.recyclerGchat.adapter = adapter
    }

    private fun getChatbotHistory(): ArrayList<MessageEntity> {
        val messageList = ArrayList<MessageEntity>()

        // create chatbot entity
        val chatBot = UserEntity(
            id = "0",
            nickname = "Chatbot",
            profileUrl = "https://icon-library.com/images/robot-flat-icon/robot-flat-icon-29.jpg"
        )

        val user = UserEntity(
            id = "1",
            nickname = "Hendry",
            profileUrl = "https://i.mydramalist.com/1kymd_5f.jpg"
        )

        // retrieve chat bot history from DB
        messageList.add(
            MessageEntity(
                message = "Halo",
                createdAt = 1622958900000,
                type = 1,

                userId = chatBot.id,
                nickname = chatBot.nickname,
                profileUrl = chatBot.profileUrl
            )
        )
        messageList.add(
            MessageEntity(
                message = "Halo juga",
                createdAt = 1622958960000,
                type = 0,

                userId = user.id,
                nickname = user.nickname,
                profileUrl = user.profileUrl
            )
        )

        return messageList
    }
}
