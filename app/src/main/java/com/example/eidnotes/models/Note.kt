package com.example.eidnotes.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "notes_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    val title:String?,
    val note:String?,
    var date:String?
):Parcelable