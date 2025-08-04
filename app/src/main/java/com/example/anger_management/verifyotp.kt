package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class verifyotp : AppCompatActivity() {
    
    private lateinit var btnVerify: Button
    private lateinit var btnBack: Button
    private lateinit var btnBackArrow: ImageView
    private lateinit var tvCountdown: TextView
    private lateinit var otp1: EditText
    private lateinit var otp2: EditText
    private lateinit var otp3: EditText
    private lateinit var otp4: EditText
    private lateinit var otp5: EditText
    private lateinit var otp6: EditText
    
    private var countDownTimer: CountDownTimer? = null
    private val COUNTDOWN_TIME = 3 * 60 * 1000L // 3 minutes in milliseconds
    
    private var expectedOTP: String = ""
    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_verifyotp)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Get email and OTP from intent
        userEmail = intent.getStringExtra("email") ?: ""
        expectedOTP = intent.getStringExtra("otp") ?: ""
        
        Log.d("VerifyOTP", "Received email: $userEmail, Expected OTP: $expectedOTP")
        
        initializeViews()
        setupClickListeners()
        setupOTPNavigation()
        startCountdown()
        
        Log.d("VerifyOTP", "Activity created successfully")
    }
    
    private fun initializeViews() {
        btnVerify = findViewById(R.id.btnverify)
        btnBack = findViewById(R.id.iv_back)
        btnBackArrow = findViewById(R.id.btnBackArrow)
        tvCountdown = findViewById(R.id.tvCountdown)
        otp1 = findViewById(R.id.otp1)
        otp2 = findViewById(R.id.otp2)
        otp3 = findViewById(R.id.otp3)
        otp4 = findViewById(R.id.otp4)
        otp5 = findViewById(R.id.otp5)
        otp6 = findViewById(R.id.otp6)
        
        Log.d("VerifyOTP", "Views initialized")
    }
    
    private fun setupClickListeners() {
        btnVerify.setOnClickListener {
            Log.d("VerifyOTP", "Verify button clicked")
            verifyOTP()
        }
        
        btnBack.setOnClickListener {
            Log.d("VerifyOTP", "Back button clicked")
            finish()
        }
        
        btnBackArrow.setOnClickListener {
            Log.d("VerifyOTP", "Back arrow clicked")
            finish()
        }
        
        Log.d("VerifyOTP", "Click listeners set up")
    }
    
    private fun setupOTPNavigation() {
        // Set up text change listeners for automatic navigation
        setupOTPField(otp1, otp2)
        setupOTPField(otp2, otp3)
        setupOTPField(otp3, otp4)
        setupOTPField(otp4, otp5)
        setupOTPField(otp5, otp6)
        setupOTPField(otp6, null) // Last field doesn't need next field
        
        Log.d("VerifyOTP", "OTP navigation set up")
    }
    
    private fun setupOTPField(currentField: EditText, nextField: EditText?) {
        currentField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    // Move to next field if there is one
                    nextField?.requestFocus()
                    Log.d("VerifyOTP", "Moved to next OTP field")
                } else if (s?.isEmpty() == true) {
                    // If field is cleared, stay in current field
                    currentField.requestFocus()
                }
            }
        })
    }
    
    private fun startCountdown() {
        countDownTimer = object : CountDownTimer(COUNTDOWN_TIME, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val timeString = String.format("Code expires in: %02d:%02d", minutes, seconds)
                tvCountdown.text = timeString
                Log.d("VerifyOTP", "Countdown: $timeString")
            }
            
            override fun onFinish() {
                tvCountdown.text = "Code expired"
                tvCountdown.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
                Log.d("VerifyOTP", "Countdown finished - code expired")
                
                // Show toast message
                Toast.makeText(this@verifyotp, "OTP has expired. Please request a new code.", Toast.LENGTH_LONG).show()
                
                // Optionally disable verify button
                btnVerify.isEnabled = false
                btnVerify.alpha = 0.5f
            }
        }.start()
        
        Log.d("VerifyOTP", "Countdown timer started")
    }
    
    private fun stopCountdown() {
        countDownTimer?.cancel()
        countDownTimer = null
        Log.d("VerifyOTP", "Countdown timer stopped")
    }
    
    private fun verifyOTP() {
        Log.d("VerifyOTP", "verifyOTP() called")
        
        val otpCode = otp1.text.toString() + otp2.text.toString() + otp3.text.toString() + 
                     otp4.text.toString() + otp5.text.toString() + otp6.text.toString()
        
        Log.d("VerifyOTP", "OTP Code: $otpCode, Length: ${otpCode.length}")
        Log.d("VerifyOTP", "Expected OTP: $expectedOTP")
        
        // Check if all OTP fields are filled
        if (otpCode.length != 6) {
            Toast.makeText(this, "Please enter the complete 6-digit OTP", Toast.LENGTH_SHORT).show()
            Log.d("VerifyOTP", "OTP incomplete, showing error")
            return
        }
        
        // Validate OTP
        if (otpCode != expectedOTP) {
            Toast.makeText(this, "Invalid OTP. Please check and try again.", Toast.LENGTH_SHORT).show()
            Log.d("VerifyOTP", "OTP validation failed")
            return
        }
        
        // Stop the countdown timer
        stopCountdown()
        
        // OTP is valid, navigate to reset password
        try {
            Log.d("VerifyOTP", "OTP validated successfully, navigating to reset_password")
            
            // Navigate to Reset Password Activity
            val intent = Intent(this, resert_password::class.java)
            intent.putExtra("email", userEmail)
            Log.d("VerifyOTP", "Intent created: ${intent.component?.className}")
            
            startActivity(intent)
            
            Log.d("VerifyOTP", "Navigation successful")
            
            // Show success message
            Toast.makeText(this, "OTP verified successfully!", Toast.LENGTH_SHORT).show()
            
            // Close this activity after successful navigation
            finish()
            
        } catch (e: Exception) {
            Log.e("VerifyOTP", "Navigation failed: ${e.message}", e)
            Toast.makeText(this, "Navigation failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        stopCountdown()
        Log.d("VerifyOTP", "Activity destroyed, countdown stopped")
    }
}