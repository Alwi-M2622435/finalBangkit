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
        private const val DATABASE_TABLE = DatabaseContract.UserColumns.TABLE_NAME

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
        val cursor = database.query(DatabaseContract.UserColumns.TABLE_NAME, null, null, null, null, null, "${DatabaseContract.UserColumns._ID} ASC", null)
        cursor.moveToFirst()
        val userList = ArrayList<UserEntity>()
        var user: UserEntity
        if (cursor.count > 0) {
            do {
                user = UserEntity(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns._ID)),
                    nickname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.NICKNAME)),
                    profileUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.PROFILE_URL)),
                )
                userList.add(user)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return userList
    }

    fun getByUserId(userId: Int): UserEntity? {
        val cursor = database.query(DatabaseContract.UserColumns.TABLE_NAME, null, "${DatabaseContract.UserColumns._ID} = ?", arrayOf(userId.toString()), null, null, "${DatabaseContract.UserColumns._ID} ASC", null)
        cursor.moveToFirst()
        var user: UserEntity? = null
        if (cursor.count > 0) {
            do {
                user = UserEntity(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns._ID)),
                    nickname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.NICKNAME)),
                    profileUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.PROFILE_URL)),
                )
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()

        return user
    }

    fun isUserExist(): Boolean {
        val cursor = queryAll()
        cursor.moveToFirst()

        return (cursor.count > 0)
    }

    fun queryByUserId(userId: Int): Cursor {
        return database.query(DATABASE_TABLE, null, "${DatabaseContract.UserColumns._ID} = ?", arrayOf(userId.toString()), null, null, null, null)
    }

    fun insert(user: UserEntity): Long {
        val initialValues = ContentValues()
        initialValues.put(DatabaseContract.UserColumns.NICKNAME, user.nickname)
        initialValues.put(DatabaseContract.UserColumns.PROFILE_URL, user.profileUrl)

        return database.insert(DatabaseContract.UserColumns.TABLE_NAME, null, initialValues)
    }

    fun insertWithId(user: UserEntity): Long {
        val initialValues = ContentValues()
        initialValues.put(DatabaseContract.UserColumns._ID, user.id)
        initialValues.put(DatabaseContract.UserColumns.NICKNAME, user.nickname)
        initialValues.put(DatabaseContract.UserColumns.PROFILE_URL, user.profileUrl)

        return database.insert(DatabaseContract.UserColumns.TABLE_NAME, null, initialValues)
    }

    fun deleteByUserId(userId: Int): Int {
        return database.delete(DATABASE_TABLE, "${DatabaseContract.UserColumns._ID} = '$userId'", null)
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.UserColumns.NICKNAME} ASC")
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: Int, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "${DatabaseContract.UserColumns._ID} = ?", arrayOf(id.toString()))
    }
}