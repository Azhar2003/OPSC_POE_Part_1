package com.example.opscpoe

import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.android.synthetic.main.activity_item_task.*
import com.example.opscpoe.R.*

class ItemTask : AppCompatActivity() {
    
    
    //PUT IT HERE!private lateinit var backBtn: ImageView

    val taskTitleEditText = findViewById<EditText>(id.addTaskTitle)
    val taskDescriptionEditText = findViewById<EditText>(id.addTaskDescription)
    val taskDateEditText = findViewById<EditText>(id.taskDate)
    val taskTimeEditText = findViewById<EditText>(id.taskTime)
    val taskEndTimeEditText = findViewById<EditText>(id.taskEndTime)
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.activity_item_task)
        
        //PUT IT HERE!
        val taskTitle = taskTitleEditText.text.toString()
        val taskDescription = taskDescriptionEditText.text.toString()
        val taskDate = taskDateEditText.text.toString()
        val taskTime = taskTimeEditText.text.toString()
        val taskEndTime = taskEndTimeEditText.text.toString()


        // Example data
        val titleText = "Programming 3A"
        val descriptionText = "Programming, sprint 3, 13/03/24, 27/03/24"
        val statusText = "COMPLETED"

        // Bind data to views
        title.text = titleText
        description.text = descriptionText
        status.text = statusText

        // Additional code for handling UI events goes here
        //dont forget to add image adder
    }
}

