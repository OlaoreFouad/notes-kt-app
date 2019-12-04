package dev.foodie.notes.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import dev.foodie.notes.R
import dev.foodie.notes.databinding.ItemNoteBinding
import dev.foodie.notes.models.Note

class NoteAdapter(var ctx: Context) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }

    override fun getItemCount() = currentList.size

    class NoteViewHolder private constructor(private val itemNoteBinding: ItemNoteBinding): RecyclerView.ViewHolder(itemNoteBinding.root) {
        fun bind(note: Note) {
            Log.d("Adapter", "Note: $note")
        }

        companion object {
            fun from(parent: ViewGroup) : NoteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNoteBinding.inflate(layoutInflater, parent, false)
                return NoteViewHolder(binding)
            }
        }

    }

}

class NoteDiffUtilCallback : DiffUtil.ItemCallback<Note>() {
    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }
}