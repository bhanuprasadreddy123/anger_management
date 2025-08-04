package com.example.anger_management

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.switchmaterial.SwitchMaterial

class NotificationSettingsActivity : BaseActivity() {
    
    private lateinit var btnBack: ImageButton
    private lateinit var btnHelp: ImageButton
    
    // Notification Switches
    private lateinit var switchDailyReminders: SwitchMaterial
    private lateinit var switchCalmDownAlerts: SwitchMaterial
    private lateinit var switchNotificationSound: SwitchMaterial
    private lateinit var switchVibration: SwitchMaterial
    
    // Settings Buttons
    private lateinit var btnReminderTime: LinearLayout
    private lateinit var btnReminderFrequency: LinearLayout
    private lateinit var btnQuietHours: LinearLayout
    
    // Settings TextViews
    private lateinit var tvReminderTime: TextView
    private lateinit var tvReminderFrequency: TextView
    private lateinit var tvQuietHours: TextView
    
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
        btnHelp = findViewById(R.id.btnHelp)
        
        // Notification Switches
        switchDailyReminders = findViewById(R.id.switchDailyReminders)
        switchCalmDownAlerts = findViewById(R.id.switchCalmDownAlerts)
        switchNotificationSound = findViewById(R.id.switchNotificationSound)
        switchVibration = findViewById(R.id.switchVibration)
        
        // Settings Buttons
        btnReminderTime = findViewById(R.id.btnReminderTime)
        btnReminderFrequency = findViewById(R.id.btnReminderFrequency)
        btnQuietHours = findViewById(R.id.btnQuietHours)
        
        // Settings TextViews
        tvReminderTime = findViewById(R.id.tvReminderTime)
        tvReminderFrequency = findViewById(R.id.tvReminderFrequency)
        tvQuietHours = findViewById(R.id.tvQuietHours)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnHelp.setOnClickListener {
            showNotificationSettingsInfo()
        }
        
        btnReminderTime.setOnClickListener {
            Toast.makeText(this, "Reminder Time button clicked", Toast.LENGTH_SHORT).show()
            showReminderTimeDialog()
        }
        
        btnReminderFrequency.setOnClickListener {
            Toast.makeText(this, "Reminder Frequency button clicked", Toast.LENGTH_SHORT).show()
            showReminderFrequencyDialog()
        }
        
        btnQuietHours.setOnClickListener {
            Toast.makeText(this, "Quiet Hours button clicked", Toast.LENGTH_SHORT).show()
            showQuietHoursDialog()
        }
    }
    
    private fun setupSwitches() {
        switchDailyReminders.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("daily_reminders", isChecked).apply()
            if (isChecked) {
                Toast.makeText(this, "Daily reminders enabled", Toast.LENGTH_SHORT).show()
                // Schedule daily reminders
                scheduleDailyReminders()
            } else {
                Toast.makeText(this, "Daily reminders disabled", Toast.LENGTH_SHORT).show()
                // Cancel daily reminders
                cancelDailyReminders()
            }
        }
        
        switchCalmDownAlerts.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("calm_down_alerts", isChecked).apply()
            if (isChecked) {
                Toast.makeText(this, "Calm-down alerts enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Calm-down alerts disabled", Toast.LENGTH_SHORT).show()
            }
        }
        
        switchNotificationSound.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("notification_sound", isChecked).apply()
            if (isChecked) {
                Toast.makeText(this, "Notification sounds enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notification sounds disabled", Toast.LENGTH_SHORT).show()
            }
        }
        
        switchVibration.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("notification_vibration", isChecked).apply()
            if (isChecked) {
                Toast.makeText(this, "Vibration enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vibration disabled", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun loadSavedSettings() {
        // Load switch states
        switchDailyReminders.isChecked = sharedPreferences.getBoolean("daily_reminders", true)
        switchCalmDownAlerts.isChecked = sharedPreferences.getBoolean("calm_down_alerts", true)
        switchNotificationSound.isChecked = sharedPreferences.getBoolean("notification_sound", true)
        switchVibration.isChecked = sharedPreferences.getBoolean("notification_vibration", true)
        
        // Load saved values and update UI
        val savedReminderTime = sharedPreferences.getString("reminder_time", "9:00 AM") ?: "9:00 AM"
        val savedReminderFreq = sharedPreferences.getString("reminder_frequency", "Once daily") ?: "Once daily"
        val savedQuietHours = sharedPreferences.getString("quiet_hours", "10:00 PM - 7:00 AM") ?: "10:00 PM - 7:00 AM"
        
        tvReminderTime.text = savedReminderTime
        tvReminderFrequency.text = savedReminderFreq
        tvQuietHours.text = savedQuietHours
    }
    
    private fun showReminderTimeDialog() {
        try {
            Toast.makeText(this, "Opening reminder time dialog...", Toast.LENGTH_SHORT).show()
            
            val times = arrayOf(
                "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM",
                "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM",
                "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM",
                "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM",
                "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM", "12:00 AM"
            )
            
            val currentTime = sharedPreferences.getString("reminder_time", "9:00 AM") ?: "9:00 AM"
            val currentIndex = times.indexOf(currentTime).takeIf { it >= 0 } ?: 8
            
            runOnUiThread {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Daily Reminder Time")
                    .setMessage("Choose when you want to receive daily reminders:")
                    .setSingleChoiceItems(times, currentIndex) { dialog, which ->
                        val selectedTime = times[which]
                        sharedPreferences.edit().putString("reminder_time", selectedTime).apply()
                        tvReminderTime.text = selectedTime
                        Toast.makeText(this, "Reminder time set to $selectedTime", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                
                dialog.show()
                Toast.makeText(this, "Reminder time dialog opened with ${times.size} time options", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening reminder time dialog: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
    
    private fun showReminderFrequencyDialog() {
        try {
            Toast.makeText(this, "Opening reminder frequency dialog...", Toast.LENGTH_SHORT).show()
            
            val frequencies = arrayOf(
                "Once daily", "Twice daily", "Three times daily", "Four times daily", "Five times daily",
                "Every 2 hours", "Every 3 hours", "Every 4 hours", "Every 6 hours", "Every 8 hours",
                "Every 12 hours", "Every Monday", "Every Tuesday", "Every Wednesday", "Every Thursday",
                "Every Friday", "Every Saturday", "Every Sunday", "Weekdays only", "Weekends only",
                "Every 2 days", "Every 3 days", "Every week", "Every 2 weeks", "Every month"
            )
            val currentFreq = sharedPreferences.getString("reminder_frequency", "Once daily") ?: "Once daily"
            val currentIndex = frequencies.indexOf(currentFreq).takeIf { it >= 0 } ?: 0
            
            runOnUiThread {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Reminder Frequency")
                    .setMessage("Choose how often you want to receive reminders:")
                    .setSingleChoiceItems(frequencies, currentIndex) { dialog, which ->
                        val selectedFreq = frequencies[which]
                        sharedPreferences.edit().putString("reminder_frequency", selectedFreq).apply()
                        tvReminderFrequency.text = selectedFreq
                        Toast.makeText(this, "Reminder frequency set to $selectedFreq", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                
                dialog.show()
                Toast.makeText(this, "Reminder frequency dialog opened with ${frequencies.size} frequency options", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening reminder frequency dialog: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
    
    private fun showQuietHoursDialog() {
        try {
            Toast.makeText(this, "Opening quiet hours dialog...", Toast.LENGTH_SHORT).show()
            
            val quietHoursOptions = arrayOf("10:00 PM - 7:00 AM", "11:00 PM - 6:00 AM", "9:00 PM - 8:00 AM", "12:00 AM - 6:00 AM", "Custom")
            val currentSetting = sharedPreferences.getString("quiet_hours", "10:00 PM - 7:00 AM") ?: "10:00 PM - 7:00 AM"
            val currentIndex = quietHoursOptions.indexOf(currentSetting).takeIf { it >= 0 } ?: 0
            
            runOnUiThread {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Quiet Hours Configuration")
                    .setMessage("Choose your quiet hours when notifications are silenced:")
                    .setSingleChoiceItems(quietHoursOptions, currentIndex) { dialog, which ->
                        val selectedSetting = quietHoursOptions[which]
                        sharedPreferences.edit().putString("quiet_hours", selectedSetting).apply()
                        tvQuietHours.text = selectedSetting
                        Toast.makeText(this, "Quiet hours set to $selectedSetting", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                
                dialog.show()
                Toast.makeText(this, "Quiet hours dialog opened with ${quietHoursOptions.size} options", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening quiet hours dialog: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
    
    private fun scheduleDailyReminders() {
        // This would typically use AlarmManager or WorkManager to schedule notifications
        // For now, we'll just show a message that reminders are scheduled
        val reminderTime = sharedPreferences.getString("reminder_time", "9:00 AM") ?: "9:00 AM"
        Toast.makeText(this, "Daily reminders scheduled for $reminderTime", Toast.LENGTH_LONG).show()
    }
    
    private fun cancelDailyReminders() {
        // This would typically cancel scheduled notifications
        // For now, we'll just show a message that reminders are canceled
        Toast.makeText(this, "Daily reminders canceled", Toast.LENGTH_SHORT).show()
    }
    
    private fun showNotificationSettingsInfo() {
        AlertDialog.Builder(this)
            .setTitle("Notification Settings")
            .setMessage("Configure your notification preferences:\n\n" +
                    "• Daily Reminders: Get daily notifications for anger management practice\n" +
                    "• Calm-down Alerts: Receive alerts when you might need to calm down\n" +
                    "• Notification Sound: Play sounds for notifications\n" +
                    "• Vibration: Vibrate device for notifications\n\n" +
                    "Settings are automatically saved when you toggle switches.")
            .setPositiveButton("OK", null)
            .show()
    }
    

} 