package dev.foodie.notes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.foodie.notes.R
import dev.foodie.notes.adapters.Tag
import dev.foodie.notes.adapters.TagAdapter
import dev.foodie.notes.utils.Constants

class TagsAlertDialog(var _tagIndex: Int) : DialogFragment() {

    private lateinit var mOnTagSelectedListener: OnTagSelectedListener
    private lateinit var recyclerView: RecyclerView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = LayoutInflater.from(activity).inflate(R.layout.tags_dialog, null)
        recyclerView = view.findViewById(R.id.tag_list)
        setupRecyclerView()

        val dialog = AlertDialog.Builder(activity)
            .setView(view)

        return dialog.create()
    }

    private fun setupRecyclerView() {
        val adapter = TagAdapter(context!!, getTags())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context!!)
            setHasFixedSize(true)
            setAdapter(adapter)
        }
    }

    private fun getTags(): List<Tag> {
        val tagsList = mutableListOf<Tag>()

        tagsList.add(Tag(Constants.TAG_MAP[1]!!, false))
        tagsList.add(Tag(Constants.TAG_MAP[2]!!, false))
        tagsList.add(Tag(Constants.TAG_MAP[3]!!, false))
        tagsList.add(Tag(Constants.TAG_MAP[4]!!, false))
        tagsList.add(Tag(Constants.TAG_MAP[5]!!, false))

        tagsList.set(_tagIndex - 1, Tag(Constants.TAG_MAP[_tagIndex]!!, true))

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