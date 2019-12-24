package dev.foodie.notes.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.foodie.notes.R
import dev.foodie.notes.databinding.ItemTagBinding
import dev.foodie.notes.listeners.TagInteraction

class TagAdapter(var ctx: Context, var callback: (Int, Int) -> Unit)
    : ListAdapter<Tag, TagAdapter.TagViewHolder>(TagDiffUtilCallback()) {

    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TagViewHolder(ItemTagBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {

        val tag = getItem(position)
        if (tag.isChecked) {
            selectedPosition = position
        }

        holder.bind(tag)
    }

    override fun getItemCount() = currentList.size

    inner class TagViewHolder(private val itemTagBinding: ItemTagBinding) : RecyclerView.ViewHolder(itemTagBinding.root), View.OnClickListener {

        init {
            itemTagBinding.root.setOnClickListener(this)
        }

        fun bind(tag: Tag) {
            itemTagBinding.isChecked = tag.isChecked
            itemTagBinding.tagText.text = tag.name
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != selectedPosition) {
                callback(selectedPosition, position)
            }
        }

    }

}

data class Tag(val name: String, var isChecked: Boolean)
class TagDiffUtilCallback() : DiffUtil.ItemCallback<Tag>() {
    override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
        return oldItem.isChecked == newItem.isChecked
    }

    override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
        return oldItem.name == newItem.name && oldItem.isChecked == newItem.isChecked
    }
}