package com.hpdev.bangkitcapstone.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

import com.hpdev.bangkitcapstone.db.DatabaseContract.ChatHistoryColumns

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbgithubuser"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_USER_FAVORITES = "CREATE TABLE ${ChatHistoryColumns.TABLE_NAME}" +
                " (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${ChatHistoryColumns.MESSAGE} TEXT NOT NULL," +
                " ${ChatHistoryColumns.TYPE} INTEGER NOT NULL," +
                " ${ChatHistoryColumns.CREATED_AT} INTEGER NOT NULL," +
                " ${ChatHistoryColumns.NICKNAME} TEXT NOT NULL," +
                " ${ChatHistoryColumns.PROFILE_URL} TEXT NOT NULL," +
                " ${ChatHistoryColumns.USER_ID} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER_FAVORITES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ChatHistoryColumns.TABLE_NAME}")
        onCreate(db)
    }
}