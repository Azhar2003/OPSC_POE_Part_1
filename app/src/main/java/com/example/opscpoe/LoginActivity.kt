package com.example.opscpoe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var tvRedirectLogin: TextView
    private lateinit var etSEmailAddress: EditText
    private lateinit var etSPassword: EditText
    private lateinit var btnSSigned: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize UI components
        tvRedirectLogin = findViewById(R.id.tvRedirectLogin)
        etSEmailAddress = findViewById(R.id.etSEmailAddress)
        etSPassword = findViewById(R.id.etSPassword)
        btnSSigned = findViewById(R.id.btnSSigned)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Set up the sign-in button click listener
        btnSSigned.setOnClickListener {
            performLogin()
        }

        // Set up the redirect to sign-up activity
        tvRedirectLogin.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            // Removed finish() to allow users to return to the login activity using the back button.
            finish()
        }
    }

    private fun performLogin() {
        val email = etSEmailAddress.text.toString()
        val password = etSPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                // Consider adding an Intent to navigate the user to the main part of your application here.
            } else {
                Toast.makeText(this, "Log In failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
