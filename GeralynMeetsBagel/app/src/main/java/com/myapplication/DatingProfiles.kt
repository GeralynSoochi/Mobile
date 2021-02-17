package com.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myapplication.databinding.ActivityDatingProfilesBinding
import com.myapplication.databinding.ActivityProfileBinding

class DatingProfiles : AppCompatActivity() {
    private lateinit var binding: ActivityDatingProfilesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dating_profiles)
        binding = ActivityDatingProfilesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



}