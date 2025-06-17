package com.alphbta.habitforge

import android.content.ContentValues
import android.content.Context

class TaskRepository(private val dbHelper: DbHelper) {
    fun addTask(task: Task) {
        val values = ContentValues().apply {
            put("title", task.title)
            put("note", task.note)
            put("isDone", if (task.isDone) 1 else 0)
//            put("subtasks", Gson().toJson(task.subtasks))
            put("subtasks", "")
            put("difficulty", task.difficulty)
            put("stat", task.stat)
//            put("tags", Gson().toJson(task.tags))
            put("tags", "")
            put("deadline", task.deadline)
            put("reminder", task.reminder)
        }
        val db = dbHelper.writableDatabase
        db.insert("tasks", null, values)
    }

    fun getAllTasks(): List<Task> {
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM tasks", null)
        val tasks = mutableListOf<Task>()

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val note = cursor.getString(cursor.getColumnIndexOrThrow("note"))
                val isDone = cursor.getInt(cursor.getColumnIndexOrThrow("isDone")) == 1
                val subtasksJson = cursor.getString(cursor.getColumnIndexOrThrow("subtasks"))
//                val subtasks: List<Subtask>? = gson.fromJson(subtasksJson, object: TypeToken<List<Subtask>>() {}.type)
                val difficulty = cursor.getString(cursor.getColumnIndexOrThrow("difficulty"))
                val stat = cursor.getString(cursor.getColumnIndexOrThrow("stat"))
                val tagsJson = cursor.getString(cursor.getColumnIndexOrThrow("tags"))
//                val tags: List<String>? = gson.fromJson(tagsJson, object: TypeToken<List<String>>() {}.type)
                val deadline = cursor.getString(cursor.getColumnIndexOrThrow("deadline"))
                val reminder = cursor.getString(cursor.getColumnIndexOrThrow("reminder"))
                val subtasks = null
                val tags = null

                val task = Task(
                    id,
                    title,
                    note,
                    isDone,
                    subtasks,
                    difficulty,
                    stat,
                    tags,
                    deadline,
                    reminder
                )
                tasks.add(task)
                cursor.moveToNext()
            }
        }

        cursor.close()
        db.close()

        return tasks
    }

    fun comleteTask(context: Context, task: Task) {
        StatsManager.addExperienceToStat(context, task.stat, task.difficulty)
    }

    fun deleteTask(task: Task): Boolean {
        val db = dbHelper.writableDatabase
        val result = db.delete("tasks", "id=?", arrayOf(task.id.toString()))
        db.close()
        return result != -1
    }
}