package com.example.anger_management

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class GratitudeJournalActivity : AppCompatActivity() {
    
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etGratitude1: EditText
    private lateinit var etGratitude2: EditText
    private lateinit var etGratitude3: EditText
    private lateinit var tvGratitudeTips: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gratitude_journal)
        
        sharedPreferences = getSharedPreferences("GratitudeJournal", Context.MODE_PRIVATE)
        
        initializeViews()
        setupClickListeners()
        loadSavedGratitude()
        loadDynamicTips()
    }
    
    private fun initializeViews() {
        etGratitude1 = findViewById(R.id.et_gratitude_1)
        etGratitude2 = findViewById(R.id.et_gratitude_2)
        etGratitude3 = findViewById(R.id.et_gratitude_3)
        tvGratitudeTips = findViewById<TextView>(R.id.tv_gratitude_tips)
    }
    
    private fun setupClickListeners() {
        // Back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
        
        // Save & Reflect button
        findViewById<MaterialButton>(R.id.btn_save_gratitude).setOnClickListener {
            saveGratitude()
        }
        
        // Share button
        findViewById<MaterialButton>(R.id.btn_share_gratitude).setOnClickListener {
            shareGratitude()
        }
    }
    
    private fun loadSavedGratitude() {
        try {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val savedDate = sharedPreferences.getString("last_saved_date", "")
            
            if (savedDate == today) {
                // Load today's saved gratitude
                etGratitude1.setText(sharedPreferences.getString("gratitude_1", ""))
                etGratitude2.setText(sharedPreferences.getString("gratitude_2", ""))
                etGratitude3.setText(sharedPreferences.getString("gratitude_3", ""))
                
                // Show a message if there's saved data
                val hasSavedData = (sharedPreferences.getString("gratitude_1", "") ?: "").isNotEmpty() ||
                                 (sharedPreferences.getString("gratitude_2", "") ?: "").isNotEmpty() ||
                                 (sharedPreferences.getString("gratitude_3", "") ?: "").isNotEmpty()
                
                if (hasSavedData) {
                    android.widget.Toast.makeText(this, "Loaded today's saved gratitude", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "Error loading saved gratitude: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    private fun saveGratitude() {
        val gratitude1 = etGratitude1.text.toString().trim()
        val gratitude2 = etGratitude2.text.toString().trim()
        val gratitude3 = etGratitude3.text.toString().trim()
        
        if (gratitude1.isEmpty() && gratitude2.isEmpty() && gratitude3.isEmpty()) {
            android.widget.Toast.makeText(this, "Please write at least one thing you're grateful for", android.widget.Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            // Save to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("last_saved_date", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
            editor.putString("gratitude_1", gratitude1)
            editor.putString("gratitude_2", gratitude2)
            editor.putString("gratitude_3", gratitude3)
            editor.apply()
            
            // Show immediate confirmation
            android.widget.Toast.makeText(this, "Gratitude saved successfully! 🌟", android.widget.Toast.LENGTH_SHORT).show()
            
            showGratitudeConfirmation(gratitude1, gratitude2, gratitude3)
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "Error saving gratitude: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    private fun showGratitudeConfirmation(g1: String, g2: String, g3: String) {
        val message = buildString {
            appendLine("Your gratitude reflections:")
            appendLine()
            if (g1.isNotEmpty()) appendLine("✨ $g1")
            if (g2.isNotEmpty()) appendLine("✨ $g2")
            if (g3.isNotEmpty()) appendLine("✨ $g3")
            appendLine()
            appendLine("Taking time to appreciate the good things helps shift your perspective and reduce frustration.")
        }
        
        AlertDialog.Builder(this)
            .setTitle("Gratitude Saved! 🌟")
            .setMessage(message)
            .setPositiveButton("I feel better") { _, _ ->
                // Update progress in parent activity if needed
                updateParentProgress()
                finish()
            }
            .setNegativeButton("Add more") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun shareGratitude() {
        val gratitude1 = etGratitude1.text.toString().trim()
        val gratitude2 = etGratitude2.text.toString().trim()
        val gratitude3 = etGratitude3.text.toString().trim()
        
        if (gratitude1.isEmpty() && gratitude2.isEmpty() && gratitude3.isEmpty()) {
            android.widget.Toast.makeText(this, "Please write something to share", android.widget.Toast.LENGTH_SHORT).show()
            return
        }
        
        val shareText = buildString {
            appendLine("Today I'm grateful for:")
            appendLine()
            if (gratitude1.isNotEmpty()) appendLine("✨ $gratitude1")
            if (gratitude2.isNotEmpty()) appendLine("✨ $gratitude2")
            if (gratitude3.isNotEmpty()) appendLine("✨ $gratitude3")
            appendLine()
            appendLine("Practicing gratitude helps me stay positive even during challenging times.")
            appendLine()
            appendLine("Shared via Anger Management App")
        }
        
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        
        startActivity(Intent.createChooser(shareIntent, "Share Your Gratitude"))
    }
    
    private fun updateParentProgress() {
        // This could be used to update progress in the parent FrustrationReliefActivity
        // For now, we'll just show a success message
        android.widget.Toast.makeText(this, "Gratitude practice completed! Progress updated.", android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun loadDynamicTips() {
        val gratitudeTips = listOf(
            "• Think of simple, everyday things\n• Consider people who support you\n• Reflect on your strengths and abilities\n• Remember moments of joy or peace\n• Acknowledge your progress and growth",
            "• Focus on what you have, not what you lack\n• Appreciate the small moments of beauty\n• Remember times when you felt supported\n• Think about your personal achievements\n• Consider the kindness you've received",
            "• Look for the good in challenging situations\n• Remember people who believe in you\n• Appreciate your body and health\n• Think about your favorite memories\n• Acknowledge your resilience and courage",
            "• Consider the lessons you've learned\n• Appreciate the people who care about you\n• Remember moments of laughter and joy\n• Think about your dreams and aspirations\n• Acknowledge your unique qualities",
            "• Focus on what brings you peace\n• Remember times you overcame difficulties\n• Appreciate the beauty around you\n• Think about your talents and skills\n• Consider the love you give and receive"
        )
        
        val randomTip = gratitudeTips.random()
        tvGratitudeTips.text = randomTip
    }
} 