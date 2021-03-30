package com.myapplication.madlibs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.madlibs.databinding.ActivityMainBinding
import java.lang.reflect.Field
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // picks a random file id and passes it to next PromptActivity
    fun startGame(view: View) {
        val fileName = pickRandomFileName()

        val it = Intent(this, PromptActivity::class.java)

        it.putExtra("fileName", fileName)
        startActivity(it)
    }

    private fun pickRandomFileName() : String {
        val fileNames = ArrayList<String>()

        val fields: Array<Field> = R.raw::class.java.fields
        fields.forEach {field ->
            if (field.name != "dict") {
                fileNames.add(field.name)
            }
        }

        val rand = Random()
        val chosenFileName = fileNames[rand.nextInt(fileNames.size)]

        return chosenFileName
    }

}