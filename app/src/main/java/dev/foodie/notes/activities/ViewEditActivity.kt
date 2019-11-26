package dev.foodie.notes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import dev.foodie.notes.R
import kotlinx.android.synthetic.main.activity_view_edit.*

class ViewEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_edit)

        setSupportActionBar(view_edit_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_edit_menu, menu)
        return true
    }
}
