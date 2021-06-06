package com.hpdev.bangkitcapstone.data

data class MessageEntity (
    val message: String,
    val createdAt: Long,
    val type: Int,

    // userData
    val userId: String,
    val nickname: String,
    val profileUrl: String
)