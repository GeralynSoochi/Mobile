package com.myapplication

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.drawToBitmap
import com.myapplication.databinding.ActivityDatingProfilesBinding
import com.myapplication.databinding.ActivityProfileBinding

class DatingProfiles : AppCompatActivity() {
    private lateinit var binding: ActivityDatingProfilesBinding
    private lateinit var dateUser: String
    private lateinit var dateDrawable: Drawable
    private lateinit var dateDetails: String
    private val REQ_CODE = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatingProfilesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun getProfileDetails(view: View) {
        when (view.id) {
            R.id.card1 -> {
                dateUser = binding.date1TextView.toString()
                dateDrawable = binding.date1ImageView.drawable
                dateDetails = "A very ambitious woman. Loves to work 9am-5pm."
            }
            R.id.card2 -> {
                dateUser = binding.date2TextView.toString()
                dateDrawable = binding.date2ImageView.drawable
                dateDetails = "Likes to smile alot. Favourite color is blue."
            }
            R.id.card3 -> {
                dateUser = binding.date3TextView.toString()
                dateDrawable = binding.date3ImageView.drawable
                dateDetails = "Clothing Model. Loves Instagram."
            }
            R.id.card4 -> {
                dateUser = binding.date4TextView.toString()
                dateDrawable = binding.date4ImageView.drawable
                dateDetails = "Geek, likes to program."
            }
        }

        Toast.makeText(this, "$dateDrawable", Toast.LENGTH_SHORT).show()

        val detailsIt = Intent(this, ProfileDetails::class.java)
        // Need to figure out how to pass drawable into intent.
        detailsIt.putExtra("dateUser", dateUser)
        detailsIt.putExtra("dateDetails", dateDetails)
        startActivityForResult(detailsIt, REQ_CODE)

    }


}