package dev.foodie.notes.listeners

interface OnNoteSelectedListener {
    fun noteSelected(position: Int, src: Int)
}