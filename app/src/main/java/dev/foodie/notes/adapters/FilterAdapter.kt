package dev.foodie.notes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.foodie.notes.R
import dev.foodie.notes.databinding.ItemFilterBinding

class FilterAdapter(val filters: List<Filter>, val ctx: Context, val onFilterSelected: ((Int) -> Unit)?)
    : ListAdapter<Filter, FilterAdapter.FilterViewHolder>(FilterDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilterViewHolder.from(parent)

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) = holder.bind(filters[position])

    override fun getItemCount() = currentList.size

    class FilterViewHolder(private val itemFilterBinding: ItemFilterBinding) : RecyclerView.ViewHolder(itemFilterBinding.root) {

        fun bind(filter: Filter) {
            itemFilterBinding.isSelected = filter.isSelected
            if (filter.isSelected) {
                itemFilterBinding.filterName.setTextColor(
                    itemFilterBinding.root.resources.getColor(R.color.colorPrimaryDark)
                )
                itemFilterBinding.filterName.text = filter.name
                itemFilterBinding.filterImage.visibility = View.VISIBLE
            }
        }

        companion object {

            fun from(parent: ViewGroup): FilterViewHolder {
                val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context))
                return FilterViewHolder(binding)
            }

        }
    }

}

data class Filter(val name: String, val id: Int, var isSelected: Boolean)

class FilterDiffUtilCallback() : DiffUtil.ItemCallback<Filter>() {

    override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.name == oldItem.name
    }
}