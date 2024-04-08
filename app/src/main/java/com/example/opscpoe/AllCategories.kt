package com.example.opscpoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class AllCategories : AppCompatActivity() {
    private lateinit var backBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_categories)

        // Hooks
        backBtn = findViewById(R.id.back_pressed)

        backBtn.setOnClickListener {
            super.onBackPressed()
        }
}
}