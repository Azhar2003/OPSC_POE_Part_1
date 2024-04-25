package com.example.opscpoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.opscpoe.R.*

class AllCategories : AppCompatActivity() {
    private lateinit var backBtn: ImageView
    private lateinit var expandAllBtn1: Button
    private lateinit var totalHoursBtn1: Button
    private lateinit var expandAllBtn2: Button
    private lateinit var totalHoursBtn2: Button
    private lateinit var expandAllBtn3: Button
    private lateinit var totalHoursBtn3: Button
    private lateinit var expandAllBtn4: Button
    private lateinit var totalHoursBtn4: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_all_categories)

        // Hooks
        backBtn = findViewById(id.back_pressed)
        expandAllBtn1 = findViewById(id.expand_all_button1)
        totalHoursBtn1 = findViewById(id.total_hours_button1)
        expandAllBtn2 = findViewById(id.expand_all_button2)
        totalHoursBtn2 = findViewById(id.total_hours_button2)
        expandAllBtn3 = findViewById(id.expand_all_button3)
        totalHoursBtn3 = findViewById(id.total_hours_button3)
        expandAllBtn4 = findViewById(id.expand_all_button4)
        totalHoursBtn4 = findViewById(id.total_hours_button4)

        backBtn.setOnClickListener {
            super.onBackPressed()
        }

        // Set OnClickListener for expandAllBtn1
        expandAllBtn1.setOnClickListener {
            // Add functionality here
            Toast.makeText(this, "Expand All 1 button clicked", Toast.LENGTH_SHORT).show()
            // You can add code here to expand all categories or perform any other action for expandAllBtn1
        }

        // Set OnClickListener for totalHoursBtn1
        totalHoursBtn1.setOnClickListener {
            // Add functionality here
            Toast.makeText(this, "Total Hours 1 button clicked", Toast.LENGTH_SHORT).show()
            // You can add code here to calculate total hours or perform any other action for totalHoursBtn1
        }

        // Set OnClickListener for expandAllBtn2
        expandAllBtn2.setOnClickListener {
            // Add functionality here
            Toast.makeText(this, "Expand All 2 button clicked", Toast.LENGTH_SHORT).show()
            // You can add code here to expand all categories or perform any other action for expandAllBtn2
        }

        // Set OnClickListener for totalHoursBtn2
        totalHoursBtn2.setOnClickListener {
            // Add functionality here
            Toast.makeText(this, "Total Hours 2 button clicked", Toast.LENGTH_SHORT).show()
            // You can add code here to calculate total hours or perform any other action for totalHoursBtn2
        }

        // Set OnClickListener for expandAllBtn3
        expandAllBtn3.setOnClickListener {
            // Add functionality here
            Toast.makeText(this, "Expand All 3 button clicked", Toast.LENGTH_SHORT).show()
            // You can add code here to expand all categories or perform any other action for expandAllBtn3
        }

        // Set OnClickListener for totalHoursBtn3
        totalHoursBtn3.setOnClickListener {
            // Add functionality here
            Toast.makeText(this, "Total Hours 3 button clicked", Toast.LENGTH_SHORT).show()
            // You can add code here to calculate total hours or perform any other action for totalHoursBtn3
        }

        // Set OnClickListener for expandAllBtn4
        expandAllBtn4.setOnClickListener {
            // Add functionality here
            Toast.makeText(this, "Expand All 4 button clicked", Toast.LENGTH_SHORT).show()
            // You can add code here to expand all categories or perform any other action for expandAllBtn4
        }

        // Set OnClickListener for totalHoursBtn4
        totalHoursBtn4.setOnClickListener {
            // Add functionality here
            Toast.makeText(this, "Total Hours 4 button clicked", Toast.LENGTH_SHORT).show()
            // You can add code here to calculate total hours or perform any other action for totalHoursBtn4
        }
    }
}
