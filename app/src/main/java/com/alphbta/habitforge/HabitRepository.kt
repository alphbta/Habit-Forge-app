package com.alphbta.habitforge

import android.content.ContentValues
import android.util.Log

class HabitRepository(private val dbHelper: DbHelper) {
    fun addHabit(habit: Habit) : Boolean {
        val values = ContentValues().apply {
            put("title", habit.title)
            put("note", habit.note)
            put("isDone", if (habit.isDone) 1 else 0)
            put("subtasks", "")
            put("difficulty", habit.difficulty)
            put("stat", habit.stat)
            put("tags", "")
            put("lastUpdated", habit.lastUpdated)
            put("streak", 0)
            put("doneCount", 0)
            put("missedCount", 0)
        }
        val db = dbHelper.writableDatabase
        val result = db.insert("habits", null, values)
        Log.d("DB_INSERT", "inser result: $result")
        return result != -1L
    }

    fun getAllHabits(): List<Habit> {
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM habits", null)
        val habits = mutableListOf<Habit>()

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val note = cursor.getString(cursor.getColumnIndexOrThrow("note"))
                val isDone = cursor.getInt(cursor.getColumnIndexOrThrow("isDone")) == 1
                val subtasks = null
                val difficulty = cursor.getString(cursor.getColumnIndexOrThrow("difficulty"))
                val stat = cursor.getString(cursor.getColumnIndexOrThrow("stat"))
                val tags = null
                val lastUpdated = cursor.getString(cursor.getColumnIndexOrThrow("lastUpdated"))
                val streak = cursor.getInt(cursor.getColumnIndexOrThrow("streak"))
                val doneCount = cursor.getInt(cursor.getColumnIndexOrThrow("doneCount"))
                val missedCount = cursor.getInt(cursor.getColumnIndexOrThrow("missedCount"))

                val habit = Habit(
                    id,
                    title,
                    note,
                    isDone,
                    subtasks,
                    difficulty,
                    stat,
                    tags,
                    lastUpdated,
                    streak,
                    doneCount,
                    missedCount
                )
                habits.add(habit)
                cursor.moveToNext()
            }
        }

        cursor.close()
        db.close()

        return habits
    }

    fun deleteHabit(_id: String): Boolean {
        val db = dbHelper.writableDatabase
        val result = db.delete("habits", "id=?", arrayOf(_id))
        db.close()
        return result != -1
    }
}