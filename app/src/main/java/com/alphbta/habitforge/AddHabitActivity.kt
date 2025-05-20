package com.alphbta.habitforge

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddHabitActivity : AppCompatActivity() {
    private var difficultyHabit: String = "easy"
    private var statHabit: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        val habitTitle = findViewById<EditText>(R.id.habitTitle)
        val habitNote = findViewById<EditText>(R.id.habitNote)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val easyButton = findViewById<ImageButton>(R.id.easy)
        val normalButton = findViewById<ImageButton>(R.id.normal)
        val hardButton = findViewById<ImageButton>(R.id.hard)
        val physiqueButton = findViewById<ImageButton>(R.id.physique)
        val intelligenceButton = findViewById<ImageButton>(R.id.intelligence)
        val creativityButton = findViewById<ImageButton>(R.id.creativity)
        val charismaButton = findViewById<ImageButton>(R.id.charisma)

        easyButton.setOnClickListener { difficultyHabit = "easy" }
        normalButton.setOnClickListener{ difficultyHabit = "normal" }
        hardButton.setOnClickListener{ difficultyHabit = "hard" }

        physiqueButton.setOnClickListener { statHabit = "physique" }
        intelligenceButton.setOnClickListener { statHabit = "intelligence" }
        creativityButton.setOnClickListener { statHabit = "creativity" }
        charismaButton.setOnClickListener { statHabit = "charisma" }

        saveButton.setOnClickListener {
            val habitText = habitTitle.text.toString().trim()
            if (statHabit.isNullOrEmpty()) {
                Toast.makeText(this, "Выберите характеристику", Toast.LENGTH_SHORT).show()
            }
            if (habitText.isNotEmpty() && !statHabit.isNullOrEmpty()) {
                val habit = Habit(
                    id = 0,
                    title = habitText,
                    note = habitNote.text.toString().trim(),
                    isDone = false,
                    subtasks = null,
                    difficulty = difficultyHabit,
                    stat = statHabit!!,
                    tags = null,
                    lastUpdated = "",
                    0,
                    0,
                    0
                )
                HabitRepository(DbHelper(this, null)).addHabit(habit)
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

                difficultyHabit = when (button.id) {
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

                statHabit = when (button.id) {
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