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
            val image = itemView.findViewById<ImageView>(R.id.menu_image)
            val text = itemView.findViewById<TextView>(R.id.menu_text)

            image.setImageResource(menu.res)
            text.text = menu.text
        }

        companion object {

            fun from(parent: ViewGroup) : MenuViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                return MenuViewHolder(inflater.inflate(R.layout.bottom_menu_item, parent, false))
            }

        }

    }

}