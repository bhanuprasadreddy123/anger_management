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

class NotificationSettingsActivity : BaseActivity() {
    
    private lateinit var btnBack: ImageButton
    
    // Notification Switches
    private lateinit var switchDailyReminders: SwitchMaterial
    private lateinit var switchCalmDownAlerts: SwitchMaterial
    private lateinit var switchProgressUpdates: SwitchMaterial
    private lateinit var switchNotificationSound: SwitchMaterial
    private lateinit var switchVibration: SwitchMaterial
    
    // Settings Buttons
    private lateinit var btnReminderTime: LinearLayout
    private lateinit var btnReminderFrequency: LinearLayout
    private lateinit var btnQuietHours: LinearLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notification_settings)
        
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
        
        // Notification Switches
        switchDailyReminders = findViewById(R.id.switchDailyReminders)
        switchCalmDownAlerts = findViewById(R.id.switchCalmDownAlerts)
        switchProgressUpdates = findViewById(R.id.switchProgressUpdates)
        switchNotificationSound = findViewById(R.id.switchNotificationSound)
        switchVibration = findViewById(R.id.switchVibration)
        
        // Settings Buttons
        btnReminderTime = findViewById(R.id.btnReminderTime)
        btnReminderFrequency = findViewById(R.id.btnReminderFrequency)
        btnQuietHours = findViewById(R.id.btnQuietHours)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnReminderTime.setOnClickListener {
            showReminderTimeDialog()
        }
        
        btnReminderFrequency.setOnClickListener {
            showReminderFrequencyDialog()
        }
        
        btnQuietHours.setOnClickListener {
            showQuietHoursDialog()
        }
    }
    
    private fun setupSwitches() {
        switchDailyReminders.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("daily_reminders", isChecked).apply()
        }
        
        switchCalmDownAlerts.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("calm_down_alerts", isChecked).apply()
        }
        
        switchProgressUpdates.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("progress_updates", isChecked).apply()
        }
        
        switchNotificationSound.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("notification_sound", isChecked).apply()
        }
        
        switchVibration.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("notification_vibration", isChecked).apply()
        }
    }
    
    private fun loadSavedSettings() {
        // Load switch states
        switchDailyReminders.isChecked = sharedPreferences.getBoolean("daily_reminders", true)
        switchCalmDownAlerts.isChecked = sharedPreferences.getBoolean("calm_down_alerts", true)
        switchProgressUpdates.isChecked = sharedPreferences.getBoolean("progress_updates", false)
        switchNotificationSound.isChecked = sharedPreferences.getBoolean("notification_sound", true)
        switchVibration.isChecked = sharedPreferences.getBoolean("notification_vibration", true)
    }
    
    private fun showReminderTimeDialog() {
        val times = arrayOf("6:00 AM", "7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", 
                           "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM", 
                           "6:00 PM", "7:00 PM", "8:00 PM", "9:00 PM")
        
        val currentTime = sharedPreferences.getString("reminder_time", "9:00 AM") ?: "9:00 AM"
        val currentIndex = times.indexOf(currentTime).takeIf { it >= 0 } ?: 2
        
        AlertDialog.Builder(this)
            .setTitle("Daily Reminder Time")
            .setSingleChoiceItems(times, currentIndex) { _, which ->
                val selectedTime = times[which]
                sharedPreferences.edit().putString("reminder_time", selectedTime).apply()
                Toast.makeText(this, "Reminder time set to $selectedTime", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showReminderFrequencyDialog() {
        val frequencies = arrayOf("Once daily", "Twice daily", "Three times daily", "Every 4 hours", "Every 6 hours")
        val currentFreq = sharedPreferences.getString("reminder_frequency", "Once daily") ?: "Once daily"
        val currentIndex = frequencies.indexOf(currentFreq).takeIf { it >= 0 } ?: 0
        
        AlertDialog.Builder(this)
            .setTitle("Reminder Frequency")
            .setSingleChoiceItems(frequencies, currentIndex) { _, which ->
                val selectedFreq = frequencies[which]
                sharedPreferences.edit().putString("reminder_frequency", selectedFreq).apply()
                Toast.makeText(this, "Reminder frequency set to $selectedFreq", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showQuietHoursDialog() {
        AlertDialog.Builder(this)
            .setTitle("Quiet Hours")
            .setMessage("Configure quiet hours when notifications are silenced:\n\n" +
                    "Current settings:\n" +
                    "• Start: 10:00 PM\n" +
                    "• End: 7:00 AM\n" +
                    "• Days: Every day\n\n" +
                    "During quiet hours:\n" +
                    "• No sound notifications\n" +
                    "• No vibration\n" +
                    "• Emergency alerts still active")
            .setPositiveButton("Configure") { _, _ ->
                Toast.makeText(this, "Opening quiet hours settings...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
} 