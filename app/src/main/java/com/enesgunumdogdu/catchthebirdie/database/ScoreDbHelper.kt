package com.enesgunumdogdu.catchthebirdie.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ScoreDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "scoreDb"
        private const val TABLE_SCORES = "scores"
        private const val KEY_ID = "_id"
        private const val KEY_SCORE = "score"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable =
            "CREATE TABLE $TABLE_SCORES($KEY_ID INTEGER PRIMARY KEY,$KEY_SCORE INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SCORES")
        onCreate(db)
    }

    fun addScore(score: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_SCORE, score)
        db.insert(TABLE_SCORES, null, contentValues)
        db.close()
    }

    fun getHighestScore(): Int {
        var highestScore = 0
        val db = this.readableDatabase
        val query = "SELECT MAX($KEY_SCORE) FROM $TABLE_SCORES"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            highestScore = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return highestScore
    }
}