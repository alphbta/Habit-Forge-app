package com.alphbta.habitforge

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Locale

class AddTaskActivity : AppCompatActivity() {
    private var deadlineDate: String? = null
    private var difficultyTask: String = "easy"
    private var statTask: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val taskTitle: EditText = findViewById(R.id.taskTitle)
        val taskNote: EditText = findViewById(R.id.taskNote)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val easyButton = findViewById<ImageButton>(R.id.easy)
        val normalButton = findViewById<ImageButton>(R.id.normal)
        val hardButton = findViewById<ImageButton>(R.id.hard)
        val physiqueButton = findViewById<ImageButton>(R.id.physique)
        val intelligenceButton = findViewById<ImageButton>(R.id.intelligence)
        val creativityButton = findViewById<ImageButton>(R.id.creativity)
        val charismaButton = findViewById<ImageButton>(R.id.charisma)

        val deadlineSetDate = findViewById<TextView>(R.id.deadlineSetDate)

        deadlineSetDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
calendar.set(Calendar.MONTH, month)
              calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val locale = Locale("ru")
                val sdf = SimpleDateFormat("d MMMM yyyy", locale)
                val formattedDate = sdf.format(calendar.time)
                deadlineSetDate.text = formattedDate

                val dbFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                deadlineDate = dbFormat.format(calendar.time)
            }
            DatePickerDialog(this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        easyButton.setOnClickListener { difficultyTask = "easy" }
        normalButton.setOnClickListener{ difficultyTask = "normal" }
        hardButton.setOnClickListener{ difficultyTask = "hard" }

        physiqueButton.setOnClickListener { statTask = "physique" }
        intelligenceButton.setOnClickListener { statTask = "intelligence" }
        creativityButton.setOnClickListener { statTask = "creativity" }
        charismaButton.setOnClickListener { statTask = "charisma" }

        saveButton.setOnClickListener {
            val taskText = taskTitle.text.toString().trim()
            if (statTask.isNullOrEmpty()) {
                Toast.makeText(this, "Выберите характеристику", Toast.LENGTH_SHORT).show()
            }
            if (taskText.isNotEmpty() && !statTask.isNullOrEmpty()) {
                val task = Task(
                    id = 0,
                    title = taskText,
                    note = taskNote.text.toString().trim(),
                    isDone = false,
                    subtasks = null,
                    difficulty = difficultyTask,
                    stat = statTask!!,
                    tags = null,
                    deadline = deadlineDate,
                    reminder = null
                )
                TaskRepository(DbHelper.getInstance(this)).addTask(task)
                setResult(RESULT_OK)
                finish()
            }
        }

        val difficultyButtons = listOf(easyButton, normalButton, hardButton)
        val statButtons = listOf(physiqueButton, intelligenceButton, creativityButton, charismaButton)

        difficultyButtons.forEach { button ->
            button.setOnClickListener {
                difficultyButtons.forEach { it.isSelected = false }
                button.isSelected = true

                difficultyTask = when (button.id) {
                    R.id.easy -> "easy"
                    R.id.normal -> "normal"
                    R.id.hard -> "hard"
                    else -> "easy"
                }
            }
        }

        statButtons.forEach { button ->
            button.setOnClickListener {
                statButtons.forEach { it.isSelected = false }
                button.isSelected = true

                statTask = when (button.id) {
                    R.id.physique -> "physique"
                    R.id.intelligence -> "intelligence"
                    R.id.creativity -> "creativity"
                    R.id.charisma -> "charisma"
                    else -> null
                }
            }
        }

    }
}
