package dev.foodie.notes.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.foodie.notes.R
import dev.foodie.notes.databinding.ItemTagBinding

class TagAdapter(var ctx: Context, var tags: List<Tag>) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TagViewHolder.from(parent)

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {

        val tag = tags[position]
        if (tag.isChecked) {
            selectedPosition = position
        }

        holder.bind(tags[position])
    }

    override fun getItemCount() = tags.size

    class TagViewHolder(private val itemTagBinding: ItemTagBinding) : RecyclerView.ViewHolder(itemTagBinding.root), View.OnClickListener {

        fun bind(tag: Tag) {
            itemTagBinding.isChecked = tag.isChecked
            itemTagBinding.tagText.text = tag.name
        }

        override fun onClick(p0: View?) {

        }

        companion object {
            fun from(parent: ViewGroup) : TagViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemTagBinding.inflate(inflater, parent, false)

                return TagViewHolder(binding)
            }
        }

    }

}

data class Tag(val name: String, var isChecked: Boolean)