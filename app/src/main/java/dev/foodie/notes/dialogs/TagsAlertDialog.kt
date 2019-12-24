package dev.foodie.notes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.foodie.notes.R
import dev.foodie.notes.adapters.Tag
import dev.foodie.notes.adapters.TagAdapter
import dev.foodie.notes.listeners.TagInteraction
import dev.foodie.notes.utils.Constants

class TagsAlertDialog(var _tagIndex: Int) : DialogFragment() {

    var tempTagIndex: Int = _tagIndex

    private lateinit var mOnTagSelectedListener: OnTagSelectedListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TagAdapter
    private lateinit var confirmButton: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = LayoutInflater.from(activity).inflate(R.layout.tags_dialog, null)
        recyclerView = view.findViewById(R.id.tag_list)
        confirmButton = view.findViewById(R.id.confirm_button)
        setupRecyclerView()

        val dialog = AlertDialog.Builder(activity)
            .setView(view)
            .create()

        confirmButton.setOnClickListener {
            _tagIndex = tempTagIndex
            mOnTagSelectedListener.tagSelected(Constants.TAG_MAP.getValue(_tagIndex))
            dialog.dismiss()
        }

        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    private fun setupRecyclerView() {
        adapter = TagAdapter(context!!) { _, newPosition ->
            tempTagIndex = newPosition + 1
            adapter.submitList(getTags())
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context!!)
            setHasFixedSize(true)
            adapter = this@TagsAlertDialog.adapter
        }
        adapter.submitList(getTags())
    }

    private fun getTags(): List<Tag> {
        val tagsList = mutableListOf<Tag>()
        listOf(1, 2, 3, 4, 5).forEach {
            val tag = Constants.TAG_MAP.getValue(it)
            if (tempTagIndex == it) {
                tagsList.add(Tag(tag, true))
            } else {
                tagsList.add(Tag(tag, false))
            }
        }

        return tagsList
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnTagSelectedListener = context as OnTagSelectedListener
    }

}

interface OnTagSelectedListener {
    fun tagSelected(tag: String)
}