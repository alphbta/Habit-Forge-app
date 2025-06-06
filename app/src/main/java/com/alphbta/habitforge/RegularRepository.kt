package com.alphbta.habitforge

import android.content.ContentValues

class RegularRepository(private val dbHelper: DbHelper) {
    fun addRegular(regular: Regular) {
        val values = ContentValues().apply {
            put("title", regular.title)
            put("note", regular.note)
            put("isDone", if (regular.isDone) 1 else 0)
            put("subtasks", "")
            put("difficulty", regular.difficulty)
            put("stat", regular.stat)
            put("tags", "")
            put("lastUpdated", regular.lastUpdated)
            put("repeatType", regular.repeatType)
            put("streak", regular.streak)
            put("freezeCount", regular.freezeCount)
            put("doneCount", regular.doneCount)
            put("missedCount", regular.missedCount)
            put("lastCompletionDate", regular.lastCompletionDate)
        }
        val db = dbHelper.writableDatabase
        db.insert("regulars", null, values)
    }
    fun updateRegularCompletion(regular: Regular) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("lastCompletionDate", regular.lastCompletionDate)
            put("doneCount", regular.doneCount)
        }
        db.update("regulars", values, "id = ?", arrayOf(regular.id.toString()))
        db.close()
    }

    fun getAllRegulars(): List<Regular> {
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM regulars", null)
        val regulars = mutableListOf<Regular>()

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
                val freezeCount = cursor.getInt(cursor.getColumnIndexOrThrow("freezeCount"))
                val repeatType = cursor.getString(cursor.getColumnIndexOrThrow("repeatType"))
                val doneCount = cursor.getInt(cursor.getColumnIndexOrThrow("doneCount"))
                val missedCount = cursor.getInt(cursor.getColumnIndexOrThrow("missedCount"))
                val lastCompletionDate = cursor.getString(cursor.getColumnIndexOrThrow("lastCompletionDate"))

                val regular = Regular(
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
                    freezeCount,
                    repeatType,
                    doneCount,
                    missedCount,
                    lastCompletionDate
                )
                regulars.add(regular)
                cursor.moveToNext()
            }
        }

        cursor.close()
        db.close()

        return regulars
    }

    fun deleteRegular(_id: String): Boolean {
        val db = dbHelper.writableDatabase
        val result = db.delete("regulars", "id=?", arrayOf(_id))
        db.close()
        return result != -1
    }

}