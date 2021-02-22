package com.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.myapplication.databinding.ActivityDatingProfilesBinding
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList

class DatingProfiles : AppCompatActivity() {
    private lateinit var binding: ActivityDatingProfilesBinding
    private lateinit var dateId: String
    private lateinit var dateUser: String
    private lateinit var dateDetails: String
    private lateinit var dateImageString: String
    private lateinit var myAdapter: ArrayAdapter<String>
    private var dateProfiles = ArrayList<String>()
    private val dateToProfiles = HashMap<String, ArrayList<String>>()

    private val REQ_CODE = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatingProfilesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readFile()
        setupList()
    }

//    fun getProfileDetails(view: View) {
//        var dateResource = 0
//        when (view.id) {
//            R.id.card1 -> {
//                dateUser = binding.date1TextView.text.toString()
//                dateDetails = "A very ambitious woman. Loves to work 9am-5pm."
//                dateResource = R.drawable.date1
//            }
//            R.id.card2 -> {
//                dateUser = binding.date2TextView.text.toString()
//                dateDetails = "Likes to smile alot. Favourite color is blue."
//                dateResource = R.drawable.date2
//            }
//            R.id.card3 -> {
//                dateUser = binding.date3TextView.text.toString()
//                dateDetails = "Clothing Model. Loves Instagram."
//                dateResource = R.drawable.date3
//            }
//            R.id.card4 -> {
//                dateUser = binding.date4TextView.text.toString()
//                dateDetails = "Geek, likes to program."
//                dateResource = R.drawable.date4
//            }
//        }
//
//        val detailsIt = Intent(this, ProfileDetails::class.java)
//        detailsIt.putExtra("dateUser", dateUser)
//        detailsIt.putExtra("dateDetails", dateDetails)
//        detailsIt.putExtra("dateResource", dateResource)
//        startActivityForResult(detailsIt, REQ_CODE)
//
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQ_CODE && data!=null) {
            val user = data.getStringExtra("dateUser")
            val filename = data.getStringExtra("filename")
            val rating = data.getFloatExtra("userRating", 0F)

            if (filename != null && user != null && rating != 0F) {
                saveUserDetails(filename, user, rating)
            }
        }
    }

    private fun saveUserDetails(fname: String, user: String, rating: Float) {
        val output = PrintStream(openFileOutput(fname, Context.MODE_PRIVATE))
        output.println(user + "\t" + rating)
        output.close()
        Toast.makeText(this, "Your rating for $user: $rating", Toast.LENGTH_SHORT).show()
    }

    private fun readFile() {
        val scanner = Scanner(resources.openRawResource((R.raw.dating_profiles)))
        while(scanner.hasNextLine()) {
            val line = scanner.nextLine()
            val pieces = line.split("|")
            
            dateId = pieces[0]
            dateUser = pieces[1]
            dateDetails = pieces[2]
            dateImageString = pieces[3]

            dateProfiles.add(dateId)
            dateProfiles.add(dateUser)
            dateProfiles.add(dateDetails)
            dateProfiles.add(dateImageString)

            dateToProfiles[dateId] = dateProfiles
        }
    }

    private fun setupList() {
        myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dateProfiles)
        binding.datingProfilesGv.adapter = myAdapter
    }




}