package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class JournalDetailActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_detail)
        
        // Get data from intent
        val journalText = intent.getStringExtra("journal_text") ?: ""
        val journalMood = intent.getStringExtra("journal_mood") ?: "Not specified"
        val journalDate = intent.getStringExtra("journal_date") ?: "Today"
        
        // Initialize views
        initializeViews()
        
        // Set journal data
        setJournalData(journalText, journalMood, journalDate)
        
        // Set click listeners
        setClickListeners()
    }
    
    private fun initializeViews() {
        // Back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
    }
    
    private fun setJournalData(journalText: String, journalMood: String, journalDate: String) {
        val tvDate = findViewById<TextView>(R.id.tv_date)
        val tvMood = findViewById<TextView>(R.id.tv_mood)
        val tvJournalText = findViewById<TextView>(R.id.tv_journal_text)
        
        tvDate.text = journalDate
        tvMood.text = journalMood
        tvJournalText.text = journalText
        
        // Set mood badge background
        val moodColor = when (journalMood) {
            "Calm" -> R.drawable.mood_badge_calm
            "Irritated" -> R.drawable.mood_badge_irritated
            "Frustrated" -> R.drawable.mood_badge_frustrated
            "Angry" -> R.drawable.mood_badge_angry
            else -> R.drawable.mood_badge_calm
        }
        tvMood.setBackgroundResource(moodColor)
    }
    
    private fun setClickListeners() {
        // Edit button
        findViewById<MaterialButton>(R.id.btn_edit).setOnClickListener {
            // For now, just show a message
            android.widget.Toast.makeText(this, "Edit functionality coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        // Delete button
        findViewById<MaterialButton>(R.id.btn_delete).setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }
    
    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Journal Entry")
            .setMessage("Are you sure you want to delete this journal entry? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                // Delete the entry from SharedPreferences
                deleteJournalEntry()
                android.widget.Toast.makeText(this, "Journal entry deleted", android.widget.Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun deleteJournalEntry() {
        // For now, we'll just show a message
        // In a full implementation, you would find and remove the specific entry
        android.widget.Toast.makeText(this, "Delete functionality will be implemented soon", android.widget.Toast.LENGTH_SHORT).show()
    }
} 