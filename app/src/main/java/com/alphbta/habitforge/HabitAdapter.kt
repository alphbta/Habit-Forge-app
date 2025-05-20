package com.alphbta.habitforge

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]

        holder.habitTitle.text = habit.title

        if (!habit.note.isNullOrEmpty()) {
            holder.habitNote.text = habit.note
            holder.habitNote.visibility = View.VISIBLE
        } else holder.habitNote.visibility = View.GONE

        when (habit.difficulty) {
            "easy" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.easy2))
            "normal" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.norma2l))
            "hard" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.hard2))
        }

        val difficultyBackground = when (habit.difficulty.lowercase()) {
            "easy" -> R.drawable.complexity_easy
            "normal" -> R.drawable.complexity_normal
            "hard" -> R.drawable.complexity_hard
            else -> R.drawable.easy_default
        }

        holder.difficultyStripe.setBackgroundResource(difficultyBackground)

        val statBackground = when (habit.stat.lowercase()) {
            "physique" -> R.drawable.physique_default
            "intelligence" -> R.drawable.intelligence_default
            "creativity" -> R.drawable.creativity_default
            "charisma" -> R.drawable.charisma_default
            else -> R.drawable.intelligence_default
        }
        holder.statCircle.setBackgroundResource(statBackground)

        holder.completeButton.setOnClickListener {
            onButtonClick(habit)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateHabits(newHabits: List<Habit>) {
        habits = newHabits
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = habits.size
}