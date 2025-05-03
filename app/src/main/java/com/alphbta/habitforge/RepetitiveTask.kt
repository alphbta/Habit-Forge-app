package com.alphbta.habitforge

data class RepetitiveTask(
    val id: Int = 0,
    val title: String,
    val note: String?,
    val isDone: Boolean,
    val subtasks: List<Subtask> = emptyList(),
    val difficulty: String,
    val stat: String,
    val tags: List<String> = emptyList(),
    val startDate: String,
    val repeatType: String,
    val lastUpdated: String,
    val doneCount: Int,
    val missedCount: Int
)
