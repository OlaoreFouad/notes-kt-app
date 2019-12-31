package dev.foodie.notes.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.foodie.notes.R
import dev.foodie.notes.adapters.MenuAdapter
import dev.foodie.notes.dialogs.RoundedBottomSheetDialogFragment
import dev.foodie.notes.models.Note
import dev.foodie.notes.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class BottomSheetFragment(val actionSelectedListener: (Int, Long) -> Unit) : RoundedBottomSheetDialogFragment() {

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter

    private var noteTitle: TextView? = null
    private var noteDate: TextView? = null

    private var note: Note? = null
    private var pattern = "dd MMM YYYY hh:mm"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuRecyclerView = view.findViewById(R.id.menu_list)

        noteTitle = view.findViewById(R.id.note_title_d)
        noteDate = view.findViewById(R.id.note_date_d)

        arguments?.apply {
            note = getSerializable("note") as Note
            val noteId = getLong("id")
            noteTitle?.text = note?.title

            val createdAt = SimpleDateFormat(pattern).format(Date(note?.createdAt!!)).toString()
            val modifiedAt = SimpleDateFormat(pattern).format(Date(note?.lastModified!!)).toString()

            noteDate?.text = String.format("Created %s - Modified %s", createdAt, modifiedAt)
            Log.d("App", "Note Id is: $noteId")
            menuAdapter = MenuAdapter(requireActivity(), Constants.getMenus(note?.isBookmarked!!),
                actionSelectedListener, noteId)
            menuRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = menuAdapter
            }
        }

    }

}