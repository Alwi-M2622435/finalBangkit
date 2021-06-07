package com.hpdev.bangkitcapstone.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.hpdev.bangkitcapstone.data.UserEntity
import java.sql.SQLException

class UserHelper(context: Context) {
    private val dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.ChatHistoryColumns.TABLE_NAME

        private var INSTANCE: UserHelper? = null
        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
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

    fun getAllData(): ArrayList<UserEntity> {
        val cursor = database.query(DatabaseContract.UserColumns.TABLE_NAME, null, null, null, null, null, "${DatabaseContract.ChatHistoryColumns.CREATED_AT} ASC", null)
        cursor.moveToFirst()
        val userList = ArrayList<UserEntity>()
        var user: UserEntity
        if (cursor.count > 0) {
            do {
                user = UserEntity(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns._ID)),
                    nickname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ChatHistoryColumns.NICKNAME)),
                    profileUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ChatHistoryColumns.PROFILE_URL)),
                )
                userList.add(user)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return userList
    }

    fun getByUserId(userId: String): UserEntity? {
        val cursor = database.query(DatabaseContract.UserColumns.TABLE_NAME, null, null, null, null, null, "${DatabaseContract.ChatHistoryColumns.CREATED_AT} ASC", null)
        cursor.moveToFirst()
        var user: UserEntity? = null
        if (cursor.count > 0) {
            do {
                user = UserEntity(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns._ID)),
                    nickname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ChatHistoryColumns.NICKNAME)),
                    profileUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ChatHistoryColumns.PROFILE_URL)),
                )
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()

        return user
    }

    fun queryByUserId(userId: String): Cursor {
        return database.query(DATABASE_TABLE, null, "${DatabaseContract.ChatHistoryColumns.USER_ID} = ?", arrayOf(userId), null, null, null, null)
    }

    fun insert(user: UserEntity): Long {
        val initialValues = ContentValues()
        initialValues.put(DatabaseContract.ChatHistoryColumns.NICKNAME, user.nickname)
        initialValues.put(DatabaseContract.ChatHistoryColumns.PROFILE_URL, user.profileUrl)

        return database.insert(DatabaseContract.ChatHistoryColumns.TABLE_NAME, null, initialValues)
    }

    fun deleteByUserId(userId: String): Int {
        return database.delete(DATABASE_TABLE, "${DatabaseContract.ChatHistoryColumns.USER_ID} = '$userId'", null)
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.ChatHistoryColumns.NICKNAME} ASC")
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "${DatabaseContract.ChatHistoryColumns.USER_ID} = ?", arrayOf(id))
    }
}