package com.hpdev.bangkitcapstone.ui.messages

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hpdev.bangkitcapstone.data.MessageEntity
import com.hpdev.bangkitcapstone.data.UserEntity
import com.hpdev.bangkitcapstone.databinding.ActivityChannelBinding
import com.hpdev.bangkitcapstone.db.MessageHelper
import com.hpdev.bangkitcapstone.db.UserHelper
import java.lang.Exception

class ChannelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChannelBinding

    companion object {
        const val EXTRA_CHANNEL_USER_ID = "extra_channel_user_id"
        const val CHATBOT_USER_ID = 0
    }

    private val messageList = ArrayList<MessageEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // check if db initialized
        checkAndInitDB()

        val extras = intent.extras
        if (extras != null) {
            val userId = extras.getInt(EXTRA_CHANNEL_USER_ID)
            val user = getUserEntity(userId)

            if (user != null) {
                // get chat history from db
                getAndAddChatHistory(userId)

                val adapter = MessageListAdapter()
                adapter.setMessages(messageList)

                binding.recyclerGchat.layoutManager = LinearLayoutManager(this)
                binding.recyclerGchat.adapter = adapter

                binding.buttonGchatSend.setOnClickListener {
                    // get text from edit text
                    val newText = binding.editGchatMessage.text.toString()

                    // create message entity
                    val message = MessageEntity(
                        message = newText,
                        createdAt = System.currentTimeMillis(),
                        type = 0,

                        userId = userId,
                        nickname = user.nickname,
                        profileUrl = user.profileUrl
                    )

                    // add message entity to message list
                    messageList.add(message)

                    // add message to adapter
                    adapter.setMessages(messageList)

                    // save message to database
                    saveMessageToHistory(message)

                    // reset edit text
                    binding.editGchatMessage.text = null
                }
            } else {
                // back to prev activity
                Toast.makeText(this@ChannelActivity, "Error, user not found.", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            // back to prev activity
            Toast.makeText(this@ChannelActivity, "Something wrong", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun checkAndInitDB() {
        try {
            val userHelper = UserHelper.getInstance(applicationContext)
            userHelper.open()

            val isUserExist = userHelper.isUserExist()
            if (!isUserExist) {
                // show toast
                Toast.makeText(this@ChannelActivity, "Initializing chat bot...", Toast.LENGTH_SHORT).show()

                // if there is no user, init DB
                val chatBot = UserEntity(
                        nickname = "Chatbot",
                        profileUrl = "https://icon-library.com/images/robot-flat-icon/robot-flat-icon-29.jpg",
                        id = 0
                )

                userHelper.insertWithId(chatBot)
            }
        } catch (e: Exception) {
            Toast.makeText(this@ChannelActivity, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun getAndAddChatHistory(userId: Int) {
        try {
            val messageHelper = MessageHelper.getInstance(applicationContext)
            messageHelper.open()

            val newMessageList = messageHelper.getByUserId(userId)
            messageHelper.close()

            messageList.addAll(newMessageList)
        } catch (e: Exception) {
            Toast.makeText(this@ChannelActivity, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun getUserEntity(userId: Int) : UserEntity? {
        try {
            val userHelper = UserHelper.getInstance(applicationContext)
            userHelper.open()

            val user = userHelper.getByUserId(userId)
            userHelper.close()

            return user
        } catch (e: Exception) {
            Toast.makeText(this@ChannelActivity, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()

            return null
        }
    }

    private fun saveMessageToHistory(messageEntity: MessageEntity) {
        try {
            val messageHelper = MessageHelper.getInstance(applicationContext)
            messageHelper.open()

            messageHelper.insert(messageEntity)
            messageHelper.close()
        } catch (e: Exception) {
            Toast.makeText(this@ChannelActivity, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
