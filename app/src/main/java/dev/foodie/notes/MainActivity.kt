package dev.foodie.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.foodie.notes.activities.ViewEditActivity
import dev.foodie.notes.models.Note
import dev.foodie.notes.viewmodels.NoteViewModel
import dev.foodie.notes.viewmodels.NoteViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var viewModelFactory: NoteViewModelFactory
    private val TAG = "MainActivity"

    private val REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
        viewModelFactory = NoteViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NoteViewModel::class.java)
        Log.d("MainActivity", "Hey!!")
        viewModel.getNotes()?.observe(this, Observer {
            it.forEach { note -> Log.d("MainActivity", note.toString()) }
        })

        add_note_fab.setOnClickListener {
            Log.d(TAG, "Getting here...")
            val intent = Intent(this@MainActivity, ViewEditActivity::class.java)
            intent.apply {
                putExtra("note", Note(title = "Note from first activity!"))
            }
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        Log.d("MainActivity", "Result Code: $resultCode")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            R.id.nav_archived -> item.isChecked = !item.isChecked
        }
        return true
    }

}
