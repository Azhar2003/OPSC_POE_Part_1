package com.example.opscpoe.model

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.opscpoe.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get reference to the Sign up button
        val signUpButton: Button = findViewById(R.id.SignUp)

        // Set onClickListener for the Sign up button
        signUpButton.setOnClickListener {
            // Create Intent to navigate to SignUpActivity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent) // Start SignUpActivity
        }

        // Get reference to the Login button
        val loginButton: Button = findViewById(R.id.Login)

        // Set onClickListener for the Login button
        loginButton.setOnClickListener {
            // Create Intent to navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // Start LoginActivity
        }
    }

    fun buttonClick(view: View) {}
}
