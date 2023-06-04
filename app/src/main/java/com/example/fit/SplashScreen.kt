package com.example.fit

import android.animation.AnimatorInflater
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val logo: ImageView = findViewById(R.id.logo)
        val animator = AnimatorInflater.loadAnimator(this,R.animator.logo_alimate)
        animator.setTarget(logo)
        animator.start()

        val timer = object:CountDownTimer(5000,1000){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                val intent = Intent(this@SplashScreen,MainActivity::class.java)
                startActivity(intent)
            }
        }.start()
    }
}