package dev.foodie.notes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.foodie.notes.R
import dev.foodie.notes.models.Menu
import dev.foodie.notes.views.BottomMenuItem

class MenuAdapter(val ctx: Context, val menus: List<Menu>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder = MenuViewHolder.from(parent)

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menus[position])
    }

    override fun getItemCount() = menus.size

    class MenuViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(menu: Menu) {
            val menuItem: BottomMenuItem = itemView.findViewById(R.id.bottom_menu_item)
            menuItem.setData(menu)
        }

        companion object {

            fun from(parent: ViewGroup) : MenuViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                return MenuViewHolder(inflater.inflate(R.layout.bottom_menu_item_layout, parent, false))
            }

        }

    }

}