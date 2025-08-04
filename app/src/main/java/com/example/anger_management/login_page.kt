package com.example.anger_management

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class login_page : AppCompatActivity() {
    
    private lateinit var etPassword: EditText
    private lateinit var ivEyeToggle: ImageView
    private lateinit var tvGender: TextView
    private lateinit var rlGender: RelativeLayout
    private lateinit var btnCreateAccount: Button
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setupClickListeners()
        
        Log.d("LoginPage", "Login page activity created successfully")
    }
    
    private fun initializeViews() {
        etPassword = findViewById(R.id.etPassword)
        ivEyeToggle = findViewById(R.id.ivEyeToggle)
        tvGender = findViewById(R.id.tvGender)
        rlGender = findViewById(R.id.rlGender)
        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        
        // Ensure password is hidden by default
        etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        
        Log.d("LoginPage", "Views initialized")
    }
    
    private fun setupClickListeners() {
        ivEyeToggle.setOnClickListener {
            Log.d("LoginPage", "Eye toggle clicked")
            togglePasswordVisibility()
        }
        
        rlGender.setOnClickListener {
            Log.d("LoginPage", "Gender field clicked")
            showGenderDialog()
        }
        
        btnCreateAccount.setOnClickListener {
            Log.d("LoginPage", "Create Account button clicked")
            navigateToSignPage()
        }
        
        Log.d("LoginPage", "Click listeners set up")
    }
    
    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide password
            etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            ivEyeToggle.setImageResource(R.drawable.ic_eye)
            Log.d("LoginPage", "Password hidden")
        } else {
            // Show password
            etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            ivEyeToggle.setImageResource(R.drawable.ic_eye_off)
            Log.d("LoginPage", "Password visible")
        }
        isPasswordVisible = !isPasswordVisible
    }
    
    private fun showGenderDialog() {
        val genders = arrayOf("Male", "Female", "Others")
        
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Gender")
            .setItems(genders) { dialog, which ->
                val selectedGender = genders[which]
                tvGender.text = selectedGender
                tvGender.setTextColor(resources.getColor(android.R.color.black, null))
                Log.d("LoginPage", "Gender selected: $selectedGender")
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        
        val dialog = builder.create()
        dialog.show()
    }
    
    private fun navigateToSignPage() {
        try {
            Log.d("LoginPage", "Attempting to navigate to signpage")
            val intent = Intent(this, signpage::class.java)
            Log.d("LoginPage", "Intent created: ${intent.component?.className}")
            startActivity(intent)
            Log.d("LoginPage", "Navigation to signpage successful")
            finish() // Close this activity
        } catch (e: Exception) {
            Log.e("LoginPage", "Navigation failed: ${e.message}", e)
        }
    }
}