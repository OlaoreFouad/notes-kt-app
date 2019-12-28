package dev.foodie.notes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.foodie.notes.R
import dev.foodie.notes.adapters.MenuAdapter
import dev.foodie.notes.dialogs.RoundedBottomSheetDialogFragment
import dev.foodie.notes.utils.Constants

class BottomSheetFragment : RoundedBottomSheetDialogFragment() {

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter

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

        menuAdapter = MenuAdapter(requireActivity(), Constants.MENUS)
        menuRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = menuAdapter
        }

    }

}