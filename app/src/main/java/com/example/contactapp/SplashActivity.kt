package com.example.contactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.contactapp.databinding.ActivitySplashBinding
import com.example.contactapp.signIn.SignInActivity

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val splashImage = binding.imageSplash

        Glide.with(this).load(R.raw.splash_background).into(splashImage)

        splashImage.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}