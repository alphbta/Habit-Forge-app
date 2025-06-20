package com.alphbta.habitforge

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class CustomPotionFragment(private val potion: Potion, private val context: Context, private val listener: OnItemBoughtListener) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.description_potions, container, false)
        val potionName = rootView.findViewById<TextView>(R.id.namePotions)
        val potionDesc = rootView.findViewById<TextView>(R.id.descriptionPotions)
        val potionBackground = rootView.findViewById<View>(R.id.imagePotion)
        val buyPotion = rootView.findViewById<TextView>(R.id.buyPotion)
        val potionCost = rootView.findViewById<TextView>(R.id.potionsMoney)

        potionName.text = potion.getName()
        potionDesc.text = potion.getDesc()
        potionCost.text = potion.getCost().toString()
        val imageId = context.resources.getIdentifier(potion.getFileName(), "drawable", context.packageName)
        potionBackground.setBackgroundResource(imageId)

        buyPotion.setOnClickListener {
            val stats = StatsManager.getAllStats(context)
            val currentCoins = stats["coins"]!!
            val potionCost = potion.getCost()
            if (currentCoins >= potionCost) {
                potion.drink(context)
                StatsManager.addCoins(context, -potionCost)
                listener.onItemBought()
                dismiss()
            }
            else {
                Toast.makeText(context, "Недостаточно монет", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }
}