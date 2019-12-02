package dev.foodie.notes.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
            Note()
        }
        isBookmarked = note.isBookmarked
        tags = note.tags

        binding.note = note

        setSupportActionBar(view_edit_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.nav_share -> { /* TODO: handle share logic */ }
            R.id.nav_lock -> { /* TODO: handle lock logic */ }
            R.id.nav_archive -> { /* TODO: handle archive logic */ }
            R.id.nav_delete -> { /* TODO: handle delete logic */ }
            R.id.nav_tags -> tag()
            R.id.nav_bookmark -> { /* TODO: handle bookmark logic */ }
            R.id.nav_save -> save()
        }

        return true
    }

    fun tag() {
        val dialog: TagsAlertDialog

        if (note.tags != Constants.UNCATEGORIZED) {
            dialog = TagsAlertDialog(note.tags)
            dialog.show(supportFragmentManager, "Tags")
            return
        }

        dialog = TagsAlertDialog()

        dialog.show(supportFragmentManager, "Tags")
    }

    fun save() {
        if (TextUtils.isEmpty(title_edit_text.text) || TextUtils.isEmpty(content_edit_text.text)) {
            Snackbar.make(title_edit_text, "Empty fields!", Snackbar.LENGTH_SHORT).show()
            return
        }

        if (
            (note.title != title_edit_text.text.toString()) || (note.content != content_edit_text.text.toString())
                ) {
            note.title = title_edit_text.text.toString()
            note.content = content_edit_text.text.toString()

            note.lastModified = System.currentTimeMillis()
        }

        if (isBookmarked != note.isBookmarked) {
            note.isBookmarked = isBookmarked
        }

        if (tags != note.tags) {
            note.tags = tags
            note.lastModified = System.currentTimeMillis()
        }

        editMode = true

        val _intent = Intent()
        _intent.extras?.putSerializable("note", note)
        _intent.extras?.putBoolean("editMode", editMode)
        setResult(Activity.RESULT_OK, _intent)
        finish()
    }

    override fun onNavigateUp(): Boolean {
        Log.d("App", "Navigate up called!")
        return true
    }

    override fun tagSelected(tag: String) {
        note.tags = tag
        Log.d("App", tag)
    }

}
