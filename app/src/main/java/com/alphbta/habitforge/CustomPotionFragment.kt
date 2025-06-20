package com.alphbta.habitforge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class CustomPotionFragment(val imagePotion: String) : DialogFragment() {

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

        if (imagePotion == "freeze") {
            potionName.text = "Зелье заморозки"
            potionDesc.text = "Заморозьте регулярное дело или привычку. Замороженные задания не оборвут количество выполнений подряд сегодня, если вы их не выполните!"
            potionBackground.setBackgroundResource(R.drawable.potion_freeze)

        }

        if (imagePotion == "hp") {
            potionName.text = "Зелье лечения"
            potionDesc.text = "Восстанавливает 25% от максимального здоровья."
            potionBackground.setBackgroundResource(R.drawable.potion_hp)
        }



        return rootView
    }
}