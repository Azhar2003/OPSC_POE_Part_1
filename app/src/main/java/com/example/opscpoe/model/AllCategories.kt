package com.example.opscpoe.model

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.opscpoe.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllCategories : AppCompatActivity() {
    private lateinit var backBtn: ImageView
    private lateinit var expandAllButtons: List<Button>
    private lateinit var totalHoursButtons: List<Button>
    private val activityScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_categories)

        initializeUI()
        setButtonListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        activityScope.cancel() // Cancel coroutines when the activity is destroyed
    }

    private fun initializeUI() {
        backBtn = findViewById(R.id.back_pressed)
        expandAllButtons = listOf(
            findViewById(R.id.expand_all_button1),
            findViewById(R.id.expand_all_button2),
            findViewById(R.id.expand_all_button3),
            findViewById(R.id.expand_all_button4)
        )
        totalHoursButtons = listOf(
            findViewById(R.id.total_hours_button1),
            findViewById(R.id.total_hours_button2),
            findViewById(R.id.total_hours_button3),
            findViewById(R.id.total_hours_button4)
        )
    }

    private fun setButtonListeners() {
        backBtn.setOnClickListener { onBackPressed() }

        expandAllButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                expandCategory(index + 1)
            }
        }

        totalHoursButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                showTotalHours(index + 1)
            }
        }
    }

    private fun expandCategory(categoryId: Int) {
        Toast.makeText(this, "Expanding Category $categoryId", Toast.LENGTH_SHORT).show()
        // This could trigger expanding a section in a RecyclerView, for example
    }

    private fun showTotalHours(categoryId: Int) {
        activityScope.launch {
            val totalHours = withContext(Dispatchers.IO) {
                calculateHoursForCategory(categoryId)
            }
            Toast.makeText(this@AllCategories, "Total hours for Category $categoryId: $totalHours hours", Toast.LENGTH_LONG).show()
        }
    }

    private fun calculateHoursForCategory(categoryId: Int): Double {
        // Simulate a database or network call to fetch data
        return categoryId * 5.0 // Dummy calculation
    }
}
