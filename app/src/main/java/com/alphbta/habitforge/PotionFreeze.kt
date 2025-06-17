package com.alphbta.habitforge

import android.content.Context

class PotionFreeze : Potion {
    override fun drink(context: Context) {
        StatsManager.addFreeze(context)
    }

    override fun getName(): String = "зелье заморозки"

    override fun getCost(): Int = 15

    override fun getFileName(): String = "potion_freeze"
}