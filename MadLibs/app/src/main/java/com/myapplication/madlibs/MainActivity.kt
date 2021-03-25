package com.myapplication.madlibs

import android.R.raw
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.madlibs.databinding.ActivityMainBinding
import com.myapplication.madlibs.databinding.ActivityPromptBinding
import java.io.File
import java.io.PrintStream
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
//        val fileId = pickRandomFileId()
        val fileName = pickRandomFileName()

        val it = Intent(this, PromptActivity::class.java)

//        intent.putExtra("fileId", fileId)
        it.putExtra("fileName", fileName)
        startActivity(it)
    }

    // temp not ideal version for now
    private fun pickRandomFileName() : String {
        val fileNames = ArrayList<String>()
        fileNames.add("madlib0_simple")
        fileNames.add("madlib1_tarzan")
        fileNames.add("madlib2_university")
        fileNames.add("madlib3_clothes")
        fileNames.add("madlib4_dance")

        val rand = Random()
        val chosenFileName = fileNames[rand.nextInt(fileNames.size)]
        Log.i("chosenFileName", "$chosenFileName")

        return chosenFileName
    }

    // gets all fileIds in res/raw folder, then pick one random id.
    private fun pickRandomFileId() : Int {
        val fileIds = allRawFileIds()

        val rand = Random()
        val chosenFileId = fileIds[rand.nextInt(fileIds.size)]
        Log.i("File", "$chosenFileId")

        return chosenFileId
    }

    // get all ids of all raw files (Issue is how to exclude dict.txt?)
    private fun allRawFileIds(): List<Int> {
        val resIds: MutableList<Int> = ArrayList()
        val fields = raw::class.java.declaredFields
        for (field in fields) {
            try {
                val resId = field.getInt(field)
                resIds.add(resId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return resIds
    }

}