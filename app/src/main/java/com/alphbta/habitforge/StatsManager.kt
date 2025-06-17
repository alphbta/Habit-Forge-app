package com.alphbta.habitforge

import android.content.Context
import kotlin.math.min

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

    private fun addToStat(context: Context, key: String, value: Int = 1) {
        val current = getStat(context, key)
        setStat(context, key, current + value)
    }

    fun getAllStats(context: Context): Map<String, Int> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return mapOf(
            "hp" to prefs.getInt("hp", 50),
            "physique" to prefs.getInt("physique", 1),
            "intelligence" to prefs.getInt("intelligence", 1),
            "creativity" to prefs.getInt("creativity", 1),
            "charisma" to prefs.getInt("charisma", 1),
            "physiqueXp" to prefs.getInt("physiqueXp", 0),
            "intelligenceXp" to prefs.getInt("intelligenceXp", 0),
            "creativityXp" to prefs.getInt("creativityXp", 0),
            "charismaXp" to prefs.getInt("charismaXp", 0),
            "user" to prefs.getInt("user", 1),
            "userXp" to prefs.getInt("userXp", 0),
            "coins" to prefs.getInt("coins", 0),
            "freeze" to prefs.getInt("freeze", 0)
        )
    }

    fun addExperienceToStat(context: Context, stat: String, difficulty: String) {
        val xp = when (difficulty) {
            "easy" -> 5
            "normal" -> 15
            "hard" -> 45
            else -> 0
        }

        val stats = getAllStats(context)
        var currentXp = stats["${stat}Xp"]!! + xp
        var currentLevel = stats[stat]!!
        val requiredXp = 50 + (currentLevel - 1) * 25
        if (currentXp >= requiredXp) {
            currentXp -= requiredXp
            currentLevel += 1
            setStat(context, stat, currentLevel)

            if (stat == "physique") {
                setStat(context, "hp", stats["hp"]!! + 5)
            }
        }

        setStat(context, "${stat}Xp", currentXp)

        var currentUserXp = stats["userXp"]!! + xp
        var currentUserLevel = stats["user"]!!
        val requiredUserXp = 100 + (currentUserLevel - 1) * 50
        if (currentUserXp >= requiredUserXp) {
            currentUserXp -= requiredUserXp
            currentUserLevel += 1
            setStat(context, "user", currentUserLevel)
        }

        setStat(context, "userXp", currentUserXp)

        val coins = when (difficulty) {
            "easy" -> 1
            "normal" -> 3
            "hard" -> 9
            else -> 0
        }

        val currentCoins = stats["coins"]!! + coins
        setStat(context, "coins", currentCoins)
    }

    fun getRequiredXpStat(context: Context, stat: String) : Int {
        val stats = getAllStats(context)
        val currentLevel = stats[stat]!!
        val requiredXp = 50 + (currentLevel - 1) * 25
        return requiredXp
    }

    fun getMaxHp(context: Context) : Int {
        val stats = getAllStats(context)
        val physique = stats["physique"]!!
        val maxHp = 45 + 5 * physique
        return maxHp
    }

    fun addHp(context: Context, hp: Int) {
        val stats = getAllStats(context)
        val newHp = min(stats["hp"]!! + hp, getMaxHp(context))
        if (newHp < 0) {
            setStat(context, "hp", 0)
        }
        else {
            setStat(context, "hp", newHp)
        }
    }

    fun getFreezeCount(context: Context) : Int {
        return getAllStats(context)["freeze"]!!
    }

    fun addFreeze(context: Context, value: Int = 1) {
        val stats = getAllStats(context)
        val newFreeze = stats["freeze"]!! + value
        setStat(context, "freeze", newFreeze)
    }

    fun addCoins(context: Context, coins: Int) {
        val stats = getAllStats(context)
        val newCoins = stats["coins"]!! + coins
        setStat(context, "coins", newCoins)
    }

    fun getRequiredUserXpStat(context: Context) : Int {
        val stats = getAllStats(context)
        val currentLevel = stats["user"]!!
        val requiredXp = 100 + (currentLevel - 1) * 50
        return requiredXp
    }
}