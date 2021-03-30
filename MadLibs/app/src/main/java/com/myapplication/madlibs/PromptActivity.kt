package com.myapplication.madlibs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.madlibs.databinding.ActivityPromptBinding
import java.util.*

class PromptActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPromptBinding
    private var fileName = ""
    private var wordTypes = ArrayList<String>()
    private var userInputs = ArrayList<String>()
    private var currentWordTypeIndex = 0
    private var allowedWords = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            fileName = extras.getString("fileName")!!

            readResFile(fileName)
            readDictFile()
            binding.progressBar.max = wordTypes.size
            updateWordsLeft()
            updateHintAndText()
        }
    }

    private fun readResFile(fileName: String) {
        val fileId = resources.getIdentifier(fileName, "raw", this.packageName)

        val scanner = Scanner(resources.openRawResource(fileId))
        while(scanner.hasNextLine()) {
            val line = scanner.nextLine()

            // For each line, need split the words and find out the type of word in placeholder
            val words = line.split(" ") // split by white space
            for (word in words) {
                if (word.length >= 1) {
                    val firstChar = word.substring(0, 1)
                    if (firstChar == "<") {
                        // remove the "<" and ">" from the word
                        val wordType = word.substring(1, word.length-1).toLowerCase()
                        // store in wordTypes array
                        wordTypes.add(wordType)
                    }
                }
            }
        }
    }

    private fun readDictFile() {
        val scan = Scanner(resources.openRawResource(R.raw.dict))

        while (scan.hasNextLine()) {
            val line = scan.nextLine()
            allowedWords.add(line)
        }
    }

    private fun updateWordsLeft() {
        val numWordsLeft = wordTypes.size - currentWordTypeIndex
        binding.wordsLeftTv.text = "Number of words left: $numWordsLeft."
        binding.progressBar.progress = currentWordTypeIndex
    }

    private fun updateHintAndText() {
        val currentWordType = wordTypes[currentWordTypeIndex]
        binding.wordEt.hint = "$currentWordType"
        binding.promptTV.text = "Please type a/an $currentWordType"
    }

    // store userInput if exist in dictionary and not more than wordTypes size
    fun submit(view: View) {
        val userInput = binding.wordEt.text.toString().trim()

        if (userInput == "") {
            Toast.makeText(this, "Please input a word!", Toast.LENGTH_SHORT).show()
            binding.wordEt.text.clear()
            return
        }

        if (userInput in allowedWords && userInputs.size < wordTypes.size) {
            Toast.makeText(this, "Great! Keep going!", Toast.LENGTH_SHORT).show()
            userInputs.add(userInput)
            binding.wordEt.text.clear()

            currentWordTypeIndex++

            if (currentWordTypeIndex != wordTypes.size) {
                updateHintAndText()
            }

            updateWordsLeft()
        } else {
            Toast.makeText(this, "Sorry, the word \"$userInput\" does not exist. Please try again.", Toast.LENGTH_SHORT).show()
            binding.wordEt.text.clear()
        }

        // if userInputs size == wordTypes size, start replacing the words.
        if (userInputs.size == wordTypes.size) {
            constructStory(fileName)
        }
    }

    private fun constructStory(fileName : String) {
        var result = ""

        val fileId = resources.getIdentifier(fileName, "raw", this.packageName)
        val scanner = Scanner(resources.openRawResource(fileId))

        var currentUserInputIndex = 0
        while(scanner.hasNextLine()) {
            val line = scanner.nextLine()

            val words = line.split(" ") // split by white space
            for (word in words) {
                if (word.length >= 1) {
                    // detect placeholder, replace with userInput
                    val firstChar = word.substring(0, 1)
                    if (firstChar == "<") {
                        val currentUserInput = userInputs[currentUserInputIndex]
                        result += "$currentUserInput "
                        currentUserInputIndex++
                    } else {
                        result += "$word "
                    }
                }
            }
            result += "\n"
        }

        result = result.substring(0, result.length - 1) // remove last whitespace

        Toast.makeText(this, "Your story has been generated!", Toast.LENGTH_LONG).show()
        //pass this result into intent
        val it = Intent(this, DisplayActivity::class.java)
        it.putExtra("result", result)
        startActivity(it)
    }
}