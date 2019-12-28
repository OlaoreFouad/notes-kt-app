package dev.foodie.notes.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import dev.foodie.notes.R
import dev.foodie.notes.models.Menu
import kotlinx.android.synthetic.main.bottom_menu_item.view.*

class BottomMenuItem @JvmOverloads
    constructor(ctx: Context, attributeSet: AttributeSet? = null, defStyle: Int? = null)
    : ConstraintLayout(ctx, attributeSet, defStyle!!) {

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.bottom_menu_item, this)

        setPadding(10)
        setBackgroundResource(R.drawable.bottom_menu_item_bg)
    }

    fun setData(menu: Menu) {
        menu_image.setImageResource(menu.res)
        menu_text.text = menu.text
    }

}

