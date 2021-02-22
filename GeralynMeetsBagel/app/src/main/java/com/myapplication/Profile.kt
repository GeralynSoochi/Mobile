package com.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.databinding.ActivityProfileBinding
import java.io.*


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

    private fun saveImageToStream(bitmap: Bitmap) {
        var file = File(this.getFilesDir(), "profile.png")

        try {
            val stream: OutputStream = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            stream.flush()
            stream.close()
        } catch (e: IOException){
            e.printStackTrace()
        }
        return
    }

    fun saveProfile(view: View) {
        val name = binding.name.text.toString()
        val age = binding.age.text.toString()
        val interest = binding.interest.text.toString()

        val avatarImageView = binding.avatarIv
        val image = avatarImageView.drawable
        if (image == null) {
            Toast.makeText(this, "Please add a profile picture", Toast.LENGTH_SHORT).show()
            return
        }

        if (name == "") {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            return
        }

        if (age == "") {
            Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show()
            return
        }

        if (interest == "") {
            Toast.makeText(this, "Please enter your interests", Toast.LENGTH_SHORT).show()
            return
        }

        if (name != null && age != null && interest != null) {

            val output = PrintStream(openFileOutput("profile.txt", MODE_PRIVATE))
            output.println(name + "\t" + age + "\t" + interest + "\t")
            output.close()
            val bitmap = (avatarImageView.drawable as BitmapDrawable).bitmap
            saveImageToStream(bitmap)

            setResult(RESULT_OK, goBack)
            finish()
        }
    }
}