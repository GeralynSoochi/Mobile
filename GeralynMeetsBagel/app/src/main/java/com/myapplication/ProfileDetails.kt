package com.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import com.myapplication.databinding.ActivityProfileDetailsBinding

class ProfileDetails : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {
    private lateinit var binding: ActivityProfileDetailsBinding
    private val REQ_CODE = 3
    private lateinit var dateUser: String
    private lateinit var dateDrawable: String
    private lateinit var dateDetails: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ratingBar = binding.ratingBar
        ratingBar.onRatingBarChangeListener = this
        val bundle: Bundle? = intent.extras
        dateUser = intent.getStringExtra("dateUser")
        dateDetails = intent.getStringExtra("dateDetails")

        if (dateUser != null && dateDetails != null) {
            binding.userTv.text = dateUser
            binding.detailsTv.text = dateDetails
        }

    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
            Toast.makeText(this@ProfileDetails, "Given rating is: $p1", Toast.LENGTH_SHORT).show()
    }

    private fun populateResults() {
        if (dateUser != null && dateDrawable != null && dateDetails != null) {
            binding.userTv.text = dateUser
        }
    }
}