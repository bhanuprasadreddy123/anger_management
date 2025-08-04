package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class DailyJournalActivity : AppCompatActivity() {
    
    private lateinit var journalEntry: EditText
    private lateinit var btnCalm: MaterialButton
    private lateinit var btnIrritated: MaterialButton
    private lateinit var btnFrustrated: MaterialButton
    private lateinit var btnAngry: MaterialButton
    private lateinit var btnSaveJournal: MaterialButton
    private lateinit var btnClear: TextView
    
    private var selectedMood: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dailyjournal)
        
        // Initialize views
        initializeViews()
        
        // Set current date
        setCurrentDate()
        
        // Set click listeners
        setClickListeners()
        
        // Start autosave timer
        startAutosaveTimer()
    }
    
    private fun initializeViews() {
        journalEntry = findViewById(R.id.journal_entry)
        btnCalm = findViewById(R.id.btn_calm)
        btnIrritated = findViewById(R.id.btn_irritated)
        btnFrustrated = findViewById(R.id.btn_frustrated)
        btnAngry = findViewById(R.id.btn_angry)
        btnSaveJournal = findViewById(R.id.btn_save_journal)
        btnClear = findViewById(R.id.btn_clear)
    }
    
    private fun setCurrentDate() {
        val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        
        val dateTextView = findViewById<TextView>(R.id.date_text)
        dateTextView.text = currentDate
    }
    
    private fun setClickListeners() {
        // Back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
        
        // Mood buttons
        btnCalm.setOnClickListener {
            selectMood("Calm", btnCalm)
        }
        
        btnIrritated.setOnClickListener {
            selectMood("Irritated", btnIrritated)
        }
        
        btnFrustrated.setOnClickListener {
            selectMood("Frustrated", btnFrustrated)
        }
        
        btnAngry.setOnClickListener {
            selectMood("Angry", btnAngry)
        }
        
        // Save journal button
        btnSaveJournal.setOnClickListener {
            android.util.Log.d("DailyJournal", "Save Journal button clicked")
            // Add visual feedback
            btnSaveJournal.isEnabled = false
            btnSaveJournal.text = "Saving..."
            saveJournal()
        }
        
        // Clear button
        btnClear.setOnClickListener {
            clearJournal()
        }
    }
    
    private fun selectMood(mood: String, selectedButton: MaterialButton) {
        // Reset all buttons to default state
        resetMoodButtons()
        
        // Highlight selected button
        selectedButton.setStrokeColorResource(android.R.color.holo_blue_dark)
        selectedButton.strokeWidth = 2
        
        selectedMood = mood
    }
    
    private fun resetMoodButtons() {
        val buttons = listOf(btnCalm, btnIrritated, btnFrustrated, btnAngry)
        buttons.forEach { button ->
            button.setStrokeColorResource(android.R.color.transparent)
            button.strokeWidth = 0
        }
    }
    
    private fun saveJournal() {
        val journalText = journalEntry.text.toString().trim()
        
        if (journalText.isEmpty()) {
            Toast.makeText(this, "Please write something in your journal", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (selectedMood == null) {
            Toast.makeText(this, "Please select how you're feeling today", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Get current date
        val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        
        // Debug logging
        android.util.Log.d("DailyJournal", "Saving journal: $journalText")
        android.util.Log.d("DailyJournal", "Selected mood: $selectedMood")
        android.util.Log.d("DailyJournal", "Current date: $currentDate")
        
        // Save to SharedPreferences
        saveJournalToStorage(journalText, selectedMood!!, System.currentTimeMillis())
        
        // Navigate to success page
        navigateToSuccessPage(journalText, selectedMood!!, currentDate)
    }
    
    private fun saveJournalToStorage(text: String, mood: String, timestamp: Long) {
        val sharedPreferences = getSharedPreferences("journal_entries", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        
        // Get current count
        val currentCount = sharedPreferences.getInt("entries_count", 0)
        
        // Save the new entry
        editor.putString("entry_text_$currentCount", text)
        editor.putString("entry_mood_$currentCount", mood)
        editor.putLong("entry_timestamp_$currentCount", timestamp)
        
        // Increment count
        editor.putInt("entries_count", currentCount + 1)
        
        // Apply changes
        editor.apply()
        
        android.util.Log.d("DailyJournal", "Saved journal entry #$currentCount to SharedPreferences")
    }
    
    private fun clearJournal() {
        journalEntry.text.clear()
        resetMoodButtons()
        selectedMood = null
    }
    
    private fun startAutosaveTimer() {
        // Implement autosave functionality
        // This would save the journal entry periodically
    }
    
    private fun navigateToSuccessPage(journalText: String, journalMood: String, journalDate: String) {
        try {
            android.util.Log.d("DailyJournal", "Attempting to navigate to JournalSavedActivity")
            val intent = Intent(this, JournalSavedActivity::class.java)
            intent.putExtra("journal_text", journalText)
            intent.putExtra("journal_mood", journalMood)
            intent.putExtra("journal_date", journalDate)
            startActivity(intent)
            android.util.Log.d("DailyJournal", "Successfully started JournalSavedActivity")
            // Reset button state before finishing
            btnSaveJournal.isEnabled = true
            btnSaveJournal.text = "Save Journal"
            finish() // Close the current activity
        } catch (e: Exception) {
            android.util.Log.e("DailyJournal", "Failed to navigate to JournalSavedActivity: ${e.message}", e)
            Toast.makeText(this, "Error saving journal: ${e.message}", Toast.LENGTH_LONG).show()
            // Reset button state
            btnSaveJournal.isEnabled = true
            btnSaveJournal.text = "Save Journal"
        }
    }
    
    data class JournalEntry(
        val text: String,
        val mood: String?,
        val timestamp: Date
    )
}