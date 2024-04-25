package com.example.opscpoe

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DailyGoals : AppCompatActivity() {
    private lateinit var addTaskTitle: EditText
    private lateinit var addTaskDescription: EditText
    private lateinit var taskDate: EditText
    private lateinit var addTaskButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_goals)

        // Initialize views
        addTaskTitle = findViewById(R.id.addTaskTitle)
        addTaskDescription = findViewById(R.id.addTaskDescription)
        taskDate = findViewById(R.id.taskDate)
        addTaskButton = findViewById(R.id.addTask)

        // Set click listener for the add task button
        addTaskButton.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        val title = addTaskTitle.text.toString().trim()
        val description = addTaskDescription.text.toString().trim()
        val date = taskDate.text.toString().trim()

        // Check if any field is empty
        if (title.isEmpty() || description.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Add your logic here to save the task to a database or perform any necessary action
        // For demonstration, let's just display the task details
        val message = "Title: $title\nDescription: $description\nDate: $date"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        // Clear input fields after adding the task
        addTaskTitle.text.clear()
        addTaskDescription.text.clear()
        taskDate.text.clear()
    }
}
