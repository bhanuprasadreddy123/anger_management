package com.example.anger_management

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.switchmaterial.SwitchMaterial

class PrivacySecurityActivity : BaseActivity() {
    
    private lateinit var btnBack: ImageButton
    
    // Privacy Settings
    private lateinit var btnDataUsageInfo: LinearLayout
    private lateinit var btnActivityTracking: LinearLayout
    private lateinit var btnDataExport: LinearLayout
    
    // Data Collection Switches
    private lateinit var switchAnalytics: SwitchMaterial
    private lateinit var switchLocationServices: SwitchMaterial
    private lateinit var switchPersonalizedContent: SwitchMaterial
    
    // Security Settings
    private lateinit var btnBiometricAuth: LinearLayout
    private lateinit var btnTwoFactorAuth: LinearLayout
    private lateinit var switchAppLock: SwitchMaterial
    
    // Data Management
    private lateinit var btnClearData: LinearLayout
    private lateinit var btnDeleteAccount: LinearLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_privacy_security)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setupClickListeners()
        setupSwitches()
        loadSavedSettings()
    }
    
    private fun initializeViews() {
        btnBack = findViewById(R.id.btnBack)
        
        // Privacy Settings
        btnDataUsageInfo = findViewById(R.id.btnDataUsageInfo)
        btnActivityTracking = findViewById(R.id.btnActivityTracking)
        btnDataExport = findViewById(R.id.btnDataExport)
        
        // Data Collection Switches
        switchAnalytics = findViewById(R.id.switchAnalytics)
        switchLocationServices = findViewById(R.id.switchLocationServices)
        switchPersonalizedContent = findViewById(R.id.switchPersonalizedContent)
        
        // Security Settings
        btnBiometricAuth = findViewById(R.id.btnBiometricAuth)
        btnTwoFactorAuth = findViewById(R.id.btnTwoFactorAuth)
        switchAppLock = findViewById(R.id.switchAppLock)
        
        // Data Management
        btnClearData = findViewById(R.id.btnClearData)
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnDataUsageInfo.setOnClickListener {
            showDataUsageInfoDialog()
        }
        
        btnActivityTracking.setOnClickListener {
            showActivityTrackingDialog()
        }
        
        btnDataExport.setOnClickListener {
            showDataExportDialog()
        }
        
        btnBiometricAuth.setOnClickListener {
            showBiometricAuthDialog()
        }
        
        btnTwoFactorAuth.setOnClickListener {
            showTwoFactorAuthDialog()
        }
        
        btnClearData.setOnClickListener {
            showClearDataDialog()
        }
        
        btnDeleteAccount.setOnClickListener {
            showDeleteAccountDialog()
        }
    }
    
    private fun setupSwitches() {
        switchAnalytics.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("analytics_enabled", isChecked).apply()
        }
        
        switchLocationServices.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("location_services", isChecked).apply()
        }
        
        switchPersonalizedContent.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("personalized_content", isChecked).apply()
        }
        
        switchAppLock.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("app_lock_enabled", isChecked).apply()
        }
    }
    
    private fun loadSavedSettings() {
        // Load switch states
        switchAnalytics.isChecked = sharedPreferences.getBoolean("analytics_enabled", true)
        switchLocationServices.isChecked = sharedPreferences.getBoolean("location_services", false)
        switchPersonalizedContent.isChecked = sharedPreferences.getBoolean("personalized_content", true)
        switchAppLock.isChecked = sharedPreferences.getBoolean("app_lock_enabled", false)
    }
    
    private fun showDataUsageInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle("Data Usage Information")
            .setMessage("How we use your data:\n\n" +
                    "• App functionality: Essential data for app operation\n" +
                    "• Analytics: Anonymous usage statistics to improve the app\n" +
                    "• Personalization: Customized content and recommendations\n" +
                    "• Support: Technical information to help with issues\n\n" +
                    "Data storage:\n" +
                    "• Local storage: On your device\n" +
                    "• Cloud backup: Optional, encrypted\n" +
                    "• Third-party services: Minimal, for essential features")
            .setPositiveButton("View Details") { _, _ ->
                Toast.makeText(this, "Opening detailed data usage info...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Close", null)
            .show()
    }
    
    private fun showActivityTrackingDialog() {
        AlertDialog.Builder(this)
            .setTitle("Activity Tracking")
            .setMessage("Activity tracking helps provide personalized insights:\n\n" +
                    "What we track:\n" +
                    "• Session duration and frequency\n" +
                    "• Feature usage patterns\n" +
                    "• Progress and achievements\n" +
                    "• App preferences\n\n" +
                    "What we don't track:\n" +
                    "• Personal journal entries\n" +
                    "• Specific mood details\n" +
                    "• Sensitive personal information")
            .setPositiveButton("Configure") { _, _ ->
                Toast.makeText(this, "Opening activity tracking settings...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showDataExportDialog() {
        AlertDialog.Builder(this)
            .setTitle("Export Data")
            .setMessage("Export your data in various formats:\n\n" +
                    "Available exports:\n" +
                    "• Complete data backup (JSON)\n" +
                    "• Mood history (CSV)\n" +
                    "• Session records (PDF)\n" +
                    "• Journal entries (TXT)\n" +
                    "• Settings and preferences\n\n" +
                    "Export options:\n" +
                    "• Email to yourself\n" +
                    "• Save to device\n" +
                    "• Cloud storage")
            .setPositiveButton("Export") { _, _ ->
                Toast.makeText(this, "Starting data export...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showBiometricAuthDialog() {
        AlertDialog.Builder(this)
            .setTitle("Biometric Authentication")
            .setMessage("Use fingerprint or face recognition to secure your app:\n\n" +
                    "Benefits:\n" +
                    "• Quick and secure access\n" +
                    "• No need to remember passwords\n" +
                    "• Enhanced privacy protection\n\n" +
                    "Setup process:\n" +
                    "• Verify device biometrics\n" +
                    "• Set up app authentication\n" +
                    "• Configure fallback options")
            .setPositiveButton("Setup") { _, _ ->
                Toast.makeText(this, "Opening biometric setup...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showTwoFactorAuthDialog() {
        AlertDialog.Builder(this)
            .setTitle("Two-Factor Authentication")
            .setMessage("Add an extra layer of security to your account:\n\n" +
                    "How it works:\n" +
                    "• SMS verification codes\n" +
                    "• Authenticator app (Google, Authy)\n" +
                    "• Backup codes for emergencies\n\n" +
                    "Security benefits:\n" +
                    "• Protection against unauthorized access\n" +
                    "• Account recovery options\n" +
                    "• Enhanced privacy")
            .setPositiveButton("Enable") { _, _ ->
                Toast.makeText(this, "Opening 2FA setup...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showClearDataDialog() {
        AlertDialog.Builder(this)
            .setTitle("Clear Data")
            .setMessage("⚠️ This will permanently delete selected data:\n\n" +
                    "Data to clear:\n" +
                    "• Session history\n" +
                    "• Mood tracking data\n" +
                    "• Journal entries\n" +
                    "• App preferences\n" +
                    "• Cached files\n\n" +
                    "This action cannot be undone.")
            .setPositiveButton("Clear Data") { _, _ ->
                showClearDataConfirmation()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showClearDataConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Final Confirmation")
            .setMessage("Are you absolutely sure you want to clear all data?\n\n" +
                    "Type 'CLEAR' to confirm:")
            .setPositiveButton("Clear") { _, _ ->
                Toast.makeText(this, "Data cleared successfully", Toast.LENGTH_SHORT).show()
                // Here you would implement the actual data clearing logic
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
                    "• Cancel any active subscriptions\n" +
                    "• Remove all session history\n\n" +
                    "Before deleting, consider:\n" +
                    "• Exporting your data\n" +
                    "• Contacting support\n" +
                    "• Taking a break instead")
            .setPositiveButton("Delete Account") { _, _ ->
                showFinalDeleteConfirmation()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showFinalDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Final Confirmation")
            .setMessage("Are you absolutely sure you want to delete your account?\n\n" +
                    "Type 'DELETE' to confirm:")
            .setPositiveButton("Delete") { _, _ ->
                Toast.makeText(this, "Account deletion initiated", Toast.LENGTH_SHORT).show()
                // Here you would implement the actual account deletion logic
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
} 