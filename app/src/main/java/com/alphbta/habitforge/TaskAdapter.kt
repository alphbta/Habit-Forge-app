package com.alphbta.habitforge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(var tasks: List<Task>, var context: Context): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskNote: TextView = itemView.findViewById(R.id.taskNote)
        val taskDeadlineDate: TextView = itemView.findViewById(R.id.deadlineDate)
        val deadlineItem: LinearLayout = itemView.findViewById(R.id.deadlineItem)
        val taskDifficulty: TextView = itemView.findViewById(R.id.taskDifficulty)
        val taskStat: TextView = itemView.findViewById(R.id.taskStat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        tasks[position].let {
            holder.taskTitle.text = it.title
            if (!it.note.isNullOrEmpty()) {
                holder.taskNote.text = it.note
                holder.taskNote.visibility = View.VISIBLE
            }
            else holder.taskNote.visibility = View.GONE
            val formattedDate = formatDate(it.deadline)
            if (!formattedDate.isNullOrEmpty()) {
                holder.taskDeadlineDate.text = formattedDate
                holder.deadlineItem.visibility = View.VISIBLE
            }
            else holder.deadlineItem.visibility = View.GONE
            holder.taskDifficulty.text = it.difficulty
            holder.taskStat.text = it.stat
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

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = tasks.size
}
