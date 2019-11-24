package dev.foodie.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.foodie.notes.models.Note
import dev.foodie.notes.viewmodels.NoteViewModel
import dev.foodie.notes.viewmodels.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var viewModelFactory: NoteViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelFactory = NoteViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NoteViewModel::class.java)
        viewModel.addNote(Note(
            tags = listOf("work", "me").joinToString(", "),
            title = "Title",
            content = "Content",
            isBookmarked = false
        ))
        Log.d("MainActivity", "Hey!!")
        viewModel.getNotes()?.observe(this, Observer {
            it.forEach { note -> Log.d("MainActivity", note.toString()) }
        })

    }
}
