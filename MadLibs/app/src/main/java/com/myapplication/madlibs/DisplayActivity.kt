package com.myapplication.madlibs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.myapplication.madlibs.databinding.ActivityDisplayBinding

class DisplayActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            val result = extras.getString("result")
            binding.story.text = result
        }
    }

    fun makeStory(view: View) {
        val it = Intent(this, MainActivity::class.java)
        startActivity(it)
    }
}