package com.hpdev.bangkitcapstone.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.hpdev.bangkitcapstone"
    const val SCHEME = "content"

    internal class ChatHistoryColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "chat_history"
            const val USER_ID = "user_id"
            const val MESSAGE = "message"
            const val TYPE = "type"
            const val CREATED_AT = "created_at"
            const val NICKNAME = "nickname"
            const val PROFILE_URL = "profile_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "users"
            const val _ID = "_id"
            const val NICKNAME = "nickname"
            const val PROFILE_URL = "profile_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}