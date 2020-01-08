package dev.foodie.notes.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class FilterAdapter(val filters: List<Filter>, val ctx: Context, val onFilterSelected: ((Int) -> Unit)?)
    : ListAdapter<Filter, FilterAdapter.FilterViewHolder>(FilterDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = currentList.size

    inner class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



    }

}

data class Filter(val name: String, val id: Int)

class FilterDiffUtilCallback() : DiffUtil.ItemCallback<Filter>() {

    override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.name == oldItem.name
    }
}