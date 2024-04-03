package com.example.scrollingreccyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.scrollingreccyclerview.userdetail.UserDetailsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val backgroundText: ImageView = findViewById(R.id.splashImage)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_anim)
        backgroundText.startAnimation(slideAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, UserDetailsActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)


    }
}