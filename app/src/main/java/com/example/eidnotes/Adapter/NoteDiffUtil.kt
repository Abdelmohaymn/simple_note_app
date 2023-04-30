package com.example.eidnotes.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.eidnotes.models.Note

class NoteDiffUtil(
    private val oldItems:List<Note>
    ,private val newItems:List<Note>)
    :DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldItems[oldItemPosition].id != newItems[newItemPosition].id ->false
            oldItems[oldItemPosition].title != newItems[newItemPosition].title ->false
            oldItems[oldItemPosition].note != newItems[newItemPosition].note ->false
            oldItems[oldItemPosition].date != newItems[newItemPosition].date ->false
            else -> true
        }
    }

}