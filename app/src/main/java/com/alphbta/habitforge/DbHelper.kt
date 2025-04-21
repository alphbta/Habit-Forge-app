package com.alphbta.habitforge

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "habitForgeDB", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val tasks = """CREATE TABLE tasks (
                id INTEGER PRIMARY KEY AUTOINCREMENT, 
                title TEXT NOT NULL, 
                note TEXT,
                isDone INTEGER NOT NULL DEFAULT 0, 
                subtasks TEXT, 
                difficulty TEXT NOT NULL DEFAULT 'easy', 
                stat TEXT NOT NULL, 
                tags TEXT, deadline TEXT, 
                reminder TEXT
            )"""
        val repetitiveTasks = """CREATE TABLE repetitive_tasks (
                id INTEGER PRIMARY KEY AUTOINCREMENT, 
                title TEXT NOT NULL, 
                isDone INTEGER NOT NULL DEFAULT 0, 
                subtasks TEXT, 
                difficulty TEXT NOT NULL DEFAULT 'easy', 
                stat TEXT NOT NULL, 
                tags TEXT, deadline TEXT, 
                startDate TEXT NOT NULL, 
                lastUpdated TEXT, 
                repeatType TEXT NOT NULL, 
                doneCount INTEGER DEFAULT 0, 
                missedCount INTEGER DEFAULT 0
            )"""
        val habits = """CREATE TABLE habits (
                id INTEGER PRIMARY KEY AUTOINCREMENT, 
                title TEXT NOT NULL, 
                isDone INTEGER NOT NULL DEFAULT 0, 
                subtasks TEXT, 
                difficulty TEXT NOT NULL DEFAULT 'easy', 
                stat TEXT NOT NULL, 
                tags TEXT, deadline TEXT, 
                startDate TEXT NOT NULL, 
                lastUpdated TEXT, 
                isPositive INTEGER NOT NULL DEFAULT 1, 
                streak INTEGER DEFAULT 0, 
                doneCount INTEGER DEFAULT 0, 
                missedCount INTEGER DEFAULT 0
            )"""
        db!!.execSQL(tasks)
        db.execSQL(repetitiveTasks)
        db.execSQL(habits)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS tasks")
        db.execSQL("DROP TABLE IF EXISTS repetitive_tasks")
        db.execSQL("DROP TABLE IF EXISTS habits")
        onCreate(db)
    }
}