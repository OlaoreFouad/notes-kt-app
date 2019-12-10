package dev.foodie.notes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.foodie.notes.R
import dev.foodie.notes.databinding.ItemTagBinding

class TagAdapter(var ctx: Context, var tags: List<Tag>) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TagViewHolder.from(parent)

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) = holder.bind(tags[position])

    override fun getItemCount() = tags.size

    class TagViewHolder(val itemTagBinding: ItemTagBinding) : RecyclerView.ViewHolder(itemTagBinding.root) {

        fun bind(tag: Tag) {
            itemTagBinding.isChecked = tag.isChecked
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