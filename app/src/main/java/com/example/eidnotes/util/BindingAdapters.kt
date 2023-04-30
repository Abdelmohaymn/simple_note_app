package com.example.eidnotes.util

import android.view.View
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.eidnotes.Adapter.NotesAdapter
import com.example.eidnotes.models.Note



@BindingAdapter("allNotes")
fun setItems(recyclerView: RecyclerView, allNotes: LiveData<List<Note>>?) {
    allNotes?.observe(recyclerView.context as LifecycleOwner) { items ->
        recyclerView.adapter?.let {
            val adapter = it as NotesAdapter
            adapter.setList(items)
        }
    }
}

@BindingAdapter("searchResults")
fun setItems(recyclerView: RecyclerView,searchResult:MutableLiveData<List<Note>>?) {
    searchResult?.observe(recyclerView.context as LifecycleOwner) { items ->
        recyclerView.adapter?.let {
            val adapter = it as NotesAdapter
            adapter.setList(items)
        }
    }
}

@BindingAdapter("cardViewBackground")
fun changeBackground(view:CardView,x:Boolean){
    view.setCardBackgroundColor(view.resources.getColor(specificColor(),null));
}
