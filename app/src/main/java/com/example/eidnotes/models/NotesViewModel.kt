package com.example.eidnotes.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eidnotes.Database.NoteDatabase
import com.example.eidnotes.Database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application:Application):AndroidViewModel(application) {

    private val repository:NoteRepository
    val allNotes:LiveData<List<Note>>
    val searchResults = MutableLiveData<List<Note>>()

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    private fun deleteNote(note:Note) = viewModelScope.launch (Dispatchers.IO){
        repository.delete(note)
    }

    private fun insertNote(note:Note) = viewModelScope.launch (Dispatchers.IO){
        repository.insert(note)
    }

    private fun updateNote(note:Note) = viewModelScope.launch (Dispatchers.IO){
        repository.update(note)
    }

    fun search(text:String){
         if(text.isNotBlank()){
            val filteredList:List<Note> =
                allNotes.value?.filter { it.title?.lowercase()!!.contains(text,ignoreCase = true)
                        || it.note?.lowercase()!!.contains(text,ignoreCase = true)
                }?: emptyList()
            searchResults.postValue(filteredList)
        }else{
            searchResults.postValue(allNotes.value?: emptyList())
        }
    }

    fun doFunction(note:Note,job:Int)
    {
        when(job){
            0 -> insertNote(note)
            1 -> updateNote(note)
            else -> deleteNote(note)
        }
    }
}