package com.alphbta.habitforge

import android.content.Context

class PotionHp : Potion {
    override fun drink(context: Context) {
        val maxHp = StatsManager.getMaxHp(context)
        val addHp = maxHp * 0.25
        StatsManager.addHp(context, addHp.toInt())
    }

    override fun getName(): String = "зелье лечения"

    override fun getCost(): Int = 5

    override fun getFileName(): String = "potion_hp"

    override fun getDesc(): String = "Восстанавливает 25% от максимального здоровья."
}