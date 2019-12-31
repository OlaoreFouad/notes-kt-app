package dev.foodie.notes.repository

import android.app.Application
import androidx.lifecycle.LiveData
import dev.foodie.notes.data.NoteDao
import dev.foodie.notes.data.NoteDatabase
import dev.foodie.notes.models.Note
import kotlinx.coroutines.*

class NoteRepository(application: Application) {
    private var dao: NoteDao
    private var database: NoteDatabase? = null
    private var allNotes: LiveData<List<Note>>? = null

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {
        database = NoteDatabase.getInstance(application)
        dao = database?.noteDao!!
        allNotes = dao.getNotes()
    }

    fun insert(note: Note) {
        uiScope.launch {
            executeWrite(note) {
                dao.insert(it)
            }
        }
    }

    fun update(note: Note) {
        uiScope.launch {
            executeWrite(note) {
                dao.update(it)
            }
        }
    }

    fun getNote(id: Long): Note? {
        var note: Note? = null
        uiScope.launch {
          note = executeRead { dao.getNote(id) }
        }
        return note
    }

    fun getNotesByParam(param: String): LiveData<List<Note>>? {
        var notes: LiveData<List<Note>>? = null
        uiScope.launch {
            notes = executeReadByParam("param")
        }
        return notes
    }

    fun deleteNote(note: Note) {
        uiScope.launch {
            executeDeleteNote(note)
        }
    }

    fun deleteAllNotes() {
        uiScope.launch {
            executeDeleteNotes()
        }
    }

    fun getAllNotes() = dao.getNotes()

    private suspend fun executeWrite(note: Note, block: (Note) -> Unit) {
        withContext(Dispatchers.IO) {
            block(note)
        }
    }

    private suspend fun executeDeleteNotes() {
        withContext(Dispatchers.IO) {
            dao.deleteAllNotes()
        }
    }

    private suspend fun executeDeleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            dao.deleteNote(note)
        }
    }

    private suspend fun executeRead(block: () -> Note): Note? {
        var note: Note? = null
        withContext(Dispatchers.IO) {
             note = block()
        }
        return note
    }

    private suspend fun executeReadByParam(param: String, tag: String = ""): LiveData<List<Note>> {
        var data: LiveData<List<Note>>? =  null
        withContext(Dispatchers.IO) {
            data = when(param) {
                "title" -> dao.getNotesByTitle()
                "dateModified" -> dao.getNotesByDateModified()
                "tags" -> dao.getNotesByTag(tag)
                else -> dao.getNotes()
            }
        }
        return data!!
    }

}