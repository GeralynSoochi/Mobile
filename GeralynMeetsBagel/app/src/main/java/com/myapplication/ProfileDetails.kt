package com.myapplication

import android.content.Intent
import android.media.Rating
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.Toast
import com.myapplication.databinding.ActivityProfileDetailsBinding
import java.io.PrintStream
import java.util.*

class ProfileDetails : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {
    private lateinit var binding: ActivityProfileDetailsBinding
    private val REQ_CODE = 3
    private lateinit var dateUser: String
    private lateinit var dateDrawable: String
    private lateinit var dateDetails: String
    private lateinit var ratingBar: RatingBar
    private var userRating = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ratingBar = binding.ratingBar
        ratingBar.onRatingBarChangeListener = this
        dateUser = intent.getStringExtra("dateUser")
        dateDetails = intent.getStringExtra("dateDetails")

        if (!fileExist("user_profiles.txt") && dateUser != null && dateDetails != null) {
            binding.userTv.text = dateUser
            binding.detailsTv.text = dateDetails
        } else {
            readFile()
            binding.userTv.text = dateUser
            binding.detailsTv.text = dateDetails
            ratingBar.rating = userRating
        }
    }

    private fun readFile() {
        val scan = Scanner(openFileInput("user_profiles.txt"))

        while(scan.hasNextLine()) {
            val line = scan.nextLine()
            val pieces = line.split("\t")
            dateUser = pieces[0]
            dateDetails = pieces[1]
            userRating = pieces[2].toFloat()
        }
    }

    private fun fileExist(fname: String?): Boolean {
        val file = baseContext.getFileStreamPath(fname)
        return file.exists()
    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        userRating = ratingBar.rating
        saveUserDetails()
    }

    private fun saveUserDetails() {
        Toast.makeText(this@ProfileDetails, "User rating when saved is $userRating", Toast.LENGTH_SHORT).show()
        val output = PrintStream(openFileOutput("user_profiles.txt", MODE_PRIVATE))
        output.println(dateUser + "\t" + dateDetails + "\t" + userRating + "\t")
        output.close()
    }
}