package com.alphbta.habitforge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class CustomDialogFragment(val stat: String) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.description_feature, container, false)
        val statName = rootView.findViewById<TextView>(R.id.characteristic)
        val statBackground = rootView.findViewById<View>(R.id.stat)
        val statDesc = rootView.findViewById<TextView>(R.id.description)
        val exit = rootView.findViewById<TextView>(R.id.exit)
        if (stat == "physique") {
            statName.text = "Телосложение"
            statDesc.text = "Уровень телосложения влияет на ваше максимальное здоровье. Во время сражений телосложение также повышает ваш физический урон."
            statBackground.setBackgroundResource(R.drawable.physique_default)
        }

        if (stat == "intelligence") {
            statName.text = "Интеллект"
            statDesc.text = "Уровень интеллекта влияет на количество получаемого опыта. Во время сражений интеллект также повышает ваш магический урон."
            statBackground.setBackgroundResource(R.drawable.intelligence_default)
        }

        if (stat == "creativity") {
            statName.text = "Творчество"
            statDesc.text = "Уровень творчества влияет на количество получаемых монет при выполнении заданий. Во время сражений творчество также повышает вашу скорость."
            statBackground.setBackgroundResource(R.drawable.creativity_default)
        }

        if (stat == "charisma") {
            statName.text = "Харизма"
            statDesc.text = "Уровень харизмы влияет на стоимость и редкость предметов в магазине. Во время сражений телосложение также повышает ваш шанс крит. удара."
            statBackground.setBackgroundResource(R.drawable.charisma_default)
        }

        exit.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}