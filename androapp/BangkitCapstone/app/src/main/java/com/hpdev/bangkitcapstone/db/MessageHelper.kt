package com.hpdev.bangkitcapstone.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.hpdev.bangkitcapstone.data.MessageEntity
import com.hpdev.bangkitcapstone.db.DatabaseContract.ChatHistoryColumns.Companion.CREATED_AT
import com.hpdev.bangkitcapstone.db.DatabaseContract.ChatHistoryColumns.Companion.MESSAGE
import com.hpdev.bangkitcapstone.db.DatabaseContract.ChatHistoryColumns.Companion.NICKNAME
import com.hpdev.bangkitcapstone.db.DatabaseContract.ChatHistoryColumns.Companion.PROFILE_URL
import com.hpdev.bangkitcapstone.db.DatabaseContract.ChatHistoryColumns.Companion.TABLE_NAME
import com.hpdev.bangkitcapstone.db.DatabaseContract.ChatHistoryColumns.Companion.TYPE
import com.hpdev.bangkitcapstone.db.DatabaseContract.ChatHistoryColumns.Companion.USER_ID
import java.sql.SQLException

class MessageHelper(context: Context) {
    private val dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: MessageHelper? = null
        fun getInstance(context: Context): MessageHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MessageHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun getAllData(): ArrayList<MessageEntity> {
        val cursor = database.query(TABLE_NAME, null, null, null, null, null, "$CREATED_AT ASC", null)
        cursor.moveToFirst()
        val messageList = ArrayList<MessageEntity>()
        var message: MessageEntity
        if (cursor.count > 0) {
            do {
                message = MessageEntity(
                    message = cursor.getString(cursor.getColumnIndexOrThrow(MESSAGE)),
                    createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(CREATED_AT)),
                    type = cursor.getInt(cursor.getColumnIndexOrThrow(TYPE)),

                    userId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)),
                    nickname = cursor.getString(cursor.getColumnIndexOrThrow(NICKNAME)),
                    profileUrl = cursor.getString(cursor.getColumnIndexOrThrow(PROFILE_URL)),
                )
                messageList.add(message)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return messageList
    }

    fun getByUserId(userId: Int): ArrayList<MessageEntity> {
        val cursor = database.query(TABLE_NAME, null, "$USER_ID = ?", arrayOf(userId.toString()), null, null, "$CREATED_AT ASC", null)
        cursor.moveToFirst()
        val messageList = ArrayList<MessageEntity>()
        var message: MessageEntity
        if (cursor.count > 0) {
            do {
                message = MessageEntity(
                    message = cursor.getString(cursor.getColumnIndexOrThrow(MESSAGE)),
                    createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(CREATED_AT)),
                    type = cursor.getInt(cursor.getColumnIndexOrThrow(TYPE)),

                    userId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)),
                    nickname = cursor.getString(cursor.getColumnIndexOrThrow(NICKNAME)),
                    profileUrl = cursor.getString(cursor.getColumnIndexOrThrow(PROFILE_URL)),
                )
                messageList.add(message)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return messageList
    }

    fun queryByUserId(userId: String): Cursor {
        return database.query(DATABASE_TABLE, null, "$USER_ID = ?", arrayOf(userId), null, null, null, null)
    }

    fun insert(message: MessageEntity): Long {
        val initialValues = ContentValues()
        initialValues.put(MESSAGE, message.message)
        initialValues.put(CREATED_AT, message.createdAt)
        initialValues.put(TYPE, message.type)

        initialValues.put(USER_ID, message.userId)
        initialValues.put(NICKNAME, message.nickname)
        initialValues.put(PROFILE_URL, message.profileUrl)

        return database.insert(TABLE_NAME, null, initialValues)
    }

    fun deleteByUserId(userId: String): Int {
        return database.delete(DATABASE_TABLE, "$USER_ID = '$userId'", null)
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$NICKNAME ASC")
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$USER_ID = ?", arrayOf(id))
    }
}