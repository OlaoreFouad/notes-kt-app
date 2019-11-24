package dev.foodie.notes.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.foodie.notes.models.Note

@Database(entities = [Note::class], exportSchema = false, version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        private lateinit var instance: NoteDatabase

        fun getInstance(application: Application): NoteDatabase {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        application,
                        NoteDatabase::class.java,
                        "notes_database"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}