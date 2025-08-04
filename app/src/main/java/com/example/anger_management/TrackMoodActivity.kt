package com.example.anger_management

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*
import com.example.anger_management.medium_anger
import com.example.anger_management.FrustrationReliefActivity

class TrackMoodActivity : AppCompatActivity() {
    
    private var selectedMood: String? = null
    private var selectedIntensity: Int = 3
    private var selectedNote: String = ""
    
    // Dot views for each mood
    private lateinit var calmDots: List<View>
    private lateinit var irritatedDots: List<View>
    private lateinit var frustratedDots: List<View>
    private lateinit var angryDots: List<View>
    
    // Note text views
    private lateinit var tvNoteCalm: TextView
    private lateinit var tvNoteIrritated: TextView
    private lateinit var tvNoteFrustrated: TextView
    private lateinit var tvNoteAngry: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_mood)
        
        initializeViews()
        setupClickListeners()
        updateCurrentDateTime()
        initializeDotsToDefault()
    }
    
    private fun initializeViews() {
        // Back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
        
        // Initialize dot views
        calmDots = listOf(
            findViewById(R.id.dot_calm_1),
            findViewById(R.id.dot_calm_2),
            findViewById(R.id.dot_calm_3),
            findViewById(R.id.dot_calm_4),
            findViewById(R.id.dot_calm_5)
        )
        
        irritatedDots = listOf(
            findViewById(R.id.dot_irritated_1),
            findViewById(R.id.dot_irritated_2),
            findViewById(R.id.dot_irritated_3),
            findViewById(R.id.dot_irritated_4),
            findViewById(R.id.dot_irritated_5)
        )
        
        frustratedDots = listOf(
            findViewById(R.id.dot_frustrated_1),
            findViewById(R.id.dot_frustrated_2),
            findViewById(R.id.dot_frustrated_3),
            findViewById(R.id.dot_frustrated_4),
            findViewById(R.id.dot_frustrated_5)
        )
        
        angryDots = listOf(
            findViewById(R.id.dot_angry_1),
            findViewById(R.id.dot_angry_2),
            findViewById(R.id.dot_angry_3),
            findViewById(R.id.dot_angry_4),
            findViewById(R.id.dot_angry_5)
        )
        
        // Initialize note text views
        tvNoteCalm = findViewById(R.id.tv_note_calm)
        tvNoteIrritated = findViewById(R.id.tv_note_irritated)
        tvNoteFrustrated = findViewById(R.id.tv_note_frustrated)
        tvNoteAngry = findViewById(R.id.tv_note_angry)
        
        // Debug logging
        android.widget.Toast.makeText(this, "Views initialized - Irritated dots: ${irritatedDots.size}", android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun setupClickListeners() {
        // Mood card click listeners
        findViewById<LinearLayout>(R.id.card_calm).setOnClickListener {
            selectMood("Calm")
        }
        
        findViewById<LinearLayout>(R.id.card_irritated).setOnClickListener {
            selectMood("Irritated")
        }
        
        findViewById<LinearLayout>(R.id.card_frustrated).setOnClickListener {
            selectMood("Frustrated")
        }
        
        findViewById<LinearLayout>(R.id.card_angry).setOnClickListener {
            selectMood("Angry")
        }
        
        // Dot click listeners for Calm
        setupDotListeners(calmDots, "Calm")
        
        // Dot click listeners for Irritated
        setupDotListeners(irritatedDots, "Irritated")
        android.widget.Toast.makeText(this, "Irritated dot listeners set up", android.widget.Toast.LENGTH_SHORT).show()
        
        // Dot click listeners for Frustrated
        setupDotListeners(frustratedDots, "Frustrated")
        
        // Dot click listeners for Angry
        setupDotListeners(angryDots, "Angry")
        
        // Note click listeners
        findViewById<LinearLayout>(R.id.note_calm).setOnClickListener {
            showNoteDialog("Calm")
        }
        
        findViewById<LinearLayout>(R.id.note_irritated).setOnClickListener {
            showNoteDialog("Irritated")
        }
        
        findViewById<LinearLayout>(R.id.note_frustrated).setOnClickListener {
            showNoteDialog("Frustrated")
        }
        
        findViewById<LinearLayout>(R.id.note_angry).setOnClickListener {
            showNoteDialog("Angry")
        }
        
        // Save mood button
        findViewById<MaterialButton>(R.id.btn_save_mood).setOnClickListener {
            saveMood()
        }
        
        // Test navigation button (temporary)
        findViewById<MaterialButton>(R.id.btn_save_mood).setOnLongClickListener {
            android.widget.Toast.makeText(this, "Testing navigation to Medium Anger...", android.widget.Toast.LENGTH_SHORT).show()
            try {
                val intent = Intent(this, medium_anger::class.java)
                startActivity(intent)
                android.widget.Toast.makeText(this, "Test navigation successful!", android.widget.Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                android.widget.Toast.makeText(this, "Test navigation failed: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
            }
            true
        }
        

    }
    
    private fun setupDotListeners(dots: List<View>, mood: String) {
        android.widget.Toast.makeText(this, "Setting up listeners for $mood mood with ${dots.size} dots", android.widget.Toast.LENGTH_SHORT).show()
        
        dots.forEachIndexed { index, dot ->
            dot.setOnClickListener {
                android.widget.Toast.makeText(this, "Dot clicked: $mood dot ${index + 1}", android.widget.Toast.LENGTH_SHORT).show()
                
                // Simple test - just show which mood was clicked
                when (mood) {
                    "Calm" -> {
                        android.widget.Toast.makeText(this, "Calm dot clicked - would navigate to Calm Space", android.widget.Toast.LENGTH_SHORT).show()
                        navigateToCalmSpace()
                    }
                    "Irritated" -> {
                        android.widget.Toast.makeText(this, "Irritated dot clicked - would navigate to Medium Anger", android.widget.Toast.LENGTH_SHORT).show()
                        try {
                            val intent = Intent(this, medium_anger::class.java)
                            android.widget.Toast.makeText(this, "Intent created for medium_anger", android.widget.Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            android.widget.Toast.makeText(this, "Navigation successful!", android.widget.Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            android.widget.Toast.makeText(this, "Navigation failed: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
                            e.printStackTrace()
                        }
                    }
                    "Frustrated" -> {
                        android.widget.Toast.makeText(this, "Frustrated dot clicked - would navigate to Frustration Relief", android.widget.Toast.LENGTH_SHORT).show()
                        try {
                            val intent = Intent(this, FrustrationReliefActivity::class.java)
                            android.widget.Toast.makeText(this, "Intent created for FrustrationReliefActivity", android.widget.Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            android.widget.Toast.makeText(this, "Navigation successful!", android.widget.Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            android.widget.Toast.makeText(this, "Navigation failed: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
                            e.printStackTrace()
                        }
                    }
                    "Angry" -> {
                        android.widget.Toast.makeText(this, "Angry dot clicked - would set intensity", android.widget.Toast.LENGTH_SHORT).show()
                        if (selectedMood != mood) {
                            selectMood(mood)
                        }
                        selectIntensity(index + 1, dots)
                    }
                }
            }
        }
    }
    
    private fun updateCurrentDateTime() {
        val dateFormat = SimpleDateFormat("EEEE, h:mm a", Locale.getDefault())
        val currentDateTime = dateFormat.format(Date())
        
        findViewById<TextView>(R.id.tv_current_datetime).text = currentDateTime
    }
    
    private fun initializeDotsToDefault() {
        // Set all dots to default state (3 active dots)
        val allDots = listOf(calmDots, irritatedDots, frustratedDots, angryDots)
        
        allDots.forEach { dots ->
            dots.forEachIndexed { index, dot ->
                if (index < 3) {
                    dot.setBackgroundResource(R.drawable.dot_active)
                } else {
                    dot.setBackgroundResource(R.drawable.dot_inactive)
                }
            }
        }
    }
    
    private fun navigateToCalmSpace() {
        android.widget.Toast.makeText(this, "Opening Calm Space...", android.widget.Toast.LENGTH_SHORT).show()
        val intent = Intent(this, CalmSpaceActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToMediumAnger() {
        try {
            android.widget.Toast.makeText(this, "Opening Medium Anger Space...", android.widget.Toast.LENGTH_SHORT).show()
            val intent = Intent(this, medium_anger::class.java)
            startActivity(intent)
            android.widget.Toast.makeText(this, "Intent started successfully", android.widget.Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "Error navigating to Medium Anger: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    private fun selectMood(mood: String) {
        selectedMood = mood
        
        // Reset all cards to default state
        resetAllCards()
        
        // Highlight selected card
        when (mood) {
            "Calm" -> highlightCard(R.id.card_calm)
            "Irritated" -> highlightCard(R.id.card_irritated)
            "Frustrated" -> highlightCard(R.id.card_frustrated)
            "Angry" -> highlightCard(R.id.card_angry)
        }
        
        // Reset intensity to default (3)
        selectedIntensity = 3
        updateIntensityDots()
    }
    
    private fun selectIntensity(intensity: Int, dots: List<View>) {
        selectedIntensity = intensity
        updateIntensityDots()
    }
    
    private fun updateIntensityDots() {
        // Get the current mood's dots
        val currentDots = when (selectedMood) {
            "Calm" -> calmDots
            "Irritated" -> irritatedDots
            "Frustrated" -> frustratedDots
            "Angry" -> angryDots
            else -> return
        }
        
        // Update dot states for the selected mood
        currentDots.forEachIndexed { index, dot ->
            if (index < selectedIntensity) {
                dot.setBackgroundResource(R.drawable.dot_active)
            } else {
                dot.setBackgroundResource(R.drawable.dot_inactive)
            }
        }
        
        // Reset dots for other moods to default state (3 active dots)
        val allDots = listOf(calmDots, irritatedDots, frustratedDots, angryDots)
        val allMoods = listOf("Calm", "Irritated", "Frustrated", "Angry")
        
        allDots.forEachIndexed { moodIndex, dots ->
            if (allMoods[moodIndex] != selectedMood) {
                dots.forEachIndexed { dotIndex, dot ->
                    if (dotIndex < 3) { // Default to 3 active dots
                        dot.setBackgroundResource(R.drawable.dot_active)
                    } else {
                        dot.setBackgroundResource(R.drawable.dot_inactive)
                    }
                }
            }
        }
    }
    
    private fun resetAllCards() {
        // Reset all cards to default background and elevation
        val cards = listOf(R.id.card_calm, R.id.card_irritated, R.id.card_frustrated, R.id.card_angry)
        cards.forEach { cardId ->
            val card = findViewById<LinearLayout>(cardId)
            card.setBackgroundResource(R.drawable.mood_card_background)
            card.elevation = 0f // Reset elevation
        }
    }
    
    private fun highlightCard(cardId: Int) {
        // Add a subtle highlight effect with elevation
        val card = findViewById<LinearLayout>(cardId)
        card.setBackgroundResource(R.drawable.mood_card_background)
        card.elevation = 8f // Add elevation to show selection
    }
    
    private fun showNoteDialog(mood: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_note_input, null)
        val editTextNote = dialogView.findViewById<EditText>(R.id.et_note)
        
        // Pre-fill with existing note if any
        val currentNote = when (mood) {
            "Calm" -> tvNoteCalm.text.toString()
            "Irritated" -> tvNoteIrritated.text.toString()
            "Frustrated" -> tvNoteFrustrated.text.toString()
            "Angry" -> tvNoteAngry.text.toString()
            else -> ""
        }
        
        if (currentNote != "Add note...") {
            editTextNote.setText(currentNote)
        }
        
        AlertDialog.Builder(this)
            .setTitle("Add Note for $mood")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val noteText = editTextNote.text.toString().trim()
                updateNoteText(mood, noteText)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun updateNoteText(mood: String, noteText: String) {
        val displayText = if (noteText.isEmpty()) "Add note..." else noteText
        val textColor = if (noteText.isEmpty()) "#9CA3AF" else "#1F2937"
        
        when (mood) {
            "Calm" -> {
                tvNoteCalm.text = displayText
                tvNoteCalm.setTextColor(android.graphics.Color.parseColor(textColor))
            }
            "Irritated" -> {
                tvNoteIrritated.text = displayText
                tvNoteIrritated.setTextColor(android.graphics.Color.parseColor(textColor))
            }
            "Frustrated" -> {
                tvNoteFrustrated.text = displayText
                tvNoteFrustrated.setTextColor(android.graphics.Color.parseColor(textColor))
            }
            "Angry" -> {
                tvNoteAngry.text = displayText
                tvNoteAngry.setTextColor(android.graphics.Color.parseColor(textColor))
            }
        }
        
        // Store the note for the selected mood
        if (selectedMood == mood) {
            selectedNote = noteText
        }
    }
    
    private fun saveMood() {
        if (selectedMood == null) {
            android.widget.Toast.makeText(this, "Please select a mood first", android.widget.Toast.LENGTH_SHORT).show()
            return
        }
        
        // Get the note for the selected mood
        val noteText = when (selectedMood) {
            "Calm" -> if (tvNoteCalm.text != "Add note...") tvNoteCalm.text.toString() else ""
            "Irritated" -> if (tvNoteIrritated.text != "Add note...") tvNoteIrritated.text.toString() else ""
            "Frustrated" -> if (tvNoteFrustrated.text != "Add note...") tvNoteFrustrated.text.toString() else ""
            "Angry" -> if (tvNoteAngry.text != "Add note...") tvNoteAngry.text.toString() else ""
            else -> ""
        }
        
        // Save mood to SharedPreferences
        saveMoodToStorage(selectedMood!!, selectedIntensity, noteText)
        
        // Show success message
        android.widget.Toast.makeText(this, "Mood saved: $selectedMood (Intensity: $selectedIntensity)", android.widget.Toast.LENGTH_SHORT).show()
        
        // Navigate to mood history
        val intent = Intent(this, MoodHistoryActivity::class.java)
        startActivity(intent)
        finish()
    }
    
    private fun saveMoodToStorage(mood: String, intensity: Int, note: String) {
        val sharedPreferences = getSharedPreferences("mood_tracking", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        
        // Get current count
        val currentCount = sharedPreferences.getInt("mood_entries_count", 0)
        
        // Save the new mood entry
        editor.putString("mood_type_$currentCount", mood)
        editor.putInt("mood_intensity_$currentCount", intensity)
        editor.putString("mood_note_$currentCount", note)
        editor.putLong("mood_timestamp_$currentCount", System.currentTimeMillis())
        
        // Increment count
        editor.putInt("mood_entries_count", currentCount + 1)
        
        // Apply changes
        editor.apply()
        
        android.util.Log.d("TrackMood", "Saved mood entry #$currentCount: $mood (intensity: $intensity, note: $note)")
    }
} 