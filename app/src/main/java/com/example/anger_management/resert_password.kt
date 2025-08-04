package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class resert_password : AppCompatActivity() {
    
    private lateinit var etEmail: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnTogglePassword1: ImageButton
    private lateinit var btnTogglePassword2: ImageButton
    private lateinit var btnResetPassword: Button
    private lateinit var btnBack: ImageButton
    private lateinit var tvBackToLogin: TextView
    
    private var isPassword1Visible = false
    private var isPassword2Visible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reset_password)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setupClickListeners()
        
        Log.d("ResetPassword", "Reset Password Activity created successfully")
    }
    
    private fun initializeViews() {
        etEmail = findViewById(R.id.etEmail)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnTogglePassword1 = findViewById(R.id.btnTogglePassword1)
        btnTogglePassword2 = findViewById(R.id.btnTogglePassword2)
        btnResetPassword = findViewById(R.id.btnResetPassword)
        btnBack = findViewById(R.id.btnBack)
        tvBackToLogin = findViewById(R.id.tvBackToLogin)
        
        Log.d("ResetPassword", "Views initialized")
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            Log.d("ResetPassword", "Back button clicked")
            finish()
        }
        
        btnTogglePassword1.setOnClickListener {
            Log.d("ResetPassword", "Toggle password 1 clicked")
            togglePasswordVisibility(etNewPassword, btnTogglePassword1, isPassword1Visible)
            isPassword1Visible = !isPassword1Visible
        }
        
        btnTogglePassword2.setOnClickListener {
            Log.d("ResetPassword", "Toggle password 2 clicked")
            togglePasswordVisibility(etConfirmPassword, btnTogglePassword2, isPassword2Visible)
            isPassword2Visible = !isPassword2Visible
        }
        
        btnResetPassword.setOnClickListener {
            Log.d("ResetPassword", "Reset password button clicked")
            resetPassword()
        }
        
        tvBackToLogin.setOnClickListener {
            Log.d("ResetPassword", "Back to login clicked")
            try {
                // Navigate to sign-in page
                val intent = Intent(this, signpage::class.java)
                Log.d("ResetPassword", "Intent created: ${intent.component?.className}")
                startActivity(intent)
                Log.d("ResetPassword", "Navigation to signpage successful")
                finish() // Close this activity
            } catch (e: Exception) {
                Log.e("ResetPassword", "Navigation failed: ${e.message}", e)
                Toast.makeText(this, "Navigation failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
        
        Log.d("ResetPassword", "Click listeners set up")
    }
    
    private fun togglePasswordVisibility(editText: EditText, button: ImageButton, isVisible: Boolean) {
        if (isVisible) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            button.setImageResource(R.drawable.ic_eye)
            Log.d("ResetPassword", "Password hidden")
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            button.setImageResource(R.drawable.ic_eye_off)
            Log.d("ResetPassword", "Password visible")
        }
    }
    
    private fun resetPassword() {
        val email = etEmail.text.toString().trim()
        val newPassword = etNewPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()
        
        Log.d("ResetPassword", "Validating password reset - Email: $email")
        
        // Validation
        if (email.isEmpty()) {
            etEmail.error = "Please enter your email"
            Log.d("ResetPassword", "Email validation failed - empty")
            return
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Please enter a valid email address"
            Log.d("ResetPassword", "Email validation failed - invalid format")
            return
        }
        
        if (newPassword.isEmpty()) {
            etNewPassword.error = "Please enter a new password"
            Log.d("ResetPassword", "New password validation failed - empty")
            return
        }
        
        if (newPassword.length < 6) {
            etNewPassword.error = "Password must be at least 6 characters"
            Log.d("ResetPassword", "New password validation failed - too short")
            return
        }
        
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.error = "Please confirm your password"
            Log.d("ResetPassword", "Confirm password validation failed - empty")
            return
        }
        
        if (newPassword != confirmPassword) {
            etConfirmPassword.error = "Passwords do not match"
            Log.d("ResetPassword", "Password validation failed - passwords don't match")
            return
        }
        
        // Here you would typically make an API call to reset the password
        // For now, we'll just show a success message
        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show()
        Log.d("ResetPassword", "Password reset successful")
        
        // Clear the fields
        etEmail.text.clear()
        etNewPassword.text.clear()
        etConfirmPassword.text.clear()
        
        // Navigate to sign-in page
        try {
            Log.d("ResetPassword", "Attempting to navigate to signpage")
            val intent = Intent(this, signpage::class.java)
            Log.d("ResetPassword", "Intent created: ${intent.component?.className}")
            startActivity(intent)
            Log.d("ResetPassword", "Navigation to signpage successful")
            finish() // Close this activity
        } catch (e: Exception) {
            Log.e("ResetPassword", "Navigation failed: ${e.message}", e)
            Toast.makeText(this, "Navigation failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}