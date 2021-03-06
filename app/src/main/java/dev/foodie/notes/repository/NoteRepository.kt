package dev.foodie.notes.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import dev.foodie.notes.data.NoteDao
import dev.foodie.notes.data.NoteDatabase
import dev.foodie.notes.models.Note
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NoteRepository(application: Application) {
    var dao: NoteDao
    var database: NoteDatabase? = null
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

    fun getNote(id: Long): Note? = runBlocking { executeRead(id).await() }

    fun getNotesByParam(param: String, tag: String = "") = dao.getNotes()

    suspend fun get(param: String, tag: String = ""): Flow<List<Note>> {
        return when(param) {
                "dateModified" -> dao.getNotesByDateModified()
                "title" -> dao.getNotesByTitle()
                "tags" -> dao.getNotesByTag(tag)
                else -> dao.getNotesByDateModified()
            }
    }

    fun deleteNote(note: Note) = runBlocking { executeDeleteNote(note) }

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
        return uiScope.async {
            dao.getNote(id)
        }
    }

    private suspend fun executeReadByParam(param: String, tag: String = ""): Deferred<Flow<List<Note>>> {
        return uiScope.async {
            dao.getNotesByDateModified()
        }
    }

}