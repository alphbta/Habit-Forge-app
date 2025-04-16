package com.alphbta.habitforge

import android.content.Context

object StatsManager {
    private const val PREF_NAME = "userStats"

    fun getStat(context: Context, key: String): Int {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(key, 0)
    }

    private fun setStat(context: Context, key: String, value: Int) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(key, value).apply()
    }

    fun addToStat(context: Context, key: String, value: Int = 1) {
        val current = getStat(context, key)
        setStat(context, key, current + value)
    }

    fun getAllStats(context: Context): Map<String, Int> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return mapOf(
            "hp" to prefs.getInt("hp", 50),
            "human_health" to prefs.getInt("human_health", 0),
            "intelligence" to prefs.getInt("intelligence", 0),
            "creativity" to prefs.getInt("creativity", 0),
            "charisma" to prefs.getInt("charisma", 0)
        )
    }
}