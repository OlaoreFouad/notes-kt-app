package dev.foodie.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.foodie.notes.models.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY createdAt DESC")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id = :id")
    fun getNote(id: Long): Note

    @Query("SELECT * FROM notes_table ORDER BY lastModified DESC")
    fun getNotesByDateModified(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table ORDER BY title DESC")
    fun getNotesByTitle(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE tags = :tag")
    fun getNotesByTag(tag: String): LiveData<List<Note>>

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()
}