package com.alphbta.habitforge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val taskTitle: EditText = findViewById(R.id.taskTitle)
        val taskNote: EditText = findViewById(R.id.taskNote)
        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val taskText = taskTitle.text.toString().trim()
            if (taskText.isNotEmpty()) {
                val task = Task(
                    id = 0,
                    title = taskTitle.text.toString().trim(),
                    note = taskNote.text.toString().trim(),
                    isDone = false,
                    subtasks = null,
                    difficulty = "easy",
                    stat = "intelligence",
                    tags = null,
                    deadline = null,
                    reminder = null
                )
                TaskRepository(DbHelper(this, null)).addTask(task)
//                val resultIntent = Intent()
//                resultIntent.putExtra("new_task", taskText)
//                setResult(RESULT_OK, resultIntent)
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}
