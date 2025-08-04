package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class forgotpassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgotpassword)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        setupViews()
    }
    
    private fun setupViews() {
        // Setup Send Reset Link button
        val btnSendResetLink = findViewById<Button>(R.id.btn_send_reset_link)
        if (btnSendResetLink != null) {
            Log.d("ForgotPassword", "Send Reset Link button found")
            btnSendResetLink.setOnClickListener {
                Log.d("ForgotPassword", "Send Reset Link button clicked")
                handleSendResetLink()
            }
        } else {
            Log.e("ForgotPassword", "Send Reset Link button not found!")
            Toast.makeText(this, "Send Reset Link button not found!", Toast.LENGTH_LONG).show()
        }
        
        // Setup Sign In navigation
        val btnSignIn = findViewById<TextView>(R.id.btn_sign_in)
        if (btnSignIn != null) {
            Log.d("ForgotPassword", "Sign In button found")
            btnSignIn.setOnClickListener {
                Log.d("ForgotPassword", "Sign In button clicked")
                navigateToSignPage()
            }
        } else {
            Log.e("ForgotPassword", "Sign In button not found!")
        }
    }
    
    private fun handleSendResetLink() {
        val emailInput = findViewById<TextInputEditText>(R.id.et_email)
        val email = emailInput?.text?.toString()?.trim()
        
        if (email.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Generate OTP
        val otp = generateOTP()
        
        // Show loading message
        Toast.makeText(this, "Sending OTP to your email...", Toast.LENGTH_SHORT).show()
        
        // Send OTP via email
        val emailService = EmailService()
        emailService.sendOTPEmail(email, otp, object : EmailService.EmailCallback {
                    override fun onSuccess() {
            runOnUiThread {
                Toast.makeText(this@forgotpassword, "OTP sent to your email: $email", Toast.LENGTH_LONG).show()
                Log.d("ForgotPassword", "Generated OTP: $otp for email: $email")
                // Navigate directly to OTP verification
                navigateToVerifyOTP(email, otp)
            }
        }
            
                    override fun onFailure(error: String) {
            runOnUiThread {
                Toast.makeText(this@forgotpassword, "Failed to send email: $error", Toast.LENGTH_LONG).show()
                Log.e("ForgotPassword", "Email sending failed: $error")
            }
        }
        })
    }
    
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    private fun generateOTP(): String {
        // Generate a 6-digit OTP
        return (100000..999999).random().toString()
    }
    
    private fun navigateToVerifyOTP(email: String, otp: String) {
        try {
            Log.d("ForgotPassword", "Creating intent to verifyotp with email: $email")
            val intent = Intent(this, verifyotp::class.java)
            intent.putExtra("email", email)
            intent.putExtra("otp", otp)
            Log.d("ForgotPassword", "Starting verifyotp activity")
            startActivity(intent)
            Log.d("ForgotPassword", "Verify OTP activity started successfully")
        } catch (e: Exception) {
            Log.e("ForgotPassword", "Error navigating to verify OTP: ${e.message}")
            Toast.makeText(this, "Navigation error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun navigateToSignPage() {
        try {
            Log.d("ForgotPassword", "Creating intent to sign_page")
            val intent = Intent(this, sign_page::class.java)
            Log.d("ForgotPassword", "Starting sign_page activity")
            startActivity(intent)
            Log.d("ForgotPassword", "Sign page activity started successfully")
        } catch (e: Exception) {
            Log.e("ForgotPassword", "Error navigating to sign page: ${e.message}")
            Toast.makeText(this, "Navigation error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}