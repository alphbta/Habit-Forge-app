package com.alphbta.habitforge

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AccountActivity : AppCompatActivity() {
    private lateinit var physiqueLevel: TextView
    private lateinit var intelligenceLevel: TextView
    private lateinit var creativityLevel: TextView
    private lateinit var charismaLevel: TextView
    private lateinit var physiqueXp: TextView
    private lateinit var intelligenceXp: TextView
    private lateinit var creativityXp: TextView
    private lateinit var charismaXp: TextView
    private lateinit var userLevel: TextView
    private lateinit var userXp: TextView
    private lateinit var coins: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        physiqueLevel = findViewById(R.id.physiqueLevel)
        intelligenceLevel = findViewById(R.id.intelligenceLevel)
        creativityLevel = findViewById(R.id.creativityLevel)
        charismaLevel = findViewById(R.id.charismaLevel)
        physiqueXp = findViewById(R.id.physiqueXp)
        intelligenceXp = findViewById(R.id.intelligenceXp)
        creativityXp = findViewById(R.id.creativityXp)
        charismaXp = findViewById(R.id.charismaXp)
        userLevel = findViewById(R.id.userLevel)
        userXp = findViewById(R.id.userXp)
        coins = findViewById(R.id.coins)

        updateAllTexts()
    }

    private fun updateAllTexts() {
        val stats = StatsManager.getAllStats(this)
        physiqueLevel.text = stats["physique"].toString()
        intelligenceLevel.text = stats["intelligence"].toString()
        creativityLevel.text = stats["creativity"].toString()
        charismaLevel.text = stats["charisma"].toString()
        physiqueXp.text = "${stats["physiqueXp"]}/${StatsManager.getRequiredXpStat(this, "physique")}"
        intelligenceXp.text = "${stats["intelligenceXp"]}/${StatsManager.getRequiredXpStat(this, "intelligence")}"
        creativityXp.text = "${stats["creativityXp"]}/${StatsManager.getRequiredXpStat(this, "creativity")}"
        charismaXp.text = "${stats["charismaXp"]}/${StatsManager.getRequiredXpStat(this, "charisma")}"
        userLevel.text = "Уровень: ${stats["user"]}"
        userXp.text = "Опыт: ${stats["userXp"]}"
        coins.text = "Монеты: ${stats["coins"]}"
    }
}