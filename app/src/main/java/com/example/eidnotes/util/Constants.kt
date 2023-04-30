package com.example.eidnotes.util

import com.example.eidnotes.R

const val DATABASE_NAME:String = "note_database"

val colors = listOf<Int>(
    R.color.yellow,
    R.color.pink,
    R.color.teal_200,
    R.color.blue,
    R.color.pink2,
    R.color.purple_200,
    R.color.orange,
    R.color.green
)

fun specificColor():Int{
    return colors.randomOrNull()!!
}