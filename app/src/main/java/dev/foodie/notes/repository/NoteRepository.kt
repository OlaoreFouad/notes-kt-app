package dev.foodie.notes.repository

import android.app.Application
import android.util.Log
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
        Log.d("App", "Dao that gets the notes: ${ dao.hashCode() }")
    }

    fun insert(note: Note) {
        uiScope.launch {
            executeWrite(note) {
                dao.insert(it)
                Log.d("App", "Dao that inserts the notes: ${ dao.hashCode() }")
            }
        }
    }

    fun update(note: Note) {
        uiScope.launch {
            executeWrite(note) {
                dao.update(it)
                Log.d("App", "Dao that gets the updates: ${ dao.hashCode() }")
            }
        }
    }

    fun getNote(id: Long): Note? = runBlocking { executeRead(id).await() }

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

    private fun executeRead(id: Long): Deferred<Note?> {
        val asyncJob: Deferred<Note?> = uiScope.async {
            Log.d("App", "Dao that updates - execute read! the notes: ${ dao.hashCode() }")
            dao.getNote(id)
        }
        return asyncJob
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