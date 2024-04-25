package com.example.opscpoe

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Add_task : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)


        // Find the "Add task" button by its ID
        val addTaskButton = findViewById<Button>(R.id.addTask)

        // Find other buttons by their IDs
        val dailyGoalsButton = findViewById<Button>(R.id.dailyGoals)
        val graphButton = findViewById<Button>(R.id.Graph)
        val nextPageButton = findViewById<Button>(R.id.NextPage)

        // Find the ImageView by its ID
        val imageView = findViewById<ImageView>(R.id.noDataImage)

        // Set OnClickListener on the "Add task" button
        addTaskButton.setOnClickListener {
            displayTaskInImageView(imageView, getString(R.string.add_task))
        }

        // Set OnClickListener on the "Daily Goals" button
        dailyGoalsButton.setOnClickListener {
            displayTaskInImageView(imageView, getString(R.string.add_daily_goals))
        }

        // Set OnClickListener on the "Graph" button
        graphButton.setOnClickListener {
            displayTaskInImageView(imageView, getString(R.string.view_graphs))
        }

        // Set OnClickListener on the "NextPage" button
        nextPageButton.setOnClickListener {
            displayTaskInImageView(imageView, getString(R.string.view_next_page))
        }
    }

    private fun displayTaskInImageView(imageView: ImageView, message: String) {
        // Load corresponding entry/image into the ImageView (you can customize this based on your app logic)
        // For demonstration, we'll just display a placeholder image
        imageView.setImageResource(R.drawable.task_image)

        // Show a toast message indicating the action performed
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
