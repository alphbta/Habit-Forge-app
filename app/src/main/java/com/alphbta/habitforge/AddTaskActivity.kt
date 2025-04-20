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

        val taskInput = findViewById<EditText>(R.id.taskInput)
        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val taskText = taskInput.text.toString().trim()
            if (taskText.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("new_task", taskText)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
