package com.alphbta.habitforge

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
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
    private lateinit var freezeCount: TextView
    private lateinit var menuOverlay: FrameLayout
    private lateinit var menuLayout: View
    private lateinit var xp: TextView
    private lateinit var hp: TextView

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
        freezeCount = findViewById(R.id.freezeCount)
        xp = findViewById(R.id.xp)
        hp = findViewById(R.id.hp)

        updateAllTexts()

        val menuIcon = findViewById<ImageView>(R.id.menu)
        menuOverlay = findViewById(R.id.menuOverlay)
        menuLayout = findViewById(R.id.navigationMenu)

        menuIcon.setOnClickListener {
            openMenu()
        }

        menuOverlay.setOnClickListener {
            closeMenu()
        }

        val openedMenu = findViewById<ImageView>(R.id.openedMenu)
        openedMenu.setOnClickListener {
            closeMenu()
        }

        val menuAccount = findViewById<TextView>(R.id.menuAccount)
        menuAccount.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
            menuOverlay.visibility = View.GONE
        }

        val menuTasks = findViewById<TextView>(R.id.menuTasks)
        menuTasks.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            menuOverlay.visibility = View.GONE
        }

        val menuShop = findViewById<TextView>(R.id.menuShop)
        menuShop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
            menuOverlay.visibility = View.GONE
        }

        val stats = StatsManager.getAllStats(this)
        val userXpBar = findViewById<ProgressBar>(R.id.userXpBar)
        val xpValue = (stats["userXp"]!! * 100) / StatsManager.getRequiredUserXpStat(this)
        userXpBar.progress = xpValue
        val hpBar = findViewById<ProgressBar>(R.id.hpBar)
        val hpValue = (stats["hp"]!! * 100) / StatsManager.getMaxHp(this)
        hpBar.progress = hpValue

        val helpPhysique = findViewById<ImageView>(R.id.help_physique)
        helpPhysique.setOnClickListener {
            val dialog = CustomDialogFragment("physique")
            dialog.show(supportFragmentManager, "customDialog")
        }

        val helpIntelligence = findViewById<ImageView>(R.id.help_intelligence)
        helpIntelligence.setOnClickListener {
            val dialog = CustomDialogFragment("intelligence")
            dialog.show(supportFragmentManager, "customDialog")
        }

        val helpCreativity = findViewById<ImageView>(R.id.help_creativity)
        helpCreativity.setOnClickListener {
            val dialog = CustomDialogFragment("creativity")
            dialog.show(supportFragmentManager, "customDialog")
        }

        val helpCharisma = findViewById<ImageView>(R.id.help_charisma)
        helpCharisma.setOnClickListener {
            val dialog = CustomDialogFragment("charisma")
            dialog.show(supportFragmentManager, "customDialog")
        }

        val helpPotions = findViewById<ImageView>(R.id.imagePotions)
        helpPotions.setOnClickListener{
            val dialog = CustomPotionFragment("freeze")
            dialog.show(supportFragmentManager, "customPotion")
        }
        helpPotions.setOnClickListener{
            val dialog = CustomPotionFragment("hp")
            dialog.show(supportFragmentManager, "customPotion")
        }
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
        userLevel.text = "${stats["user"]}"
        userXp.text = "Опыт: ${stats["userXp"]}"
        coins.text = "Монеты: ${stats["coins"]}"
        freezeCount.text = "Заморозки: ${stats["freeze"]}"
        xp.text = "${stats["userXp"]}/${StatsManager.getRequiredUserXpStat(this)}"
        hp.text = "${stats["hp"]}/${StatsManager.getMaxHp(this)}"
    }

    private fun openMenu() {
        menuOverlay.visibility = View.VISIBLE
    }

    private fun closeMenu() {
        menuOverlay.visibility = View.GONE
    }
}