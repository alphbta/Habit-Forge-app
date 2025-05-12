package com.alphbta.habitforge

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.core.content.ContextCompat

class TaskAdapter(
    private var tasks: List<Task>,
    private var context: Context,
    private var onButtonClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskNote: TextView = itemView.findViewById(R.id.taskNote)
        val taskDeadlineDate: TextView = itemView.findViewById(R.id.deadlineDate)
        val deadlineItem: LinearLayout = itemView.findViewById(R.id.deadlineItem)
        val completeButton: Button = itemView.findViewById(R.id.complete)

        val difficultyStripe: View = itemView.findViewById(R.id.difficultyStripe)
        val statCircle: View = itemView.findViewById(R.id.statCircle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.taskTitle.text = task.title

        if (!task.note.isNullOrEmpty()) {
            holder.taskNote.text = task.note
            holder.taskNote.visibility = View.VISIBLE
        } else holder.taskNote.visibility = View.GONE

        val formattedDate = formatDate(task.deadline)
        if (!formattedDate.isNullOrEmpty()) {
            holder.taskDeadlineDate.text = formattedDate
            holder.deadlineItem.visibility = View.VISIBLE
        } else holder.deadlineItem.visibility = View.GONE


        when (task.difficulty) {
            "easy" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.easy2))
            "normal" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.norma2l))
            "hard" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.hard2))
        }


        val difficultyBackground = when (task.difficulty.lowercase()) {
            "easy" -> R.drawable.complexity_easy
            "normal" -> R.drawable.complexity_normal
            "hard" -> R.drawable.complexity_hard
            else -> R.drawable.easy_default
        }

        holder.difficultyStripe.setBackgroundResource(difficultyBackground)

        val statBackground = when (task.stat.lowercase()) {
            "physique" -> R.drawable.physique_default
            "intelligence" -> R.drawable.intelligence_default
            "creativity" -> R.drawable.creativity_default
            "charisma" -> R.drawable.charisma_default
            else -> R.drawable.intelligence_default
        }
        holder.statCircle.setBackgroundResource(statBackground)

        holder.completeButton.setOnClickListener {
            onButtonClick(task)
        }
    }

    private fun formatDate(dateString: String?): String? {
        if (dateString.isNullOrEmpty()) return null
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
            outputFormat.format(date!!)
        } catch (e: Exception) {
            null
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = tasks.size
}

