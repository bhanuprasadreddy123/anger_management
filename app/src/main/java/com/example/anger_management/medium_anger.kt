package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class medium_anger : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium_anger)
        
        // Set up back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
        
        // Set up physical relief card click listeners
        setupPhysicalReliefListeners()
        
        // Set up calming tools click listeners
        setupCalmingToolsListeners()
        
        // Set up journal entry click listener
        setupJournalEntryListener()
        
        // Update intensity level dynamically
        updateIntensityLevel()
    }
    
    private fun setupPhysicalReliefListeners() {
        // Jogging card
        findViewById<CardView>(R.id.card_jogging).setOnClickListener {
            val intent = Intent(this, JoggingActivity::class.java)
            startActivity(intent)
        }
        
        // Push-ups card
        findViewById<CardView>(R.id.card_pushups).setOnClickListener {
            val intent = Intent(this, PushUpsActivity::class.java)
            startActivity(intent)
        }
        
        // Shadow Boxing card
        findViewById<CardView>(R.id.card_shadowboxing).setOnClickListener {
            val intent = Intent(this, ShadowBoxingActivity::class.java)
            startActivity(intent)
        }
        
        // Dance card
        findViewById<CardView>(R.id.card_dance).setOnClickListener {
            val intent = Intent(this, DanceActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun setupCalmingToolsListeners() {
        // Calming Music card
        findViewById<androidx.cardview.widget.CardView>(R.id.card_calming_music).setOnClickListener {
            val intent = Intent(this, CalmingMusicActivity::class.java)
            startActivity(intent)
        }
        
        // Guided Meditation card
        findViewById<androidx.cardview.widget.CardView>(R.id.card_guided_meditation).setOnClickListener {
            val intent = Intent(this, GuidedMeditationActivity::class.java)
            startActivity(intent)
        }
        
        // Natural Sounds card
        findViewById<androidx.cardview.widget.CardView>(R.id.card_natural_sounds).setOnClickListener {
            val intent = Intent(this, NaturalSoundsActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun setupJournalEntryListener() {
        // Journal Entry card
        findViewById<androidx.cardview.widget.CardView>(R.id.card_journal_entry).setOnClickListener {
            val intent = Intent(this, DailyJournalActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun updateIntensityLevel() {
        // Get current date to determine if we should show different intensity
        val currentDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
        val sharedPreferences = getSharedPreferences("anger_management_prefs", MODE_PRIVATE)
        val lastLoginDate = sharedPreferences.getString("last_login_date", "")
        
        // If it's a new day, update the intensity level
        if (lastLoginDate != currentDate) {
            val intensityLevels = listOf("Low Intensity Level", "Medium Intensity Level", "High Intensity Level")
            val randomIntensity = intensityLevels.random()
            
            // Save the new intensity level
            sharedPreferences.edit()
                .putString("current_intensity_level", randomIntensity)
                .putString("last_login_date", currentDate)
                .apply()
        }
        
        // Display the current intensity level
        val currentIntensity = sharedPreferences.getString("current_intensity_level", "Medium Intensity Level")
        findViewById<android.widget.TextView>(R.id.tv_intensity_level).text = currentIntensity
    }
}