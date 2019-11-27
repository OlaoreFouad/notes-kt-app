package dev.foodie.notes.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    var tags: String = "",

    var title: String = "",

    var content: String = "",

    var isBookmarked: Boolean = false,

    var createdAt: Long = System.currentTimeMillis(),

    var lastModified: Long = createdAt
) : Serializable