package com.example.opscpoe

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Create_Timesheet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_timesheet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val previousPageButton: Button = findViewById(R.id.PreviousPage)
        previousPageButton.setOnClickListener {
            // Navigate back to the previous page
            onBackPressed()
        }

        val addTaskButton: Button = findViewById(R.id.addTask)
        addTaskButton.setOnClickListener {
            // Add task functionality here
            addTask()
        }
    }

    private fun addTask() {
        // Get references to the EditText fields
        val taskTitleEditText = findViewById<EditText>(R.id.addTaskTitle)
        val taskDescriptionEditText = findViewById<EditText>(R.id.addTaskDescription)
        val taskDateEditText = findViewById<EditText>(R.id.taskDate)
        val taskTimeEditText = findViewById<EditText>(R.id.taskTime)
        val taskEndTimeEditText = findViewById<EditText>(R.id.taskEndTime)

        // Get the text from the EditText fields
        val taskTitle = taskTitleEditText.text.toString()
        val taskDescription = taskDescriptionEditText.text.toString()
        val taskDate = taskDateEditText.text.toString()
        val taskTime = taskTimeEditText.text.toString()
        val taskEndTime = taskEndTimeEditText.text.toString()

        // Here you can perform the necessary actions to add the task,
        // such as saving it to a database or displaying it in a list.

        // For now, let's just display a toast message with the task details
        val message = "Task added:\nTitle: $taskTitle\nDescription: $taskDescription\nDate: $taskDate\nStart Time: $taskTime\nEnd Time: $taskEndTime"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        // Clear the EditText fields after adding the task
        taskTitleEditText.text.clear()
        taskDescriptionEditText.text.clear()
        taskDateEditText.text.clear()
        taskTimeEditText.text.clear()
        taskEndTimeEditText.text.clear()
    }
}
