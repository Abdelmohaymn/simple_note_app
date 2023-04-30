package com.example.eidnotes.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.eidnotes.R
import com.example.eidnotes.databinding.NoteItemBinding
import com.example.eidnotes.models.Note
import com.example.eidnotes.util.specificColor

class NotesAdapter(private var notesList:List<Note>, private val listener:NotesClickListener):RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount() = notesList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.binding.apply {
            //setVariable(BR.item,currentNote)
            item = currentNote
            itemCardView.setOnClickListener {
                listener.OnItemClicked(currentNote)
            }
            itemCardView.setOnLongClickListener {
                listener.onLongItemClicked(currentNote,itemCardView)
                true
            }
        }
    }

    fun setList(notes:List<Note>){
        val diffUtil = NoteDiffUtil(notesList,notes)
        val difResult = DiffUtil.calculateDiff(diffUtil)
        notesList=notes
        difResult.dispatchUpdatesTo(this)
    }


    inner class NotesViewHolder(val binding:NoteItemBinding):RecyclerView.ViewHolder(binding.root){}

    interface NotesClickListener{
        fun OnItemClicked(note:Note)
        fun onLongItemClicked(note:Note,cardView:CardView)
    }
}

