package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class sign_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signpage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        setupViews()
    }
    
    private fun setupViews() {
        // Setup password field with eye icon toggle
        val etPassword = findViewById<EditText>(R.id.etPassword)
        etPassword.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP) {
                if (event.rawX >= (etPassword.right - etPassword.compoundDrawables[2].bounds.width())) {
                    togglePasswordVisibility(etPassword)
                    return@setOnTouchListener true
                }
            }
            false
        }
        
        // Setup Sign Up navigation
        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)
        tvSignUp.setOnClickListener {
            navigateToLoginPage()
        }
        
        // Setup Forgot Password navigation
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        if (tvForgotPassword != null) {
            Log.d("SignPage", "Forgot Password button found")
            tvForgotPassword.setOnClickListener {
                Log.d("SignPage", "Forgot Password button clicked")
                Toast.makeText(this, "Navigating to Forgot Password...", Toast.LENGTH_SHORT).show()
                navigateToForgotPassword()
            }
        } else {
            Log.e("SignPage", "Forgot Password button not found!")
            Toast.makeText(this, "Forgot Password button not found!", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun togglePasswordVisibility(editText: EditText) {
        if (editText.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            // Show password
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off, 0)
        } else {
            // Hide password
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0)
        }
    }
    
    private fun navigateToLoginPage() {
        val intent = Intent(this, login_page::class.java)
        startActivity(intent)
    }
    
    private fun navigateToForgotPassword() {
        try {
            Log.d("SignPage", "Creating intent to forgotpassword")
            val intent = Intent(this, forgotpassword::class.java)
            Log.d("SignPage", "Starting forgotpassword activity")
            startActivity(intent)
            Log.d("SignPage", "Forgot password activity started successfully")
        } catch (e: Exception) {
            Log.e("SignPage", "Error navigating to forgot password: ${e.message}")
            Toast.makeText(this, "Navigation error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}