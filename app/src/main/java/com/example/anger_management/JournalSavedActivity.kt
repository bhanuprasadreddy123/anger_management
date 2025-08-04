package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class JournalSavedActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_saved)
        
        // Get data from intent
        val journalText = intent.getStringExtra("journal_text") ?: ""
        val journalMood = intent.getStringExtra("journal_mood") ?: "Not specified"
        val journalDate = intent.getStringExtra("journal_date") ?: "Today"
        
        // Initialize views
        initializeViews()
        
        // Set journal summary data
        setJournalSummary(journalText, journalMood, journalDate)
        
        // Set click listeners
        setClickListeners()
    }
    
    private fun initializeViews() {
        // Back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
    }
    
    private fun setJournalSummary(journalText: String, journalMood: String, journalDate: String) {
        val tvJournalDate = findViewById<TextView>(R.id.tv_journal_date)
        val tvJournalTime = findViewById<TextView>(R.id.tv_journal_time)
        val tvJournalMood = findViewById<TextView>(R.id.tv_journal_mood)
        val tvJournalPreview = findViewById<TextView>(R.id.tv_journal_preview)
        
        tvJournalDate.text = "Date: $journalDate"
        
        // Get current time
        val timeFormat = java.text.SimpleDateFormat("h:mm a", java.util.Locale.getDefault())
        val currentTime = timeFormat.format(java.util.Date())
        tvJournalTime.text = "Time: $currentTime"
        
        tvJournalMood.text = "Mood: $journalMood"
        
        // Create preview text (first 50 characters)
        val preview = if (journalText.length > 50) {
            journalText.substring(0, 50) + "..."
        } else {
            journalText
        }
        tvJournalPreview.text = "Preview: $preview"
    }
    
    private fun setClickListeners() {
        // New Entry button
        findViewById<MaterialButton>(R.id.btn_new_entry).setOnClickListener {
            // Start new journal entry
            val intent = Intent(this, DailyJournalActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        
        // View All Journals button
        findViewById<MaterialButton>(R.id.btn_view_journals).setOnClickListener {
            // Navigate to journal list
            val intent = Intent(this, JournalListActivity::class.java)
            startActivity(intent)
            finish()
        }
        
        // Go to Home button
        findViewById<MaterialButton>(R.id.btn_go_home).setOnClickListener {
            val intent = Intent(this, home_page::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        
        // Delete Entry button
        findViewById<MaterialButton>(R.id.btn_delete_entry).setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }
    
    private fun showDeleteConfirmationDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Delete Journal Entry")
            .setMessage("Are you sure you want to delete this journal entry? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                deleteLatestJournalEntry()
                android.widget.Toast.makeText(this, "Journal entry deleted", android.widget.Toast.LENGTH_SHORT).show()
                // Go back to home page
                val intent = Intent(this, home_page::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun deleteLatestJournalEntry() {
        val sharedPreferences = getSharedPreferences("journal_entries", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        
        // Get current count
        val currentCount = sharedPreferences.getInt("entries_count", 0)
        
        if (currentCount > 0) {
            // Remove the latest entry (the one we just saved)
            val lastIndex = currentCount - 1
            editor.remove("entry_text_$lastIndex")
            editor.remove("entry_mood_$lastIndex")
            editor.remove("entry_timestamp_$lastIndex")
            
            // Decrement count
            editor.putInt("entries_count", currentCount - 1)
            editor.apply()
            
            android.util.Log.d("JournalSaved", "Deleted journal entry #$lastIndex")
        }
    }
} 