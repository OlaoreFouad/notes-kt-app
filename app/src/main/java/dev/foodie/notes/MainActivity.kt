package dev.foodie.notes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.foodie.notes.activities.ViewEditActivity
import dev.foodie.notes.adapters.NoteAdapter
import dev.foodie.notes.databinding.ActivityMainBinding
import dev.foodie.notes.fragments.BottomSheetFragment
import dev.foodie.notes.fragments.FilterBottomSheetFragment
import dev.foodie.notes.listeners.OnNoteSelectedListener
import dev.foodie.notes.models.Note
import dev.foodie.notes.utils.Constants
import dev.foodie.notes.viewmodels.NoteViewModel
import dev.foodie.notes.viewmodels.NoteViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val NEXT_RELEASE_TEXT = "Available in the next release"

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var viewModelFactory: NoteViewModelFactory
    private val TAG = "MainActivity"

    private val sortLiveData = mutableListOf<LiveData<List<Note>>?>()

    private val REQUEST_CODE = 0
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter
    private lateinit var bottomSheetFragment: BottomSheetFragment

    private val obs = Observer<List<Note>> { adapter.submitList(it as MutableList<Note>) }

    private var selectedSortFilter = Constants.BY_DATE_ADDED
    private var selectedTagFilter = Constants.BY_ALL_NOTES

    private var deletedNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(main_toolbar)
        viewModelFactory = NoteViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NoteViewModel::class.java)

        viewModel.getNotes().observe(this, Observer {
            if (it.isEmpty()) {
                binding.noNotesTextView.visibility = View.VISIBLE
                binding.noteRecyclerView.visibility = View.GONE
            } else {
                adapter.submitList(it as MutableList<Note>)
                binding.noNotesTextView.visibility = View.GONE
                binding.noteRecyclerView.visibility = View.VISIBLE
            }
        })

        adapter = NoteAdapter(applicationContext, object : OnNoteSelectedListener {
            override fun noteSelected(position: Int, src: Int) {
                val note = adapter.currentList[position]

                if (src == 0) {
                    val intent = Intent(this@MainActivity, ViewEditActivity::class.java)
                    intent.putExtra("note", note)
                    startActivityForResult(intent, REQUEST_CODE)
                } else {
                    bottomSheetFragment = BottomSheetFragment { actionId, _ ->
                        bottomSheetFragment.dismiss()
                        when(actionId) {
                            Constants.BOOKMARK -> bookmark(note)
                            Constants.SHARE -> share(note)
                            Constants.LOCK -> showToast(NEXT_RELEASE_TEXT)
                            Constants.ARCHIVE -> showToast(NEXT_RELEASE_TEXT)
                            Constants.DELETE -> delete(note)
                        }
                    }
                    val bundle = Bundle()
                    bundle.putSerializable("note", note)
                    bundle.putLong("id", note.id)
                    bottomSheetFragment.arguments = bundle
                    bottomSheetFragment.show(supportFragmentManager, "bottomSheetModalFragment")
                }
            }
        })

        binding.noteRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }

        selectedSortFilter = Constants.BY_DATE_ADDED

        add_note_fab.setOnClickListener {
            val intent = Intent(this@MainActivity, ViewEditActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            var note: Note
            var editMode: Boolean
            data.extras?.apply {
                note = getSerializable("note") as Note
                editMode = getBoolean("editMode")

                if (editMode) viewModel.updateNote(note) else viewModel.addNote(note)
                Log.d("App", "Edit mode: $editMode Note: $note")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val menuItem = menu?.findItem(R.id.nav_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                adapter.filter.filter(newText)

                return true
            }
        })
        searchView.setOnCloseListener {
            adapter.searchCompleted()
            return@setOnCloseListener true
        }

        return true
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            R.id.nav_archived -> {
                item.isChecked = !item.isChecked
                showToast(NEXT_RELEASE_TEXT)
            }
            R.id.nav_filter -> {
                val bottomBar = FilterBottomSheetFragment(selectedSortFilter, selectedTagFilter) {
                    selectedSortFilter = it.first
                    selectedTagFilter = it.second

                    Log.d("App", "Coming from setupSortMethod")

                    //setUpSortMethod()
                }
                bottomBar.show(supportFragmentManager, "bottomBar")
            }
        }
        return true
    }

    private fun setUpSortMethod() {
        //Log.d("App", "Note 13L: ${ viewModel.getNote(13L) }")
        Log.d("App", "Set up sort method..")
        if (selectedSortFilter != Constants.BY_DATE_ADDED) {
            val param = when(selectedSortFilter) {
                Constants.BY_DATE_MODIFIED -> "dateModified"
                Constants.BY_TITLE -> "title"
                else -> "title"
            }
            Log.d("App", "Data gotten by param: $param outside")
            viewModel.getN("").observe(this, Observer {
                Log.d("App", "Data gotten by param: $param inside")
                it.forEach { Log.d("App", "$it") }
            })
        } else {
            viewModel.getNotes().observe(this, Observer {
                adapter.submitList(it as MutableList<Note>)
            })
        }
        adapter.notifyDataSetChanged()
    }

    fun bookmark(note: Note) {
        note.isBookmarked = !note.isBookmarked
        viewModel.updateNote(note).let { adapter.notifyDataSetChanged() }
    }

    fun share(note: Note) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_text_template, note.title, note.content))
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))
    }

    fun lock(noteId: Int) {

    }

    fun delete(note: Note) {
        viewModel.deleteNote(note).let { adapter.notifyDataSetChanged() }
        Snackbar.make(
            binding.root, "Note Deleted Successfully", Snackbar.LENGTH_LONG
        ).setAction("Undo") {
            viewModel.addNote(note)
        }.show()
    }

    /*private fun refresh() {
        adapter.submitList(viewModel.getNotes().value)
    }
*/
}
