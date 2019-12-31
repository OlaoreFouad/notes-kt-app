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
import dev.foodie.notes.utils.Constants
import dev.foodie.notes.views.BottomMenuItem

class MenuAdapter(val ctx: Context, val menus: List<Menu>, val actionSelectedListener: (Int, Long) -> Unit, val noteId: Long) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder = MenuViewHolder.from(parent, actionSelectedListener, noteId)

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menus[position])
    }

    override fun getItemCount() = menus.size

    class MenuViewHolder
    private constructor(itemView: View, val actionSelectedListener: (Int, Long) -> Unit, val noteId: Long)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            actionSelectedListener(when(adapterPosition+1) {
                Constants.BOOKMARK -> 1
                Constants.SHARE -> 2
                Constants.LOCK -> 3
                Constants.ARCHIVE -> 4
                Constants.DELETE -> 5
                else -> 1
            }, noteId)
        }

        fun bind(menu: Menu) {
            val image = itemView.findViewById<ImageView>(R.id.menu_image)
            val text = itemView.findViewById<TextView>(R.id.menu_text)

            image.setImageResource(menu.res)
            text.text = menu.text

            if (menu.id != 4) {
                val divider = itemView.findViewById<View>(R.id.view_divider)
                divider.visibility = View.INVISIBLE
            }
        }

        companion object {

            fun from(parent: ViewGroup, actionSelectedListener: (Int, Long) -> Unit, noteId: Long) : MenuViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                return MenuViewHolder(inflater.inflate(R.layout.bottom_menu_item, parent, false), actionSelectedListener, noteId)
            }

        }

    }

}