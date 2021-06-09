package com.hpdev.bangkitcapstone.ui.channel

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hpdev.bangkitcapstone.data.MessageEntity
import com.hpdev.bangkitcapstone.data.UserEntity
import com.hpdev.bangkitcapstone.data.remote.ChatbotApiService
import com.hpdev.bangkitcapstone.data.remote.ResponseChatbot
import com.hpdev.bangkitcapstone.databinding.ActivityChannelBinding
import com.hpdev.bangkitcapstone.db.MessageHelper
import com.hpdev.bangkitcapstone.db.UserHelper
import com.hpdev.bangkitcapstone.ui.messages.MessageListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChannelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChannelBinding
    private lateinit var retrofit: Retrofit

    companion object {
        const val EXTRA_CHANNEL_USER_ID = "extra_channel_user_id"
        const val CHATBOT_USER_ID = 0
        const val BASE_URL = "https://getresponse-4abxtq7oia-et.a.run.app"
        const val PARAM_INPUT = "chat"

        const val TYPE_MY_MESSAGE = 0
        const val TYPE_FRIEND_MESSAGE = 1
    }

    private val messageList = ArrayList<MessageEntity>()
    private var adapter = MessageListAdapter()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // check if db initialized
        checkAndInitDB()

        // init retrofit
        initializeRetrofit()

        // init rv
        recyclerView = binding.recyclerGchat

        val extras = intent.extras
        if (extras != null) {
            val userId = extras.getInt(EXTRA_CHANNEL_USER_ID)
            val user = getUserEntity(userId)

            if (user != null) {
                // get chat history from db
                getAndAddChatHistory(userId)

                adapter.setMessages(messageList)

                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter

                // scroll down rv
                recyclerView.post {
                    recyclerView.scrollToPosition(adapter.itemCount - 1)
                }

                binding.buttonGchatSend.setOnClickListener {
                    // add and update message list with user new text
                    val newText = binding.editGchatMessage.text.toString()
                    addAndUpdateMessages(newText, user, TYPE_MY_MESSAGE)

                    // reset edit text
                    binding.editGchatMessage.text = null

                    // send message to API, get response, then
                    // add and update message list with chatbot new reply
                    postMessageToAPI(newText, user)
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

    private fun postMessageToAPI(input: String, user: UserEntity) {
        val params = HashMap<String, String>()
        params[PARAM_INPUT] = input

        val apiService = retrofit.create(ChatbotApiService::class.java)
        val client = apiService.postUserInput(params)

        client.enqueue(object : Callback<ResponseChatbot> {
            override fun onResponse(
                call: Call<ResponseChatbot>,
                response: Response<ResponseChatbot>
            ) {
                if (response.isSuccessful) {
                    val replyText = response.body()?.response
                    if (replyText != null) {
                        addAndUpdateMessages(replyText, user, TYPE_FRIEND_MESSAGE)
                    }
                } else {
                    println(response)
                }
            }

            override fun onFailure(call: Call<ResponseChatbot>, t: Throwable) {
                // show toast
                Toast.makeText(
                    this@ChannelActivity,
                    "Ada masalah saat menghubungi Chatbot",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }

        })
    }

    private fun initializeRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun addAndUpdateMessages(newText: String, user: UserEntity, type: Int) {
        // create message entity
        val message = MessageEntity(
            message = newText,
            createdAt = System.currentTimeMillis(),
            type = type,

            userId = user.id,
            nickname = user.nickname,
            profileUrl = user.profileUrl
        )

        // add message entity to message list
        messageList.add(message)

        // add message to adapter
        adapter.setMessages(messageList)

        // save message to database
        saveMessageToHistory(message)

        // scroll down rv
        recyclerView.post {
            recyclerView.scrollToPosition(adapter.itemCount - 1)
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
        return try {
            val userHelper = UserHelper.getInstance(applicationContext)
            userHelper.open()

            val user = userHelper.getByUserId(userId)
            userHelper.close()

            user
        } catch (e: Exception) {
            Toast.makeText(this@ChannelActivity, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()

            null
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
