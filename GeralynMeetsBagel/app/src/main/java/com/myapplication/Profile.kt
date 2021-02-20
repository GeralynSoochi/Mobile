package com.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.databinding.ActivityProfileBinding
import java.io.PrintStream


class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val goBack = Intent()
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2
    private lateinit var imageBitmap: Bitmap
    private lateinit var imageURI: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun saveProfile(view: View) {
        val name = binding.name.text.toString()
        val age = binding.age.text.toString()
        val interest = binding.interest.text.toString()

        if (name != null && age != null && interest != null) {

            val output = PrintStream(openFileOutput("profile.txt", MODE_PRIVATE))
            output.println(name + "\t" + age + "\t" + interest + "\t")
            output.close()

            setResult(RESULT_OK, goBack)
            finish()
        }
    }

    fun returnMain(view: View) {
        setResult(RESULT_CANCELED, goBack)
        finish()
    }

    fun takePhoto(view: View) {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE)
    }

    fun choosePhoto(view: View) {
        val choosePhotoIntent = Intent(Intent.ACTION_PICK)
        choosePhotoIntent.type = "image/*"
        startActivityForResult(choosePhotoIntent, REQUEST_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    imageBitmap = data.extras!!.get("data") as Bitmap
                    val avatarImageView = binding.avatarIv
                    avatarImageView.setImageBitmap(imageBitmap)
                }
                REQUEST_PICK_IMAGE -> {
                    imageURI = data.data!!
                    val avatarImageView = binding.avatarIv
                    avatarImageView.setImageURI(imageURI)
                }
            }
        }
    }

//    private fun saveImageToFile(imageView: ImageView) {
//        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
//
//        // Create a file to save the image
//        file = File(file, "imageBitMap")
//    }


    //    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putInt("points", points)
//    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        points = savedInstanceState.getInt("points")
//        Log.i("points", "onRestoreInstance $points")
//    }
}