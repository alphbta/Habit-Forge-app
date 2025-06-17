package com.alphbta.habitforge

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShopActivity : AppCompatActivity(), OnItemBoughtListener {
    private lateinit var coinsText : TextView
    private lateinit var menuOverlay: FrameLayout
    private lateinit var menuLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        coinsText = findViewById(R.id.coins)
        updateCoins()
        val potions : List<Potion> = listOf(PotionHp(), PotionFreeze())
        val potionsAdapter = PotionAdapter(potions, this, this)
        val potionsRecyclerView = findViewById<RecyclerView>(R.id.potionsList)
        val gridLayoutManager = GridLayoutManager(this, 3)
        potionsRecyclerView.adapter = potionsAdapter
        potionsRecyclerView.layoutManager = gridLayoutManager

        val spacing = 32
        potionsRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(spacing, spacing, spacing, spacing)
            }
        })

        val menuIcon = findViewById<ImageView>(R.id.menu)
        menuOverlay = findViewById(R.id.menuOverlay)
        menuLayout = findViewById(R.id.navigationMenu)

        val userLevel = findViewById<TextView>(R.id.userLevel)
        userLevel.text = "${StatsManager.getAllStats(this)["user"].toString()} ур."

        menuIcon.setOnClickListener {
            openMenu()
        }

        menuOverlay.setOnClickListener {
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
    }

    override fun onItemBought() {
        updateCoins()
    }

    private fun updateCoins() {
        val coins = StatsManager.getAllStats(this)["coins"]
        coinsText.text = coins.toString()
    }

    private fun openMenu() {
        menuOverlay.visibility = View.VISIBLE
    }

    private fun closeMenu() {
        menuOverlay.visibility = View.GONE
    }
}