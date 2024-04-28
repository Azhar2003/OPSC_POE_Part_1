package com.example.opscpoe.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.opscpoe.R

class Add_Task : AppCompatActivity() {

    private lateinit var taskTitleInput: EditText
    private lateinit var taskDescriptionInput: EditText
    private lateinit var addTaskButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        taskTitleInput = findViewById(R.id.taskTitleInput)
        taskDescriptionInput = findViewById(R.id.taskDescriptionInput)
        addTaskButton = findViewById(R.id.addTaskButton)

        addTaskButton.setOnClickListener {
            val title = taskTitleInput.text.toString().trim()
            val description = taskDescriptionInput.text.toString().trim()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                saveTask(title, description)
            }
        }
    }

    private fun saveTask(title: String, description: String) {
        // Here you would include logic to save the task to a database or another storage mechanism
        // For demonstration, just show a toast
        Toast.makeText(this, "Task Saved: $title", Toast.LENGTH_SHORT).show()

        // Optionally clear the input fields
        taskTitleInput.setText("")
        taskDescriptionInput.setText("")
    }
}
