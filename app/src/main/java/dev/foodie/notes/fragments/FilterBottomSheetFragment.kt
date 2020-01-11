package dev.foodie.notes.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dev.foodie.notes.R
import dev.foodie.notes.adapters.Filter
import dev.foodie.notes.adapters.FilterAdapter
import dev.foodie.notes.databinding.FragmentFilterBottomFragmentBinding
import dev.foodie.notes.dialogs.RoundedBottomSheetDialogFragment
import dev.foodie.notes.utils.Constants

class FilterBottomSheetFragment
    (private var selectedSortFilter: Int, private var selectedTagFilter: Int, val filtersSelectedListener: (Pair<Int, Int>) -> Unit)
    : RoundedBottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBottomFragmentBinding
    private var currentFilter: Int = 0

    private val initialTagsList = mutableListOf(
        Filter("All Notes", Constants.BY_ALL_NOTES, false),
        Filter(Constants.WORK, Constants.BY_WORK, false),
        Filter(Constants.PERSONAL, Constants.BY_PERSONAL, false),
        Filter(Constants.STUDY, Constants.BY_STUDY, false),
        Filter(Constants.UNCATEGORIZED, Constants.BY_UNCATEGORIZED, false),
        Filter(Constants.FAMIY, Constants.BY_FAMILY, false)
    )

    private val initialSortList = mutableListOf(
        Filter("Date Added", Constants.BY_DATE_ADDED, false),
        Filter("Date Modified", Constants.BY_DATE_MODIFIED, false),
        Filter("Title", Constants.BY_TITLE, false)
    )

    private lateinit var sortFilterAdapter: FilterAdapter
    private lateinit var tagFilterAdapter: FilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_bottom_fragment, container, false)
        initialSortList[selectedSortFilter].isSelected = true
        initialTagsList[selectedTagFilter].isSelected = true

        initializeTagsList()
        initializeSortList()
        currentFilter = 0
        binding.currentFilter = currentFilter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sortActivator.setOnClickListener {
            binding.currentFilter = 0
            binding.sortActivator.setTextColor(Color.WHITE)
            binding.tagActivator.setTextColor(resources.getColor(R.color.colorAccent))
        }
        binding.tagActivator.setOnClickListener {
            binding.currentFilter = 1
            binding.tagActivator.setTextColor(Color.WHITE)
            binding.sortActivator.setTextColor(resources.getColor(R.color.colorAccent))
        }
        binding.doneButton.setOnClickListener {
            dismiss()
            filtersSelectedListener.invoke(selectedSortFilter to selectedTagFilter)
        }
    }

    private fun initializeSortList() {
        sortFilterAdapter = FilterAdapter(initialSortList, activity!!) { selectedFilter ->
            selectedSortFilter = selectedFilter
            initialSortList.forEach { it.isSelected = false }
            initialSortList[selectedFilter].isSelected = true
            sortFilterAdapter.submitList(initialSortList)
            sortFilterAdapter.notifyDataSetChanged()
        }

        binding.sortList.apply {
            layoutManager = LinearLayoutManager(activity!!)
            setHasFixedSize(true)
            adapter = this@FilterBottomSheetFragment.sortFilterAdapter
        }
        sortFilterAdapter.submitList(initialSortList)
    }

    private fun initializeTagsList() {
        tagFilterAdapter = FilterAdapter(initialTagsList, activity!!) { selectedFilter ->
            selectedTagFilter = selectedFilter
            initialTagsList.forEach { it.isSelected = false }
            initialTagsList[selectedTagFilter].isSelected = true
            tagFilterAdapter.submitList(initialTagsList)
            tagFilterAdapter.notifyDataSetChanged()
        }

        binding.tagsList.apply {
            layoutManager = LinearLayoutManager(activity!!)
            setHasFixedSize(true)
            adapter = this@FilterBottomSheetFragment.tagFilterAdapter
        }
        tagFilterAdapter.submitList(initialTagsList)
    }



}