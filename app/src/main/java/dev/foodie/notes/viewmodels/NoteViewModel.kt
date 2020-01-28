package dev.foodie.notes.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import dev.foodie.notes.models.Note
import dev.foodie.notes.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = NoteRepository(application)
    private val allNotes = repo.getAllNotes()

    private var _sortNotes: LiveData<List<Note>> = MutableLiveData<List<Note>>()
    val sortNotes: LiveData<List<Note>>
        get() = _sortNotes

    fun addNote(note: Note) = repo.insert(note)

    fun updateNote(note: Note) = repo.update(note)

    fun getNotes() = repo.getAllNotes()

    fun getNote(id: Long) = repo.getNote(id)

//    fun getNotesBy(param: String) = repo.getNotesByParam(param)

    fun deleteNote(note: Note) = repo.deleteNote(note)

    fun deleteAllNotes() = repo.deleteAllNotes()

    fun getN(param: String): LiveData<List<Note>> {
        Log.d("App", "Getting here...")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("App", "Getting here... - inside the viewModelScope")
            _sortNotes = repo.getNotesByParam("")
                //.asLiveData(viewModelScope.coroutineContext)
            Log.d("App", "${ _sortNotes.value?.size }")
        }
        Log.d("App", "Leaving here...")
        return _sortNotes
    }

//    override fun onCleared() {
//        super.onCleared()
//        repo.job.cancel()
//    }
}