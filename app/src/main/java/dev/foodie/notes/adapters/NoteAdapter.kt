package dev.foodie.notes.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import dev.foodie.notes.R
import dev.foodie.notes.databinding.ItemNoteBinding
import dev.foodie.notes.listeners.OnNoteSelectedListener
import dev.foodie.notes.models.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter
    (var ctx: Context, private val mOnNoteSelectedListener: OnNoteSelectedListener)
    : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent, mOnNoteSelectedListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }

    override fun getItemCount() = currentList.size

    class NoteViewHolder
        private constructor(
            private val itemNoteBinding: ItemNoteBinding,
            private val listener: OnNoteSelectedListener)
        : RecyclerView.ViewHolder(itemNoteBinding.root), View.OnClickListener, View.OnLongClickListener {

        init {
            itemNoteBinding.root.setOnClickListener(this)
            itemNoteBinding.root.setOnLongClickListener(this)
        }

        override fun onLongClick(p0: View?): Boolean {
            listener.noteSelected(adapterPosition, 1)
            return true
        }

        override fun onClick(p0: View?) {
            listener.noteSelected(adapterPosition, 0)
        }

        @SuppressLint("SimpleDateFormat")
        fun bind(note: Note) {
            itemNoteBinding.apply {
                noteTag.text = "#${ note.tags }"
                noteTitle.text = note.title.capitalize()
                noteContent.text = note.content.capitalize()
                noteDate.text = SimpleDateFormat("dd MMM").format(Date(note.createdAt))
                if (note.isBookmarked) {
                    noteBookmark.setBackgroundResource(R.drawable.bookmarked)
                    noteBookmark.visibility = View.VISIBLE
                } else {
                    noteBookmark.visibility = View.INVISIBLE
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: OnNoteSelectedListener) : NoteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNoteBinding.inflate(layoutInflater, parent, false)
                return NoteViewHolder(binding, listener)
            }
        }

    }

}

class NoteDiffUtilCallback : DiffUtil.ItemCallback<Note>() {
    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.title == newItem.title && oldItem.tags == newItem.tags && oldItem.isBookmarked == newItem.isBookmarked
    }

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }
}