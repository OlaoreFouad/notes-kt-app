package dev.foodie.notes.fragments

import android.os.Bundle
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

class FilterBottomSheetFragment() : RoundedBottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBottomFragmentBinding
    private var currentFilter: Int = 0

    private val initialTagsList = listOf(
        Filter("All Notes", Constants.BY_ALL_NOTES, true),
        Filter(Constants.WORK, Constants.BY_WORK, false),
        Filter(Constants.PERSONAL, Constants.BY_PERSONAL, false),
        Filter(Constants.STUDY, Constants.BY_STUDY, false),
        Filter(Constants.UNCATEGORIZED, Constants.BY_UNCATEGORIZED, false),
        Filter(Constants.FAMIY, Constants.BY_FAMILY, false)
    )

    private val initialSortList = listOf(
        Filter("Date Added", Constants.BY_DATE_ADDED, true),
        Filter("Date Modified", Constants.BY_DATE_MODIFIED, false),
        Filter("Title", Constants.BY_TITLE, false)
    )

    var selectedSortFilter = 0
    var selectedTagFilter = 0

    private lateinit var sortFilterAdapter: FilterAdapter
    private lateinit var tagFilterAdapter: FilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_bottom_fragment, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeSortList()
        initializeTagsList()
    }

    private fun setCurrentFilter() {
        when(currentFilter) {
            0 -> {}
            1 -> {}
        }
    }

    private fun initializeSortList() {
        sortFilterAdapter = FilterAdapter(initialSortList, activity!!) { selectedFilter ->
            selectedSortFilter = selectedFilter
            initialSortList.forEach { it.isSelected = false }
            initialSortList[selectedSortFilter].isSelected = true
            sortFilterAdapter.submitList(initialSortList)
        }

        binding.sortList.apply {
            layoutManager = LinearLayoutManager(activity!!)
            setHasFixedSize(true)
            adapter = sortFilterAdapter
        }
    }

    private fun initializeTagsList() {
        tagFilterAdapter = FilterAdapter(initialTagsList, activity!!) { selectedFilter ->
            selectedTagFilter = selectedFilter
            initialTagsList.forEach { it.isSelected = false }
            initialTagsList[selectedTagFilter].isSelected = true
            tagFilterAdapter.submitList(initialTagsList)
        }

        binding.tagsList.apply {
            layoutManager = LinearLayoutManager(activity!!)
            setHasFixedSize(true)
            adapter = tagFilterAdapter
        }
    }



}