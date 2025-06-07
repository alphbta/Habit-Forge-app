package com.alphbta.habitforge

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class HabitAdapter(
    private var habits: List<Habit>,
    private var context: Context,
    private var onButtonClick: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val habitTitle: TextView = itemView.findViewById(R.id.habitTitle)
        val habitNote: TextView = itemView.findViewById(R.id.habitNote)
        val completeButton: Button = itemView.findViewById(R.id.complete)
        val difficultyStripe: View = itemView.findViewById(R.id.difficultyStripe)
        val statCircle: View = itemView.findViewById(R.id.statCircle)
        val progressBar: ProgressBar = itemView.findViewById(R.id.habitProgressBar)
        val progressText: TextView = itemView.findViewById(R.id.progressText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val alreadyCompletedToday = habit.lastCompletionDate == today

        holder.habitTitle.text = habit.title
        holder.habitNote.text = habit.note ?: ""
        holder.habitNote.visibility = if (habit.note.isNullOrEmpty()) View.GONE else View.VISIBLE

        when (habit.difficulty) {
            "easy" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.easy2))
            "normal" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.norma2l))
            "hard" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.hard2))
        }

        if (alreadyCompletedToday) {
            holder.habitTitle.paintFlags = holder.habitTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.habitTitle.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            holder.habitNote.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            holder.completeButton.setBackgroundResource(R.drawable.gray_rounded)
            holder.difficultyStripe.setBackgroundResource(R.drawable.gray_rounded)

        } else {
            holder.habitTitle.paintFlags = holder.habitTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.habitTitle.setTextColor(ContextCompat.getColor(context, android.R.color.white))
            holder.habitNote.setTextColor(ContextCompat.getColor(context, android.R.color.white))

            val difficultyBackground = when (habit.difficulty.lowercase()) {
                "easy" -> R.drawable.complexity_easy
                "normal" -> R.drawable.complexity_normal
                "hard" -> R.drawable.complexity_hard
                else -> R.drawable.easy_default
            }
            holder.difficultyStripe.setBackgroundResource(difficultyBackground)

        }
        val statBackground = when (habit.stat.lowercase()) {
            "physique" -> R.drawable.physique_default
            "intelligence" -> R.drawable.intelligence_default
            "creativity" -> R.drawable.creativity_default
            "charisma" -> R.drawable.charisma_default
            else -> R.drawable.intelligence_default
        }
        holder.statCircle.setBackgroundResource(statBackground)
        // Прогресс
        val db = DbHelper.getInstance(context)
        val repo = HabitRepository(db)
        val progress = (habit.currentDay * 100) / habit.targetDays
        holder.progressBar.progress = progress
        holder.progressBar.max = 100
        holder.progressText.text = "${habit.currentDay} / ${habit.targetDays}"

        // Блокировка кнопки
        holder.completeButton.isEnabled = !alreadyCompletedToday

        // Логика нажатия
        holder.completeButton.setOnClickListener {
            if (!alreadyCompletedToday && habit.currentDay < habit.targetDays) {
                val updatedHabit = habit.copy(
                    currentDay = habit.currentDay + 1,
                    lastCompletionDate = today
                )
                repo.updateHabitProgress(updatedHabit)
                habits = habits.toMutableList().also { it[position] = updatedHabit }
                notifyItemChanged(position)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateHabits(newHabits: List<Habit>) {
        habits = newHabits
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = habits.size
}
