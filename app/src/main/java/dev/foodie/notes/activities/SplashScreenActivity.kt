package dev.foodie.notes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import dev.foodie.notes.MainActivity
import dev.foodie.notes.R

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        }, 2500)
    }
}
