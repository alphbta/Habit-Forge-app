package com.alphbta.habitforge

data class Task (
    val id: Int = 0,
    val title: String,
    val note: String?,
    val isDone: Boolean,
    val subtasks: List<Subtask>? = emptyList(),
    val difficulty: String,
    val stat: String,
    val tags: List<String>? = emptyList(),
    val deadline: String?,
    val reminder: String?
)