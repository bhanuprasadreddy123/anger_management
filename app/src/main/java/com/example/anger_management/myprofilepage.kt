package com.example.anger_management

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.ByteArrayOutputStream
import java.io.InputStream

class myprofilepage : AppCompatActivity() {
    
    companion object {
        private const val CAMERA_REQUEST_CODE = 100
        private const val GALLERY_REQUEST_CODE = 101
        private const val CAMERA_PERMISSION_REQUEST_CODE = 102
        private const val STORAGE_PERMISSION_REQUEST_CODE = 103
    }
    
    // Profile Views
    private lateinit var textName: TextView
    private lateinit var btnEditName: LinearLayout
    private lateinit var profilePicture: ImageView
    
    // Personal Information Views
    private lateinit var btnEditEmail: LinearLayout
    private lateinit var btnEditPhone: LinearLayout
    private lateinit var btnEditDOB: LinearLayout
    private lateinit var btnEditPronouns: LinearLayout
    
    // Text Views for displaying current values
    private lateinit var textEmail: TextView
    private lateinit var textPhone: TextView
    private lateinit var textDOB: TextView
    private lateinit var textPronouns: TextView
    private lateinit var textPreferredTime: TextView
    
    // Daily Preferences Views
    private lateinit var btnEditPreferredTime: LinearLayout
    private lateinit var switchReminders: Switch
    
    // Settings Views
    private lateinit var btnManageAccount: LinearLayout
    private lateinit var btnLogout: LinearLayout
    private lateinit var btnSettings: LinearLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_myprofilepage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setupClickListeners()
        loadUserData()
    }
    
    private fun initializeViews() {
        textName = findViewById(R.id.text_name)
        btnEditName = findViewById(R.id.btn_edit_name)
        profilePicture = findViewById(R.id.profile_picture)
        
        // Personal Information Views
        btnEditEmail = findViewById(R.id.btn_edit_email)
        btnEditPhone = findViewById(R.id.btn_edit_phone)
        btnEditDOB = findViewById(R.id.btn_edit_dob)
        btnEditPronouns = findViewById(R.id.btn_edit_pronouns)
        
        // Text Views for displaying values
        textEmail = findViewById(R.id.text_email)
        textPhone = findViewById(R.id.text_phone)
        textDOB = findViewById(R.id.text_dob)
        textPronouns = findViewById(R.id.text_pronouns)
        textPreferredTime = findViewById(R.id.text_preferred_time)
        
        // Daily Preferences
        btnEditPreferredTime = findViewById(R.id.btn_edit_preferred_time)
        switchReminders = findViewById(R.id.switch_reminders)
        
        // Settings
        btnManageAccount = findViewById(R.id.btn_manage_account)
        btnLogout = findViewById(R.id.btn_logout)
        btnSettings = findViewById(R.id.btn_settings)
    }
    
    private fun setupClickListeners() {
        // Back button
        val btnBack = findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }
        
        // Personal Information
        btnEditEmail.setOnClickListener {
            showEditEmailDialog()
        }
        
        btnEditPhone.setOnClickListener {
            showEditPhoneDialog()
        }
        
        btnEditDOB.setOnClickListener {
            showEditDOBDialog()
        }
        
        btnEditPronouns.setOnClickListener {
            showEditPronounsDialog()
        }
        
        btnEditName.setOnClickListener {
            showEditNameDialog()
        }
        
        // Daily Preferences
        btnEditPreferredTime.setOnClickListener {
            showEditPreferredTimeDialog()
        }
        
        switchReminders.setOnCheckedChangeListener { _, isChecked ->
            saveReminderPreference(isChecked)
            Toast.makeText(this, 
                if (isChecked) "Daily reminders enabled" else "Daily reminders disabled", 
                Toast.LENGTH_SHORT).show()
        }
        
        // Settings
        btnManageAccount.setOnClickListener {
            showManageAccountDialog()
        }
        
        btnLogout.setOnClickListener {
            showLogoutDialog()
        }
        
        btnSettings.setOnClickListener {
            navigateToSettings()
        }
    }
    
    private fun loadUserData() {
        val prefs = getSharedPreferences("user_profile", MODE_PRIVATE)
        
        // Load personal information - using same keys as AccountSettingsActivity
        textEmail.text = prefs.getString("user_email", "john.doe@example.com") ?: "john.doe@example.com"
        textPhone.text = prefs.getString("user_phone", "+1 (555) 123-4567") ?: "+1 (555) 123-4567"
        textDOB.text = prefs.getString("dob", "March 15, 1990") ?: "March 15, 1990"
        textPronouns.text = prefs.getString("pronouns", "She/Her") ?: "She/Her"
        textPreferredTime.text = prefs.getString("preferred_time", "Morning (8:00 AM)") ?: "Morning (8:00 AM)"
        textName.text = prefs.getString("user_name", "John Doe") ?: "John Doe"
        
        // Load preferences
        switchReminders.isChecked = prefs.getBoolean("daily_reminders", true)
    }
    
    private fun saveUserData(key: String, value: String) {
        getSharedPreferences("user_profile", MODE_PRIVATE)
            .edit()
            .putString(key, value)
            .apply()
    }
    
    private fun saveReminderPreference(enabled: Boolean) {
        getSharedPreferences("user_profile", MODE_PRIVATE)
            .edit()
            .putBoolean("daily_reminders", enabled)
            .apply()
    }
    
    // Personal Information Dialogs with actual editing
    private fun showEditEmailDialog() {
        val input = EditText(this)
        input.setText(textEmail.text)
        input.hint = "Enter email address"
        
        AlertDialog.Builder(this)
            .setTitle("Edit Email Address")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newEmail = input.text.toString().trim()
                if (newEmail.isNotEmpty()) {
                    textEmail.text = newEmail
                    saveUserData("user_email", newEmail)
                    Toast.makeText(this, "Email updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showEditPhoneDialog() {
        val input = EditText(this)
        input.setText(textPhone.text)
        input.hint = "Enter phone number"
        
        AlertDialog.Builder(this)
            .setTitle("Edit Phone Number")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newPhone = input.text.toString().trim()
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
    }
    
    private fun showEditDOBDialog() {
        val input = EditText(this)
        input.setText(textDOB.text)
        input.hint = "Enter date of birth (e.g., March 15, 1990)"
        
        AlertDialog.Builder(this)
            .setTitle("Edit Date of Birth")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newDOB = input.text.toString().trim()
                if (newDOB.isNotEmpty()) {
                    textDOB.text = newDOB
                    saveUserData("dob", newDOB)
                    Toast.makeText(this, "Date of birth updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Date of birth cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showEditPronounsDialog() {
        val pronouns = arrayOf("She/Her", "He/Him", "They/Them", "She/They", "He/They", "Other")
        val currentIndex = pronouns.indexOf(textPronouns.text.toString())
        
        AlertDialog.Builder(this)
            .setTitle("Select Pronouns")
            .setSingleChoiceItems(pronouns, if (currentIndex >= 0) currentIndex else 0) { _, which ->
                val selectedPronouns = pronouns[which]
                textPronouns.text = selectedPronouns
                saveUserData("pronouns", selectedPronouns)
                Toast.makeText(this, "Pronouns updated to: $selectedPronouns", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditNameDialog() {
        val input = EditText(this)
        input.setText(textName.text)
        input.hint = "Enter your name"

        AlertDialog.Builder(this)
            .setTitle("Edit Name")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newName = input.text.toString().trim()
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
    }
    
    // Daily Preferences Dialogs
    private fun showEditPreferredTimeDialog() {
        val times = arrayOf("Morning (6:00 AM)", "Morning (8:00 AM)", "Afternoon (12:00 PM)", "Evening (6:00 PM)", "Night (9:00 PM)")
        val currentIndex = times.indexOf(textPreferredTime.text.toString())
        
        AlertDialog.Builder(this)
            .setTitle("Select Preferred Time")
            .setSingleChoiceItems(times, if (currentIndex >= 0) currentIndex else 1) { _, which ->
                val selectedTime = times[which]
                textPreferredTime.text = selectedTime
                saveUserData("preferred_time", selectedTime)
                Toast.makeText(this, "Preferred time updated to: $selectedTime", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    // Settings Dialogs
    private fun showManageAccountDialog() {
        val options = arrayOf("Change Password", "Update Profile Picture", "Account Security", "Delete Account")
        
        AlertDialog.Builder(this)
            .setTitle("Manage Account")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showChangePasswordDialog()
                    1 -> showUpdateProfilePictureDialog()
                    2 -> showAccountSecurityDialog()
                    3 -> showDeleteAccountDialog()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showChangePasswordDialog() {
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
                val currentPassword = currentPasswordInput.text.toString()
                val newPassword = newPasswordInput.text.toString()
                val confirmPassword = confirmPasswordInput.text.toString()
                
                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                } else if (newPassword != confirmPassword) {
                    Toast.makeText(this, "New passwords don't match", Toast.LENGTH_SHORT).show()
                } else if (newPassword.length < 6) {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                } else {
                    // Save new password (in real app, this would be encrypted)
                    saveUserData("password", newPassword)
                    Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showUpdateProfilePictureDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery", "Remove Current Photo")
        
        AlertDialog.Builder(this)
            .setTitle("Update Profile Picture")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        if (checkCameraPermission()) {
                            openCamera()
                        } else {
                            requestCameraPermission()
                        }
                    }
                    1 -> {
                        if (checkStoragePermission()) {
                            openGallery()
                        } else {
                            requestStoragePermission()
                        }
                    }
                    2 -> {
                        // Remove current photo
                        removeProfilePicture()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
    
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
    }
    
    private fun checkStoragePermission(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            // For Android 13+ (API 33+), we don't need READ_EXTERNAL_STORAGE for gallery access
            true
        } else {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    private fun requestStoragePermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            // For Android 13+, directly open gallery
            openGallery()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_REQUEST_CODE)
        }
    }
    
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } else {
            Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun openGallery() {
        try {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, GALLERY_REQUEST_CODE)
            } else {
                Toast.makeText(this, "No gallery app found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening gallery: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun removeProfilePicture() {
        AlertDialog.Builder(this)
            .setTitle("Remove Profile Picture")
            .setMessage("Are you sure you want to remove your profile picture?")
            .setPositiveButton("Remove") { _, _ ->
                profilePicture.setImageResource(R.drawable.profile_placeholder)
                saveUserData("profile_picture", "")
                Toast.makeText(this, "Profile picture removed", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showAccountSecurityDialog() {
        val securityOptions = arrayOf("Two-Factor Authentication", "Login History", "Active Sessions", "Security Questions")
        
        AlertDialog.Builder(this)
            .setTitle("Account Security")
            .setItems(securityOptions) { _, which ->
                when (which) {
                    0 -> {
                        AlertDialog.Builder(this)
                            .setTitle("Two-Factor Authentication")
                            .setMessage("Two-factor authentication adds an extra layer of security to your account by requiring a verification code in addition to your password.")
                            .setPositiveButton("Enable") { _, _ ->
                                Toast.makeText(this, "2FA setup would begin here", Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton("Cancel", null)
                            .show()
                    }
                    1 -> {
                        AlertDialog.Builder(this)
                            .setTitle("Login History")
                            .setMessage("Recent login activity:\n\n" +
                                    "• Today, 2:30 PM - This device\n" +
                                    "• Yesterday, 10:15 AM - Mobile device\n" +
                                    "• 3 days ago, 8:45 PM - Desktop")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                    2 -> {
                        AlertDialog.Builder(this)
                            .setTitle("Active Sessions")
                            .setMessage("Currently active sessions:\n\n" +
                                    "• This device (Current)\n" +
                                    "• Mobile device - Last active: 2 hours ago")
                            .setPositiveButton("Sign Out All") { _, _ ->
                                Toast.makeText(this, "All sessions signed out", Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton("Cancel", null)
                            .show()
                    }
                    3 -> {
                        AlertDialog.Builder(this)
                            .setTitle("Security Questions")
                            .setMessage("Set up security questions to help recover your account if you forget your password.")
                            .setPositiveButton("Set Up") { _, _ ->
                                Toast.makeText(this, "Security questions setup would begin here", Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton("Cancel", null)
                            .show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    

    
    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                // Clear user data and navigate to login
                getSharedPreferences("user_profile", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply()
                
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                // Navigate to login page
                val intent = Intent(this, login_page::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showDeleteAccountDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Account")
            .setMessage("⚠️ WARNING: This action cannot be undone!\n\n" +
                    "Deleting your account will:\n" +
                    "• Permanently remove all your data\n" +
                    "• Delete your profile and settings\n" +
                    "• Remove all session history")
            .setPositiveButton("Delete") { _, _ ->
                // Clear all user data
                getSharedPreferences("user_profile", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply()
                
                Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                // Navigate to login page
                val intent = Intent(this, login_page::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    data?.let { intent ->
                        val bitmap = intent.extras?.get("data") as? Bitmap
                        bitmap?.let {
                            profilePicture.setImageBitmap(it)
                            saveProfilePictureToPreferences(it)
                            Toast.makeText(this, "Profile picture updated from camera", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                GALLERY_REQUEST_CODE -> {
                    data?.let { intent ->
                        val imageUri = intent.data
                        if (imageUri != null) {
                            try {
                                val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
                                if (inputStream != null) {
                                    val bitmap = BitmapFactory.decodeStream(inputStream)
                                    if (bitmap != null) {
                                        profilePicture.setImageBitmap(bitmap)
                                        saveProfilePictureToPreferences(bitmap)
                                        Toast.makeText(this, "Profile picture updated from gallery", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this, "Failed to decode image", Toast.LENGTH_SHORT).show()
                                    }
                                    inputStream.close()
                                } else {
                                    Toast.makeText(this, "Failed to open image stream", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(this, "Error loading image: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
                        }
                    } ?: run {
                        Toast.makeText(this, "No data received from gallery", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this, "Camera permission is required to take a photo", Toast.LENGTH_SHORT).show()
                }
            }
            STORAGE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Toast.makeText(this, "Storage permission is required to select an image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun saveProfilePictureToPreferences(bitmap: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val base64String = android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
        saveUserData("profile_picture", base64String)
    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }
}