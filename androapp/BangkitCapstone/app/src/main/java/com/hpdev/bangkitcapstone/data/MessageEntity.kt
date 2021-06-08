package com.hpdev.bangkitcapstone.data

data class MessageEntity (
    val message: String,
    val createdAt: Long,
    val type: Int,

    // sender data
    val userId: Int,
    val nickname: String,
    val profileUrl: String
)