package com.alphbta.habitforge

import android.content.ContentValues

class HabitRepository(private val dbHelper: DbHelper) {
    fun addHabit(habit: Habit) {
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
            put("targetDays", habit.targetDays)
            put("currentDay", habit.currentDay)
            put("lastCompletionDate", habit.lastCompletionDate)

        }
        val db = dbHelper.writableDatabase
        db.insert("habits", null, values)
    }
    fun updateHabitProgress(habit: Habit) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("currentDay", habit.currentDay)
            put("lastCompletionDate", habit.lastCompletionDate)
        }
        db.update("habits", values, "id = ?", arrayOf(habit.id.toString()))
        db.close()
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
                val targetDays = cursor.getInt(cursor.getColumnIndexOrThrow("targetDays"))
                val currentDay = cursor.getInt(cursor.getColumnIndexOrThrow("currentDay"))
                val lastCompletionDate = cursor.getString(cursor.getColumnIndexOrThrow("lastCompletionDate"))

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
                    missedCount,
                    targetDays,
                    currentDay,
                    lastCompletionDate
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