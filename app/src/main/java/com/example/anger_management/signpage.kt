package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class signpage : AppCompatActivity() {
    
    private lateinit var etPassword: EditText
    private lateinit var btnSignIn: Button
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signpage)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setupClickListeners()
        
        Log.d("SignPage", "Sign page activity created successfully")
    }
    
    private fun initializeViews() {
        etPassword = findViewById(R.id.etPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        
        // Ensure password is hidden by default
        etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        
        // Set up focus change listener for debugging
        etPassword.setOnFocusChangeListener { view, hasFocus ->
            Log.d("SignPage", "Password field focus changed: $hasFocus")
        }
        
        // Set up text change listener to handle eye icon click
        etPassword.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                Log.d("SignPage", "Password text changed: ${s?.length} characters")
            }
        })
        
        Log.d("SignPage", "Views initialized")
    }
    
    private fun setupClickListeners() {
        // Add click listener to password field to ensure it's working
        etPassword.setOnClickListener {
            Log.d("SignPage", "Password field clicked")
            etPassword.requestFocus()
        }
        
        // Add touch listener to handle eye icon click
        etPassword.setOnTouchListener { view, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP) {
                val drawableRight = etPassword.compoundDrawables[2] // Right drawable
                if (drawableRight != null) {
                    val touchX = event.x
                    val rightEdge = etPassword.width - etPassword.paddingRight - drawableRight.intrinsicWidth - 16
                    if (touchX >= rightEdge) {
                        Log.d("SignPage", "Eye icon area clicked")
                        togglePasswordVisibility()
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
        
        btnSignIn.setOnClickListener {
            Log.d("SignPage", "Sign In button clicked")
            Log.d("SignPage", "Button text: ${btnSignIn.text}")
            Log.d("SignPage", "Button is enabled: ${btnSignIn.isEnabled}")
            Log.d("SignPage", "Button is clickable: ${btnSignIn.isClickable}")
            Toast.makeText(this, "Sign In button clicked!", Toast.LENGTH_SHORT).show()
            
            // Navigate to home page instead of personal page
            try {
                val intent = Intent(this, home_page::class.java)
                startActivity(intent)
                finish() // Close this activity
            } catch (e: Exception) {
                Log.e("SignPage", "Navigation failed: ${e.message}", e)
                Toast.makeText(this, "Navigation failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
        
        Log.d("SignPage", "Click listeners set up")
    }
    
    private fun togglePasswordVisibility() {
        Log.d("SignPage", "togglePasswordVisibility called, current state: $isPasswordVisible")
        
        if (isPasswordVisible) {
            // Hide password
            etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, 
                resources.getDrawable(R.drawable.ic_eye, null), null)
            Log.d("SignPage", "Password hidden - inputType changed")
        } else {
            // Show password
            etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, 
                resources.getDrawable(R.drawable.ic_eye_off, null), null)
            Log.d("SignPage", "Password visible - inputType changed")
        }
        isPasswordVisible = !isPasswordVisible
        Log.d("SignPage", "Password visibility toggled to: $isPasswordVisible")
    }
    

}