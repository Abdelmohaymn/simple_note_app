package com.example.eidnotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eidnotes.databinding.FragmentAddBinding
import com.example.eidnotes.models.Note
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment() {

    private lateinit var binding:FragmentAddBinding
    private var note:Note?=null
    private val args by navArgs<AddFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add,container,false)

        note = null

        binding.imageBackAddFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val oldNote = args.oldNote
        var update = false
        if(oldNote!=null)
        {
            update = true
            binding.etAddTitle.setText(oldNote.title)
            binding.etAddNote.setText(oldNote.note)
        }

        binding.imageSaveAddFragment.setOnClickListener {
            val title = binding.etAddTitle.text.toString()
            val note_body = binding.etAddNote.text.toString()
            if(title.isNotBlank()||note_body.isNotBlank())
            {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy MM:mm a")
                note = Note(null,title, note_body,formatter.format(Date()))
                if(update)
                {
                    note?.id = oldNote?.id
                    if(title==oldNote?.title && note_body==oldNote.note){note?.date = oldNote.date}
                }
                val action = AddFragmentDirections.actionAddFragmentToNotesFragment(note)
                findNavController().navigate(action)
            }else
            {
                Toast.makeText(requireActivity(),"Please, enter full data",Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

}