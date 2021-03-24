package com.myapplication.madlibs

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.madlibs.databinding.ActivityPromptBinding
import java.io.PrintStream
import java.util.*

class PromptActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPromptBinding
//    private var fileId = 0
    private var fileName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
//            fileId = extras.getInt("fileId")
//            Log.i("fileId", "$fileId")

            fileName = extras.getString("fileName")!!
            Log.i("fileName", fileName)
        }

        readResFile(fileName)
    }

    private fun fileExist(fname: String?): Boolean {
        val file = baseContext.getFileStreamPath(fname)
        return file.exists()
    }

    private fun readFile(fileName: String) {
        if (!fileExist(fileName)) {
            return
        }

        val scan = Scanner(openFileInput(fileName))

        while (scan.hasNextLine()) {
            val line = scan.nextLine()
            val pieces = line.split("\t")
        }
    }

    private fun readResFile(fileName: String) {
        val fileId = resources.getIdentifier(fileName, "raw", this.packageName)

        val scanner = Scanner(resources.openRawResource(fileId))
        while(scanner.hasNextLine()) {
            val line = scanner.nextLine()
            Log.i("line", line)
        }
    }


    private fun writeFile(fileName: String) {
        val output = PrintStream(openFileOutput(fileName, MODE_PRIVATE))
        output.println()
        output.close()
    }
}