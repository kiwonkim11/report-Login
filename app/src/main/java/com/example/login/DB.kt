package com.example.login

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(context: Context) : SQLiteOpenHelper(context, "Login.db", null, 1) {

    // User 테이블 생성
    override fun onCreate(MyDB: SQLiteDatabase?) {
        MyDB!!.execSQL("create Table users(id TEXT primary key, password TEXT, phone TEXT)")
    }

    // 정보 갱신
    override fun onUpgrade(MyDB: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        MyDB!!.execSQL("drop Table if exists users")
    }

    // 정보 삽입 (성공시 true, 실패시 false)
    fun insertData(id: String?, password: String?, phone: String?): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", id)
        contentValues.put("password", password)
        contentValues.put("phone", phone)

        val result = MyDB.insert("users", null, contentValues)
        MyDB.close()
        return if(result == -1L) false else true
    }
}