package com.alphbta.habitforge

import android.content.ContentValues
import android.content.Context
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.min

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
            put("currentDays", 0)
        }
        val db = dbHelper.writableDatabase
        db.insert("habits", null, values)
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
                val currentDays = cursor.getInt(cursor.getColumnIndexOrThrow("currentDays"))

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
                    currentDays
                )
                habits.add(habit)
                cursor.moveToNext()
            }
        }

        cursor.close()
        db.close()

        return habits
    }

    fun completeHabit(habit: Habit): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put("isDone", 1)
        values.put("streak", habit.streak + 1)
        values.put("currentDays", min(habit.currentDays + 1, habit.targetDays))
        values.put("doneCount", habit.doneCount + 1)
        val result = db.update("habits", values, "id=?", arrayOf(habit.id.toString()))
        db.close()
        return result != -1
    }

    fun checkHabitsForReset(context: Context) {
        val habits = getAllHabits()
        val db = dbHelper.writableDatabase
        val today = LocalDate.now()
        habits.forEach { habit ->
            val lastUpdated = LocalDate.parse(habit.lastUpdated)
            val daysPassed = ChronoUnit.DAYS.between(lastUpdated, today)

            if (daysPassed >= 1) {
                val values = ContentValues()

                if (!habit.isDone) {
                    val currentDays = if (habit.currentDays < habit.targetDays) 0 else habit.currentDays
                    values.put("currentDays", currentDays)
                    values.put("streak", 0)
                    values.put("missedCount", habit.missedCount + 1)
                }

                values.put("isDone", 0)
                values.put("lastUpdated", today.toString())

                db.update("habits", values, "id=?", arrayOf(habit.id.toString()))
            }
        }

        db.close()
    }
}