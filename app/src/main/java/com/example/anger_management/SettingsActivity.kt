package com.example.anger_management

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : BaseActivity() {
    
    private lateinit var btnBack: ImageButton
    private lateinit var btnLogOut: TextView
    
    // Account Section
    private lateinit var btnAccount: LinearLayout
    private lateinit var btnManageProfile: LinearLayout
    private lateinit var btnChangeEmailPassword: LinearLayout
    
    // AI Session Preferences
    private lateinit var btnSessionDuration: LinearLayout
    private lateinit var btnCalmingTechniques: LinearLayout
    
    // Appearance
    private lateinit var btnThemeMode: LinearLayout
    private lateinit var btnFontSize: LinearLayout
    
    // Privacy & Security
    private lateinit var btnDataUsageInfo: LinearLayout
    private lateinit var btnActivityTracking: LinearLayout
    
    // Help & Support
    private lateinit var btnFAQs: LinearLayout
    private lateinit var btnContactSupport: LinearLayout
    private lateinit var btnSendFeedback: LinearLayout
    
    // About
    private lateinit var btnDeveloperCredits: LinearLayout
    
    // Audio Settings
    private lateinit var seekBarVolume: SeekBar
    private lateinit var switchBackgroundMusic: SwitchMaterial
    private lateinit var switchDailyReminders: SwitchMaterial
    private lateinit var switchCalmDownAlerts: SwitchMaterial
    
    // Navigation Buttons
    private lateinit var btnManageNotifications: LinearLayout
    private lateinit var btnManageAudio: LinearLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setupClickListeners()
        setupSwitches()
        setupSeekBar()
        loadSavedSettings()
    }
    
    private fun initializeViews() {
        try {
            btnBack = findViewById(R.id.btnBack)
            btnLogOut = findViewById(R.id.btnLogOut)
            
            // Account Section
            btnAccount = findViewById(R.id.btnAccount)
            btnManageProfile = findViewById(R.id.btnManageProfile)
            btnChangeEmailPassword = findViewById(R.id.btnChangeEmailPassword)
            
            // AI Session Preferences
            btnSessionDuration = findViewById(R.id.btnSessionDuration)
            btnCalmingTechniques = findViewById(R.id.btnCalmingTechniques)
            
            // Appearance
            btnThemeMode = findViewById(R.id.btnThemeMode)
            btnFontSize = findViewById(R.id.btnFontSize)
            
            // Privacy & Security
            btnDataUsageInfo = findViewById(R.id.btnDataUsageInfo)
            btnActivityTracking = findViewById(R.id.btnActivityTracking)
            
            // Help & Support
            btnFAQs = findViewById(R.id.btnFAQs)
            btnContactSupport = findViewById(R.id.btnContactSupport)
            btnSendFeedback = findViewById(R.id.btnSendFeedback)
            
            // About
            btnDeveloperCredits = findViewById(R.id.btnDeveloperCredits)
            
            // Audio Settings
            seekBarVolume = findViewById(R.id.seekBarVolume)
            switchBackgroundMusic = findViewById(R.id.switchBackgroundMusic)
            switchDailyReminders = findViewById(R.id.switchDailyReminders)
            switchCalmDownAlerts = findViewById(R.id.switchCalmDownAlerts)
            
            // Navigation Buttons
            btnManageNotifications = findViewById(R.id.btnManageNotifications)
            btnManageAudio = findViewById(R.id.btnManageAudio)
            
            Toast.makeText(this, "All views initialized successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing views: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnLogOut.setOnClickListener {
            showLogoutDialog()
        }
        
        // Account Section - Navigate to Account Settings
        btnAccount.setOnClickListener {
            try {
                Toast.makeText(this, "Account button clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AccountSettingsActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Intent started", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error navigating to Account: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        
        btnManageProfile.setOnClickListener {
            val intent = Intent(this, AccountSettingsActivity::class.java)
            startActivity(intent)
        }
        
        btnChangeEmailPassword.setOnClickListener {
            val intent = Intent(this, AccountSettingsActivity::class.java)
            startActivity(intent)
        }
        
        // AI Session Preferences - Navigate to AI Session Preferences
        btnSessionDuration.setOnClickListener {
            val intent = Intent(this, AISessionPreferencesActivity::class.java)
            startActivity(intent)
        }
        
        btnCalmingTechniques.setOnClickListener {
            val intent = Intent(this, AISessionPreferencesActivity::class.java)
            startActivity(intent)
        }
        
        // Appearance - Navigate to Appearance Settings
        btnThemeMode.setOnClickListener {
            val intent = Intent(this, AppearanceSettingsActivity::class.java)
            startActivity(intent)
        }
        
        btnFontSize.setOnClickListener {
            val intent = Intent(this, AppearanceSettingsActivity::class.java)
            startActivity(intent)
        }
        
        // Privacy & Security - Navigate to Privacy & Security
        btnDataUsageInfo.setOnClickListener {
            val intent = Intent(this, PrivacySecurityActivity::class.java)
            startActivity(intent)
        }
        
        btnActivityTracking.setOnClickListener {
            val intent = Intent(this, PrivacySecurityActivity::class.java)
            startActivity(intent)
        }
        
        // Help & Support - Navigate to Help & Support
        btnFAQs.setOnClickListener {
            val intent = Intent(this, HelpSupportActivity::class.java)
            startActivity(intent)
        }
        
        btnContactSupport.setOnClickListener {
            val intent = Intent(this, HelpSupportActivity::class.java)
            startActivity(intent)
        }
        
        btnSendFeedback.setOnClickListener {
            val intent = Intent(this, HelpSupportActivity::class.java)
            startActivity(intent)
        }
        
        // About - Navigate to About
        btnDeveloperCredits.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
        
        // Navigation Buttons
        btnManageNotifications.setOnClickListener {
            val intent = Intent(this, NotificationSettingsActivity::class.java)
            startActivity(intent)
        }
        
        btnManageAudio.setOnClickListener {
            val intent = Intent(this, AudioSettingsActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun setupSwitches() {
        switchBackgroundMusic.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("background_music", isChecked).apply()
            if (isChecked) {
                Toast.makeText(this, "Background music enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Background music disabled", Toast.LENGTH_SHORT).show()
            }
        }
        
        switchDailyReminders.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("daily_reminders", isChecked).apply()
            if (isChecked) {
                Toast.makeText(this, "Daily reminders enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Daily reminders disabled", Toast.LENGTH_SHORT).show()
            }
        }
        
        switchCalmDownAlerts.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("calm_down_alerts", isChecked).apply()
            if (isChecked) {
                Toast.makeText(this, "Calm-down session alerts enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Calm-down session alerts disabled", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun setupSeekBar() {
        seekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    sharedPreferences.edit().putInt("volume_level", progress).apply()
                    Toast.makeText(this@SettingsActivity, "Volume: $progress%", Toast.LENGTH_SHORT).show()
                }
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    
    private fun loadSavedSettings() {
        // Load saved settings
        switchBackgroundMusic.isChecked = sharedPreferences.getBoolean("background_music", true)
        switchDailyReminders.isChecked = sharedPreferences.getBoolean("daily_reminders", true)
        switchCalmDownAlerts.isChecked = sharedPreferences.getBoolean("calm_down_alerts", false)
        seekBarVolume.progress = sharedPreferences.getInt("volume_level", 70)
    }
    
    // Dialog Methods
    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Log Out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ ->
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                // Navigate to login page
                val intent = Intent(this, login_page::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }
    
    private fun showManageProfileDialog() {
        AlertDialog.Builder(this)
            .setTitle("Manage Profile")
            .setMessage("Profile management features:\n\n" +
                    "• Edit personal information\n" +
                    "• Update profile picture\n" +
                    "• Change display name\n" +
                    "• Manage preferences\n" +
                    "• View activity history")
            .setPositiveButton("Edit Profile") { _, _ ->
                Toast.makeText(this, "Opening profile editor...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showChangeEmailPasswordDialog() {
        AlertDialog.Builder(this)
            .setTitle("Change Email / Password")
            .setMessage("Security options:\n\n" +
                    "• Change email address\n" +
                    "• Update password\n" +
                    "• Enable two-factor authentication\n" +
                    "• Manage login sessions\n" +
                    "• Security questions")
            .setPositiveButton("Change") { _, _ ->
                Toast.makeText(this, "Opening security settings...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showSessionDurationDialog() {
        val durations = arrayOf("5 minutes", "10 minutes", "15 minutes", "20 minutes", "30 minutes")
        AlertDialog.Builder(this)
            .setTitle("Session Duration")
            .setItems(durations) { _, which ->
                val selectedDuration = durations[which]
                sharedPreferences.edit().putString("session_duration", selectedDuration).apply()
                Toast.makeText(this, "Session duration set to: $selectedDuration", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showCalmingTechniquesDialog() {
        AlertDialog.Builder(this)
            .setTitle("Calming Techniques")
            .setMessage("Available techniques:\n\n" +
                    "• Deep breathing exercises\n" +
                    "• Progressive muscle relaxation\n" +
                    "• Guided meditation\n" +
                    "• Mindfulness practices\n" +
                    "• Visualization techniques\n" +
                    "• Grounding exercises")
            .setPositiveButton("Configure") { _, _ ->
                Toast.makeText(this, "Opening technique preferences...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showThemeModeDialog() {
        val themes = arrayOf("Light Mode", "Dark Mode", "Auto (System)")
        val currentTheme = sharedPreferences.getString("theme_mode", "Light Mode") ?: "Light Mode"
        val currentIndex = themes.indexOf(currentTheme)
        
        AlertDialog.Builder(this)
            .setTitle("Theme Mode")
            .setSingleChoiceItems(themes, currentIndex) { dialog, which ->
                val selectedTheme = themes[which]
                sharedPreferences.edit().putString("theme_mode", selectedTheme).apply()
                
                // Apply the selected theme
                when (selectedTheme) {
                    "Light Mode" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        Toast.makeText(this, "Theme set to: Light Mode", Toast.LENGTH_SHORT).show()
                    }
                    "Dark Mode" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        Toast.makeText(this, "Theme set to: Dark Mode", Toast.LENGTH_SHORT).show()
                    }
                    "Auto (System)" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        Toast.makeText(this, "Theme set to: Auto (System)", Toast.LENGTH_SHORT).show()
                    }
                }
                
                // Recreate the activity to apply theme changes
                recreate()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showFontSizeDialog() {
        val sizes = arrayOf("Small", "Medium", "Large", "Extra Large")
        val currentSize = sharedPreferences.getString("font_size", "Medium") ?: "Medium"
        val currentIndex = sizes.indexOf(currentSize)
        
        AlertDialog.Builder(this)
            .setTitle("Font Size")
            .setSingleChoiceItems(sizes, currentIndex) { dialog, which ->
                val selectedSize = sizes[which]
                sharedPreferences.edit().putString("font_size", selectedSize).apply()
                Toast.makeText(this, "Font size set to: $selectedSize", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showDataUsageInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle("Data Usage Info")
            .setMessage("Data usage statistics:\n\n" +
                    "• App storage: 45.2 MB\n" +
                    "• Cache: 12.8 MB\n" +
                    "• User data: 8.5 MB\n" +
                    "• Media files: 24.1 MB\n\n" +
                    "Last updated: Today")
            .setPositiveButton("Clear Cache") { _, _ ->
                Toast.makeText(this, "Cache cleared successfully", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Close", null)
            .show()
    }
    
    private fun showActivityTrackingDialog() {
        AlertDialog.Builder(this)
            .setTitle("Activity Tracking")
            .setMessage("Tracking settings:\n\n" +
                    "• Session history\n" +
                    "• Mood patterns\n" +
                    "• Progress analytics\n" +
                    "• Usage statistics\n" +
                    "• Privacy controls")
            .setPositiveButton("View Analytics") { _, _ ->
                Toast.makeText(this, "Opening activity analytics...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showFAQsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Frequently Asked Questions")
            .setMessage("Common questions:\n\n" +
                    "Q: How do I start a breathing session?\n" +
                    "A: Tap 'Breathe Now' on the home screen.\n\n" +
                    "Q: How do I track my mood?\n" +
                    "A: Use the 'Track Mood' feature daily.\n\n" +
                    "Q: Can I customize session duration?\n" +
                    "A: Yes, in AI Session Preferences.\n\n" +
                    "Q: How do I reset my password?\n" +
                    "A: Use the 'Change Email/Password' option.")
            .setPositiveButton("More FAQs") { _, _ ->
                Toast.makeText(this, "Opening full FAQ section...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Close", null)
            .show()
    }
    
    private fun showContactSupportDialog() {
        AlertDialog.Builder(this)
            .setTitle("Contact Support")
            .setMessage("Support options:\n\n" +
                    "• Email: support@angermanagement.com\n" +
                    "• Phone: +1-800-ANGER-HELP\n" +
                    "• Live chat: Available 24/7\n" +
                    "• Response time: Within 24 hours\n" +
                    "• Emergency: Call 911 if in crisis")
            .setPositiveButton("Send Email") { _, _ ->
                Toast.makeText(this, "Opening email client...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showSendFeedbackDialog() {
        AlertDialog.Builder(this)
            .setTitle("Send Feedback")
            .setMessage("We value your feedback!\n\n" +
                    "• Rate the app\n" +
                    "• Report bugs\n" +
                    "• Suggest features\n" +
                    "• Share experience\n" +
                    "• Help improve the app")
            .setPositiveButton("Rate App") { _, _ ->
                Toast.makeText(this, "Opening app store...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showDeveloperCreditsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Developer Credits")
            .setMessage("Anger Management App v1.0.0\n\n" +
                    "Development Team:\n" +
                    "• Lead Developer: John Smith\n" +
                    "• UI/UX Designer: Sarah Johnson\n" +
                    "• Mental Health Advisor: Dr. Emily Brown\n" +
                    "• Quality Assurance: Mike Wilson\n\n" +
                    "Special thanks to:\n" +
                    "• Mental health professionals\n" +
                    "• Beta testers\n" +
                    "• Open source contributors")
            .setPositiveButton("OK", null)
            .show()
    }
} 