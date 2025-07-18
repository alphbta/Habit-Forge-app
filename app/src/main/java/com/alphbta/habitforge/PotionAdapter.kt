package com.alphbta.habitforge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class PotionAdapter(private val potions : List<Potion>, private val context: Context, private val listener: OnItemBoughtListener, private val supportFragmentManager: FragmentManager) :
        RecyclerView.Adapter<PotionAdapter.PotionViewHolder>() {
    class PotionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemTitle = view.findViewById<TextView>(R.id.itemTitle)
        val imagePotions = view.findViewById<ImageView>(R.id.imagePotions)
        val potionsMoney = view.findViewById<TextView>(R.id.potionsMoney)
        val card = view.findViewById<ConstraintLayout>(R.id.buyPotion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PotionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_potions, parent, false)
        return PotionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PotionViewHolder, position: Int) {
        val potion = potions[position]
        holder.itemTitle.text = potion.getName().split(" ").joinToString("\n")
        holder.potionsMoney.text = potion.getCost().toString()
        val imageId = context.resources.getIdentifier(potion.getFileName(), "drawable", context.packageName)
        if (imageId != 0) {
            holder.imagePotions.setImageResource(imageId)
        }

        holder.card.setOnClickListener {
            val dialog = CustomPotionFragment(potion, context, listener)
            dialog.show(supportFragmentManager, "customPotion")
        }
    }

    override fun getItemCount(): Int = potions.size
}