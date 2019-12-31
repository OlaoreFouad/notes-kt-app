package dev.foodie.notes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dev.foodie.notes.models.Note
import dev.foodie.notes.repository.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = NoteRepository(application)
    private val allNotes = repo.getAllNotes()

    fun addNote(note: Note) = repo.insert(note)

    fun updateNote(note: Note) = repo.update(note)

    fun getNotes() = repo.getAllNotes()

    fun getNote(id: Long) = repo.getNote(id)

    fun getNotesBy(param: String) = repo.getNotesByParam(param)

    fun deleteNote(note: Note) = repo.deleteNote(note)

    fun deleteAllNotes() = repo.deleteAllNotes()
}