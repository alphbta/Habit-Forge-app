package com.alphbta.habitforge

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class RegularAdapter(
    private var regulars: List<Regular>,
    private var context: Context,
    private var onButtonClick: (Regular) -> Unit
) : RecyclerView.Adapter<RegularAdapter.RegularViewHolder>() {

    class RegularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val regularTitle: TextView = itemView.findViewById(R.id.regularTitle)
        val regularNote: TextView = itemView.findViewById(R.id.regularNote)
        val completeButton: Button = itemView.findViewById(R.id.complete)
        val difficultyStripe: View = itemView.findViewById(R.id.difficultyStripe)
        val statCircle: View = itemView.findViewById(R.id.statCircle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegularViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_regular, parent, false)
        return RegularViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegularViewHolder, position: Int) {
        val regular = regulars[position]

        holder.regularTitle.text = regular.title
        holder.regularNote.text = regular.note ?: ""
        holder.regularNote.visibility = if (regular.note.isNullOrEmpty()) View.GONE else View.VISIBLE

        when (regular.difficulty) {
            "easy" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.easy2))
            "normal" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.norma2l))
            "hard" -> holder.completeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.hard2))
        }

        val difficultyBackground = when (regular.difficulty.lowercase()) {
            "easy" -> R.drawable.complexity_easy
            "normal" -> R.drawable.complexity_normal
            "hard" -> R.drawable.complexity_hard
            else -> R.drawable.easy_default
        }
        holder.difficultyStripe.setBackgroundResource(difficultyBackground)

        val statBackground = when (regular.stat.lowercase()) {
            "physique" -> R.drawable.physique_default
            "intelligence" -> R.drawable.intelligence_default
            "creativity" -> R.drawable.creativity_default
            "charisma" -> R.drawable.charisma_default
            else -> R.drawable.intelligence_default
        }
        holder.statCircle.setBackgroundResource(statBackground)

        val todayShort = SimpleDateFormat("EEE", Locale.ENGLISH).format(Date()).lowercase()
        val scheduledDays = regular.repeatType.split(",")
        val isTodayScheduled = scheduledDays.contains(todayShort)

        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val alreadyCompleted = regular.lastCompletionDate == todayDate

        val db = DbHelper.getInstance(context)
        val repo = RegularRepository(db)

        if (isTodayScheduled) {
            if (alreadyCompleted) {
                holder.regularTitle.paintFlags = holder.regularTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.regularTitle.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                holder.difficultyStripe.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                holder.completeButton.isEnabled = false
            } else {
                holder.regularTitle.paintFlags = 0
                holder.regularTitle.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                holder.difficultyStripe.setBackgroundResource(difficultyBackground)
                holder.completeButton.isEnabled = true
            }
        } else {
            holder.completeButton.isEnabled = false
        }

        holder.completeButton.setOnClickListener {
            if (isTodayScheduled && !alreadyCompleted) {
                val updatedRegular = regular.copy(
                    doneCount = regular.doneCount + 1,
                    lastCompletionDate = todayDate
                )
                repo.updateRegularCompletion(updatedRegular)

                val updatedList = repo.getAllRegulars()
                updateRegulars(updatedList)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateRegulars(newRegulars: List<Regular>) {
        regulars = newRegulars
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = regulars.size
}
