package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Patterns
import android.widget.ImageButton

class AccountSettingsActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnManageProfile: LinearLayout
    private lateinit var btnChangeEmailPassword: LinearLayout
    private lateinit var btnDeleteAccount: LinearLayout
    
    // Profile Information UI elements
    private lateinit var btnEditName: LinearLayout
    private lateinit var btnEditEmail: LinearLayout
    private lateinit var btnEditPhone: LinearLayout
    private lateinit var textName: TextView
    private lateinit var textEmail: TextView
    private lateinit var textPhone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)
        
        Toast.makeText(this, "AccountSettingsActivity opened successfully!", Toast.LENGTH_LONG).show()
        
        try {
            initializeViews()
            setupClickListeners()
            loadUserData()
            Toast.makeText(this, "AccountSettingsActivity fully loaded", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error in AccountSettingsActivity: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initializeViews() {
        try {
            btnBack = findViewById(R.id.btnBack)
            btnManageProfile = findViewById(R.id.btnManageProfile)
            btnChangeEmailPassword = findViewById(R.id.btnChangeEmailPassword)
            btnDeleteAccount = findViewById(R.id.btnDeleteAccount)
            
            // Profile Information UI elements
            btnEditName = findViewById(R.id.btnEditName)
            btnEditEmail = findViewById(R.id.btnEditEmail)
            btnEditPhone = findViewById(R.id.btnEditPhone)
            textName = findViewById(R.id.textName)
            textEmail = findViewById(R.id.textEmail)
            textPhone = findViewById(R.id.textPhone)
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing views: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        btnManageProfile.setOnClickListener {
            val intent = Intent(this, myprofilepage::class.java)
            startActivity(intent)
        }

        btnChangeEmailPassword.setOnClickListener {
            showChangeEmailPasswordDialog()
        }

        btnDeleteAccount.setOnClickListener {
            showDeleteAccountDialog()
        }
        
        // Profile Information click listeners
        btnEditName.setOnClickListener {
            showEditNameDialog()
        }
        
        btnEditEmail.setOnClickListener {
            showEditEmailDialog()
        }
        
        btnEditPhone.setOnClickListener {
            showEditPhoneDialog()
        }
    }
    
    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("user_profile", MODE_PRIVATE)
        
        // Load saved user data
        val savedName = sharedPreferences.getString("user_name", "John Doe") ?: "John Doe"
        val savedEmail = sharedPreferences.getString("user_email", "john.doe@example.com") ?: "john.doe@example.com"
        val savedPhone = sharedPreferences.getString("user_phone", "+1 (555) 123-4567") ?: "+1 (555) 123-4567"
        
        // Update UI with saved data
        textName.text = savedName
        textEmail.text = savedEmail
        textPhone.text = savedPhone
    }
    
    private fun showEditNameDialog() {
        try {
            val editText = EditText(this)
            editText.setText(textName.text.toString())
            editText.hint = "Enter your name"
            
            AlertDialog.Builder(this)
                .setTitle("Edit Name")
                .setView(editText)
                .setPositiveButton("Save") { _, _ ->
                    val newName = editText.text.toString().trim()
                    if (newName.isNotEmpty()) {
                        textName.text = newName
                        saveUserData("user_name", newName)
                        Toast.makeText(this, "Name updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error showing dialog: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showEditEmailDialog() {
        try {
            val editText = EditText(this)
            editText.setText(textEmail.text.toString())
            editText.hint = "Enter your email"
            editText.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            
            AlertDialog.Builder(this)
                .setTitle("Edit Email")
                .setView(editText)
                .setPositiveButton("Save") { _, _ ->
                    val newEmail = editText.text.toString().trim()
                    if (newEmail.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                        textEmail.text = newEmail
                        saveUserData("user_email", newEmail)
                        Toast.makeText(this, "Email updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error showing dialog: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showEditPhoneDialog() {
        try {
            val editText = EditText(this)
            editText.setText(textPhone.text.toString())
            editText.hint = "Enter your phone number"
            editText.inputType = android.text.InputType.TYPE_CLASS_PHONE
            
            AlertDialog.Builder(this)
                .setTitle("Edit Phone Number")
                .setView(editText)
                .setPositiveButton("Save") { _, _ ->
                    val newPhone = editText.text.toString().trim()
                    if (newPhone.isNotEmpty()) {
                        textPhone.text = newPhone
                        saveUserData("user_phone", newPhone)
                        Toast.makeText(this, "Phone number updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Phone number cannot be empty", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error showing dialog: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun saveUserData(key: String, value: String) {
        val sharedPreferences = getSharedPreferences("user_profile", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun showChangeEmailPasswordDialog() {
        val options = arrayOf("Change Email Address", "Change Password")
        
        AlertDialog.Builder(this)
            .setTitle("Change Email / Password")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showChangeEmailDialog()
                    1 -> showChangePasswordDialog()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showChangeEmailDialog() {
        try {
            val currentEmailInput = EditText(this)
            currentEmailInput.hint = "Current Email"
            currentEmailInput.setText(textEmail.text.toString())
            currentEmailInput.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            
            val newEmailInput = EditText(this)
            newEmailInput.hint = "New Email Address"
            newEmailInput.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            
            val confirmEmailInput = EditText(this)
            confirmEmailInput.hint = "Confirm New Email"
            confirmEmailInput.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            
            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            layout.setPadding(50, 20, 50, 20)
            layout.addView(currentEmailInput)
            layout.addView(newEmailInput)
            layout.addView(confirmEmailInput)
            
            AlertDialog.Builder(this)
                .setTitle("Change Email Address")
                .setView(layout)
                .setPositiveButton("Change Email") { _, _ ->
                    val currentEmail = currentEmailInput.text.toString().trim()
                    val newEmail = newEmailInput.text.toString().trim()
                    val confirmEmail = confirmEmailInput.text.toString().trim()

                    if (currentEmail.isEmpty() || newEmail.isEmpty() || confirmEmail.isEmpty()) {
                        Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    if (currentEmail != textEmail.text.toString()) {
                        Toast.makeText(this, "Current email is incorrect", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                        Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    if (newEmail != confirmEmail) {
                        Toast.makeText(this, "New email and confirm email do not match", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    // Save new email
                    textEmail.text = newEmail
                    saveUserData("user_email", newEmail)
                    Toast.makeText(this, "Email changed successfully", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error showing email dialog: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showChangePasswordDialog() {
        try {
            val currentPasswordInput = EditText(this)
            currentPasswordInput.hint = "Current Password"
            currentPasswordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            
            val newPasswordInput = EditText(this)
            newPasswordInput.hint = "New Password"
            newPasswordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            
            val confirmPasswordInput = EditText(this)
            confirmPasswordInput.hint = "Confirm New Password"
            confirmPasswordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            
            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            layout.setPadding(50, 20, 50, 20)
            layout.addView(currentPasswordInput)
            layout.addView(newPasswordInput)
            layout.addView(confirmPasswordInput)
            
            AlertDialog.Builder(this)
                .setTitle("Change Password")
                .setView(layout)
                .setPositiveButton("Change Password") { _, _ ->
                    val currentPassword = currentPasswordInput.text.toString().trim()
                    val newPassword = newPasswordInput.text.toString().trim()
                    val confirmPassword = confirmPasswordInput.text.toString().trim()

                    if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                        Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    if (newPassword.length < 8) {
                        Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    if (newPassword != confirmPassword) {
                        Toast.makeText(this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    // Save new password
                    saveUserData("user_password", newPassword)
                    Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error showing password dialog: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteAccountDialog() {
        try {
            AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone and all your data will be permanently lost.")
                .setPositiveButton("Delete Account") { _, _ ->
                    showFinalDeleteConfirmation()
                }
                .setNegativeButton("Cancel", null)
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error showing delete dialog: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showFinalDeleteConfirmation() {
        try {
            val editText = EditText(this)
            editText.hint = "Type DELETE to confirm"

            AlertDialog.Builder(this)
                .setTitle("Final Confirmation")
                .setMessage("This action will permanently delete your account and all associated data. This cannot be undone.")
                .setView(editText)
                .setPositiveButton("Delete") { _, _ ->
                    val confirmation = editText.text.toString().trim()
                    if (confirmation == "DELETE") {
                        // Clear all user data
                        val sharedPreferences = getSharedPreferences("user_profile", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.clear()
                        editor.apply()

                        Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()

                        // Navigate to login page
                        val intent = Intent(this, login_page::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Please type DELETE to confirm", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error showing final confirmation: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
} 