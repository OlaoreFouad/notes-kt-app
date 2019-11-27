package dev.foodie.notes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.databinding.DataBindingUtil
import dev.foodie.notes.R
import dev.foodie.notes.databinding.ActivityViewEditBinding
import dev.foodie.notes.models.Note
import kotlinx.android.synthetic.main.activity_view_edit.*

class ViewEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_edit)

        val note = if (intent.extras?.getSerializable("note") != null) {
            intent.extras?.getSerializable("note") as Note
        } else {
            Note( title = "First title!" )
        }

        binding.note = note

        Log.d("App", "Note: ${note.title}")

        setSupportActionBar(view_edit_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_edit_menu, menu)
        return true
    }

}
