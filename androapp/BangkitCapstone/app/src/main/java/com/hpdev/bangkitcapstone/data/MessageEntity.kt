package com.hpdev.bangkitcapstone.data

data class MessageEntity (
    val message: String,
    val sender: UserEntity,
    val createdAt: Long,
    val type: Int
)