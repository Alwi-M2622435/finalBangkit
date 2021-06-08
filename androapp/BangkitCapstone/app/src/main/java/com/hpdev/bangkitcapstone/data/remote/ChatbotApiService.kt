package com.hpdev.bangkitcapstone.data.remote

import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ChatbotApiService {
    @FormUrlEncoded
    @POST("/")
    fun postUserInput(@FieldMap params: HashMap<String, String>): Call<ResponseChatbot>
}