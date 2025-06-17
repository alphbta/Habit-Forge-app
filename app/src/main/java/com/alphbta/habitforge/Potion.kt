package com.alphbta.habitforge

import android.content.Context

interface Potion {
    fun drink(context: Context)
    fun getName() : String
    fun getCost() : Int
    fun getFileName() : String
}