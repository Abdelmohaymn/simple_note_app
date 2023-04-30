package com.example.eidnotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eidnotes.Adapter.NotesAdapter
import com.example.eidnotes.Database.NoteDatabase
import com.example.eidnotes.databinding.FragmentNotesBinding
import com.example.eidnotes.models.Note
import com.example.eidnotes.models.NotesViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NotesFragment : Fragment(), NotesAdapter.NotesClickListener {

    private lateinit var binding:FragmentNotesBinding
    private lateinit var viewModel:NotesViewModel
    private lateinit var adapter:NotesAdapter
    private lateinit var searchDisposable: Disposable
    private var lastNote:Note?=null
    var update:Int = 0

    private val args by navArgs<NotesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_notes,container,false)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[NotesViewModel::class.java]

        binding.lifecycleOwner=this
        binding.viewModel = viewModel

        init()

        return binding.root
    }

    private fun init() {

        adapter = NotesAdapter(emptyList(),this)
        binding.recyclerViewNotes.adapter=adapter
        /*viewModel.searchResults.observe(viewLifecycleOwner){list ->
            adapter.setList(list)
        }*/

        if(args.returnedNote != null )
        {
            if(lastNote==null||lastNote!=args.returnedNote)
            {
                lastNote=args.returnedNote
                viewModel.doFunction(args.returnedNote!!,update)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            update=0
            val action = NotesFragmentDirections.actionNotesFragmentToAddFragment()
            findNavController().navigate(action)
        }

        val queryObservable = Observable.create { emitter ->
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        emitter.onNext(newText.lowercase())
                    }else{
                        emitter.onNext("")
                    }
                    return true
                }
            })
        }
        searchDisposable = queryObservable
            .debounce(250, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { results ->
                viewModel.search(results)
                //binding.recyclerViewNotes.smoothScrollToPosition(0)
            }

    }

    override fun OnItemClicked(note: Note) {
        update=1
        val action = NotesFragmentDirections.actionNotesFragmentToAddFragment(note)
        findNavController().navigate(action)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        val popupMenu = PopupMenu(cardView.context, cardView)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_delete_note -> {
                    viewModel.doFunction(note,2)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchDisposable.dispose()
    }

}