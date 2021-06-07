package com.hpdev.bangkitcapstone.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity (
    val id: Int,
    val nickname: String,
    val profileUrl: String
) : Parcelable