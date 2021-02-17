package com.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val REQ_CODE = 1
    private lateinit var name: String
    private lateinit var age: String
    private lateinit var interest: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readFile()
    }

    fun createProfile(view: View) {
        val it = Intent(this, Profile::class.java)
        startActivityForResult(it, REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) // data is intent you are rececing from the child activity

        if (requestCode == REQ_CODE && resultCode == RESULT_OK && data != null) {
//            val name = data.getStringExtra("name")
//            val age = data.getStringExtra("age")
//            val interest = data.getStringExtra("interest")
            readFile()
            // might wanna check if variables are not null
//            Toast.makeText(this, "$name $age $interest", Toast.LENGTH_SHORT).show()
        }
        //        else {
//            Toast.makeText(this, "You do not have a profile, please create one!", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun readFile() {
        if (!fileExist("profile.txt")) {
            return
        }

        val scan = Scanner(openFileInput("profile.txt"))

        while(scan.hasNextLine()) {
            val line = scan.nextLine()
            val pieces = line.split("\t")
            name = pieces[0]
            age = pieces[1]
            interest = pieces[2]
        }

        val profileIt = Intent(this, DatingProfiles::class.java)
        startActivity(profileIt)
    }

    private fun fileExist(fname: String?): Boolean {
        val file = baseContext.getFileStreamPath(fname)
        return file.exists()
    }



}