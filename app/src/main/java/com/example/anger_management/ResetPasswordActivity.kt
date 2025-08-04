package com.example.anger_management

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResetPasswordActivity : AppCompatActivity() {
    
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
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnTogglePassword1.setOnClickListener {
            togglePasswordVisibility(etNewPassword, btnTogglePassword1, isPassword1Visible)
            isPassword1Visible = !isPassword1Visible
        }
        
        btnTogglePassword2.setOnClickListener {
            togglePasswordVisibility(etConfirmPassword, btnTogglePassword2, isPassword2Visible)
            isPassword2Visible = !isPassword2Visible
        }
        
        btnResetPassword.setOnClickListener {
            resetPassword()
        }
        
        tvBackToLogin.setOnClickListener {
            // Navigate back to login screen
            finish()
        }
    }
    
    private fun togglePasswordVisibility(editText: EditText, button: ImageButton, isVisible: Boolean) {
        if (isVisible) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            button.setImageResource(R.drawable.ic_eye)
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            button.setImageResource(R.drawable.ic_eye_off)
        }
    }
    
    private fun resetPassword() {
        val email = etEmail.text.toString().trim()
        val newPassword = etNewPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()
        
        // Validation
        if (email.isEmpty()) {
            etEmail.error = "Please enter your email"
            return
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Please enter a valid email address"
            return
        }
        
        if (newPassword.isEmpty()) {
            etNewPassword.error = "Please enter a new password"
            return
        }
        
        if (newPassword.length < 6) {
            etNewPassword.error = "Password must be at least 6 characters"
            return
        }
        
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.error = "Please confirm your password"
            return
        }
        
        if (newPassword != confirmPassword) {
            etConfirmPassword.error = "Passwords do not match"
            return
        }
        
        // Here you would typically make an API call to reset the password
        // For now, we'll just show a success message
        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show()
        
        // Clear the fields
        etEmail.text.clear()
        etNewPassword.text.clear()
        etConfirmPassword.text.clear()
        
        // Navigate back to login
        finish()
    }
} 