package dev.foodie.notes.activities

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import dev.foodie.notes.R
import dev.foodie.notes.databinding.ActivityViewEditBinding
import dev.foodie.notes.dialogs.OnTagSelectedListener
import dev.foodie.notes.dialogs.TagsAlertDialog
import dev.foodie.notes.models.Note
import dev.foodie.notes.utils.Constants
import kotlinx.android.synthetic.main.activity_view_edit.*

class ViewEditActivity : AppCompatActivity(), OnTagSelectedListener {

    private lateinit var binding: ActivityViewEditBinding
    private lateinit var note: Note
    private var isBookmarked: Boolean = false
    private var tags: String = ""

    private var editMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_edit)

        note = if (intent.extras?.getSerializable("note") != null) {
            (intent.extras?.getSerializable("note") as Note).apply { editMode = true }
        } else {
            Note(tags = Constants.UNCATEGORIZED)
        }
        isBookmarked = note.isBookmarked
        tags = note.tags

        binding.note = note

        setSupportActionBar(view_edit_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_edit_menu, menu)

        if (isBookmarked) {
            view_edit_toolbar.menu.findItem(R.id.nav_bookmark).setIcon(R.drawable.bookmarked)
        } else {
            view_edit_toolbar.menu.findItem(R.id.nav_bookmark).setIcon(R.drawable.bookmark_outline)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.nav_share -> {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_text_template, note.title, note.content))
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(intent, null))
            }
            R.id.nav_tags -> tag()
            R.id.nav_bookmark -> bookmark()
            R.id.nav_save -> save()
        }

        return true
    }

    fun bookmark() {
        isBookmarked = !isBookmarked
        note.isBookmarked = isBookmarked
        if (isBookmarked) {
            showSnackbar("Bookmarked")
            view_edit_toolbar.menu.findItem(R.id.nav_bookmark).setIcon(R.drawable.bookmarked)
        } else {
            showSnackbar("Unbookmarked")
            view_edit_toolbar.menu.findItem(R.id.nav_bookmark).setIcon(R.drawable.bookmark_outline)
        }
    }

    fun tag() {
        val idx = getIdx(note.tags)
        val dialog = TagsAlertDialog(idx)

        dialog.show(supportFragmentManager, "Tags")
    }

    private fun getIdx(tag: String): Int {
        return when(tag) {
            Constants.UNCATEGORIZED -> 1
            Constants.FAMIY -> 5
            Constants.STUDY -> 4
            Constants.PERSONAL -> 2
            Constants.WORK -> 3
            else -> 1
        }
    }

    private fun showSnackbar(msg: String) {
        Snackbar.make(title_edit_text, msg, Snackbar.LENGTH_SHORT).show()
    }

    private fun save() {
        if (TextUtils.isEmpty(title_edit_text.text) || TextUtils.isEmpty(content_edit_text.text)) {
            showSnackbar("Empty Fields!")
            return
        }

        note.title = binding.titleEditText.text.toString()
        Log.d("App", "Note title: ${ note.title }")
        note.content = binding.contentEditText.text.toString()

        note.lastModified = System.currentTimeMillis()
        note.isBookmarked = isBookmarked

        note.lastModified = System.currentTimeMillis()
        Log.d("App", "editMode: $editMode")

        intent.putExtra("note", note)
        intent.putExtra("editMode", editMode)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onNavigateUp(): Boolean {
        Log.d("App", "Navigate up called!")
        return true
    }

    override fun tagSelected(tag: String) {
        note.tags = tag
        Log.d("App", note.tags)
    }

}
