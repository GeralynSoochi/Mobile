package com.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import com.myapplication.databinding.ActivityProfileDetailsBinding
import java.util.*

class ProfileDetails : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {
    private lateinit var binding: ActivityProfileDetailsBinding
    private val REQ_CODE = 3
    private lateinit var dateUser: String
    private lateinit var dateDetails: String
    private lateinit var ratingBar: RatingBar
    private lateinit var filename: String
    private var userRating = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ratingBar = binding.ratingBar
        dateUser = intent.getStringExtra("dateUser")
        dateDetails = intent.getStringExtra("dateDetails")
        var dateResource = intent.getIntExtra("dateResource", 0)
        filename = "" + dateResource + "_rating.txt"

        if (dateUser != null && dateDetails != null && dateResource != 0) {
            binding.userTv.text = dateUser
            binding.detailsTv.text = dateDetails
            binding.imageView.setImageResource(dateResource)
        }
        if (fileExist(filename)) {
            readFile(filename)
            ratingBar.rating = userRating
        }

        ratingBar.onRatingBarChangeListener = this
    }

    private fun readFile(fname: String) {
        val scan = Scanner(openFileInput(fname))

        while(scan.hasNextLine()) {
            val line = scan.nextLine()
            val pieces = line.split("\t")
            var fileUser = pieces[0]
            if (fileUser == dateUser) {
                userRating = pieces[1].toFloat()
            } else {
                userRating = 0F
            }
        }
        scan.close()
    }

    private fun fileExist(fname: String): Boolean {
        val file = baseContext.getFileStreamPath(fname)
        return file.exists()
    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        userRating = ratingBar.rating

        val goBack = Intent()
        goBack.putExtra("dateUser", dateUser)
        goBack.putExtra("filename", filename)
        goBack.putExtra("userRating", userRating)
        setResult(RESULT_OK, goBack)
        finish()
    }


}