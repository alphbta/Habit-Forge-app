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

        if (!regular.note.isNullOrEmpty()) {
            holder.regularNote.text = regular.note
            holder.regularNote.visibility = View.VISIBLE
        } else holder.regularNote.visibility = View.GONE

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

        holder.completeButton.setOnClickListener {
            onButtonClick(regular)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateRegulars(newRegulars: List<Regular>) {
        regulars = newRegulars
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = regulars.size
}