package com.example.opscpoe.model

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.opscpoe.R

class DailyGoals : AppCompatActivity() {
    private lateinit var addButton: Button
    private lateinit var previousButton: Button
    private lateinit var minGoalInput: EditText
    private lateinit var maxGoalInput: EditText
    private lateinit var dateInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_goals)

        // Initialize the UI components
        addButton = findViewById(R.id.addTask)
        previousButton = findViewById(R.id.PreviousPage)
        minGoalInput = findViewById(R.id.addTaskTitle) // Assuming this is the minimum goal input
        maxGoalInput = findViewById(R.id.addTaskDescription) // Assuming this is the maximum goal input
        dateInput = findViewById(R.id.taskDate) // Date input

        // Setup button listeners
        addButton.setOnClickListener { addGoals() }
        previousButton.setOnClickListener { finish() } // Assuming you want to go back to the previous activity
    }

    private fun addGoals() {
        val minGoal = minGoalInput.text.toString()
        val maxGoal = maxGoalInput.text.toString()
        val date = dateInput.text.toString()

        if (minGoal.isBlank() || maxGoal.isBlank() || date.isBlank()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Here you would typically handle the saving of these goals
        Toast.makeText(this, "Goals added for $date: Min: $minGoal, Max: $maxGoal", Toast.LENGTH_LONG).show()

        // Clear inputs after adding (optional)
        minGoalInput.text.clear()
        maxGoalInput.text.clear()
        dateInput.text.clear()
    }
}


