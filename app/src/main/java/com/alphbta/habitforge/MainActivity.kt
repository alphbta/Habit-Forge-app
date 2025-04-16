package com.alphbta.habitforge

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var humanHealthText: TextView
    private lateinit var intelligenceText: TextView
    private lateinit var creativityText: TextView
    private lateinit var charismaText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tempButton: FloatingActionButton = findViewById(R.id.tempButton)
        humanHealthText = findViewById(R.id.humanHealth)
        intelligenceText = findViewById(R.id.intelligence)
        creativityText = findViewById(R.id.creativity)
        charismaText = findViewById(R.id.charisma)
        updateAllTexts()

        tempButton.setOnClickListener {
            val statNumber = (0..3).random()
            when (statNumber) {
                0 -> StatsManager.addToStat(this, "human_health")
                1 -> StatsManager.addToStat(this, "intelligence")
                2 -> StatsManager.addToStat(this, "creativity")
                3 -> StatsManager.addToStat(this, "charisma")
                else -> Toast.makeText(
                    this,
                    "Something went wrong with adding to stat",
                    Toast.LENGTH_LONG
                ).show()
            }
            updateAllTexts()
        }
    }

    private fun updateAllTexts() {
        val stats = StatsManager.getAllStats(this)
        humanHealthText.text = "Здоровье: ${stats["human_health"]}"
        intelligenceText.text = "Интеллект: ${stats["intelligence"]}"
        creativityText.text = "Творчество: ${stats["creativity"]}"
        charismaText.text = "Харизма: ${stats["charisma"]}"
    }
}
