package com.test.android74_categorymemo


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context:Context) : SQLiteOpenHelper(context, "Memo.db", null, 1) {

    override fun onCreate(sqliteDatabase: SQLiteDatabase?) {

        // 디비 내 비밀번호 테이블 생성(번호,카테고리 이름)
        val sql = """create table PwdTable
            (idx integer primary key autoincrement,
            pwd text not null)
        """.trimIndent()

        // 디비 내 카테고리 테이블 생성(번호,카테고리 이름)
        val sql2 = """create table CategoryTable
            (idx integer primary key autoincrement ,
            categoryName text not null)
        """.trimIndent()

        // 디비 내 메모 테이블 생성(번호,제목,내용,날짜)
        val sql3 = """create table MemoTable
            (idx integer primary key autoincrement,
            categoryIdx integer not null,
            title text not null,
            content text not null,
            dateData date not null,
            foreign key (categoryIdx) references CategoryTable(idx))
        """.trimIndent()


        sqliteDatabase?.execSQL(sql)
        sqliteDatabase?.execSQL(sql2)
        sqliteDatabase?.execSQL(sql3)
    }


    override fun onUpgrade(sqliteDatabase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}