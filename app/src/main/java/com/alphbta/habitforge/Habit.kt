package com.alphbta.habitforge

data class Habit(
    val id: Int = 0,
    val title: String,
    val note: String?,
    val isDone: Boolean,
    val subtasks: List<Subtask>? = emptyList(),
    val difficulty: String,
    val stat: String,
    val tags: List<String>? = emptyList(),
    val lastUpdated: String,
    val streak: Int,
    val doneCount: Int,
    val missedCount: Int,
    val targetDays: Int,
    val currentDays: Int
)
