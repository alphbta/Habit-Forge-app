package com.alphbta.habitforge

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

class AddRegularActivity : AppCompatActivity() {
    private var difficultyRegular: String = "easy"
    private var statRegular: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_regular)

        val regularTitle = findViewById<EditText>(R.id.regularTitle)
        val regularNote = findViewById<EditText>(R.id.regularNote)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val easyButton = findViewById<ImageButton>(R.id.easy)
        val normalButton = findViewById<ImageButton>(R.id.normal)
        val hardButton = findViewById<ImageButton>(R.id.hard)
        val physiqueButton = findViewById<ImageButton>(R.id.physique)
        val intelligenceButton = findViewById<ImageButton>(R.id.intelligence)
        val creativityButton = findViewById<ImageButton>(R.id.creativity)
        val charismaButton = findViewById<ImageButton>(R.id.charisma)

        val monday = findViewById<ToggleButton>(R.id.day_monday)
        val tuesday = findViewById<ToggleButton>(R.id.day_tuesday)
        val wednesday = findViewById<ToggleButton>(R.id.day_wednesday)
        val thursday = findViewById<ToggleButton>(R.id.day_thursday)
        val friday = findViewById<ToggleButton>(R.id.day_friday)
        val saturday = findViewById<ToggleButton>(R.id.day_saturday)
        val sunday = findViewById<ToggleButton>(R.id.day_sunday)

        easyButton.setOnClickListener { difficultyRegular = "easy" }
        normalButton.setOnClickListener { difficultyRegular = "normal" }
        hardButton.setOnClickListener { difficultyRegular = "hard" }

        physiqueButton.setOnClickListener { statRegular = "physique" }
        intelligenceButton.setOnClickListener { statRegular = "intelligence" }
        creativityButton.setOnClickListener { statRegular = "creativity" }
        charismaButton.setOnClickListener { statRegular = "charisma" }

        saveButton.setOnClickListener {
            val regularText = regularTitle.text.toString().trim()
            val regularNoteText = regularNote.text.toString().trim()

            val selectedDays = mutableListOf<String>()
            if (monday.isChecked) selectedDays.add("mon")
            if (tuesday.isChecked) selectedDays.add("tue")
            if (wednesday.isChecked) selectedDays.add("wed")
            if (thursday.isChecked) selectedDays.add("thu")
            if (friday.isChecked) selectedDays.add("fri")
            if (saturday.isChecked) selectedDays.add("sat")
            if (sunday.isChecked) selectedDays.add("sun")

            val repeatType = selectedDays.joinToString(",")

            if (statRegular.isNullOrEmpty()) {
                Toast.makeText(this, "Выберите характеристику", Toast.LENGTH_SHORT).show()
            }
            if (regularText.isNotEmpty() && !statRegular.isNullOrEmpty()) {
                val regular = Regular(
                    id = 0,
                    title = regularText,
                    note = regularNoteText,
                    isDone = false,
                    subtasks = null,
                    difficulty = difficultyRegular,
                    stat = statRegular!!,
                    tags = null,
                    lastUpdated = "",
                    streak = 0,
                    freezeCount = 0,
                    repeatType = repeatType,
                    doneCount = 0,
                    missedCount = 0,
                    lastCompletionDate = ""
                )
                RegularRepository(DbHelper.getInstance(this)).addRegular(regular)
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
                difficultyRegular = when (button.id) {
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
                statRegular = when (button.id) {
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