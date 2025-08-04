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

class AISessionPreferencesActivity : BaseActivity() {
    
    private lateinit var btnBack: ImageButton
    private lateinit var btnSessionDuration: LinearLayout
    private lateinit var btnCalmingTechniques: LinearLayout
    
    // Switches
    private lateinit var switchAutoExtend: SwitchMaterial
    private lateinit var switchBackgroundSounds: SwitchMaterial
    private lateinit var switchSuggestivePrompts: SwitchMaterial
    private lateinit var switchProgressTracking: SwitchMaterial
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ai_session_preferences)
        
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
        btnSessionDuration = findViewById(R.id.btnSessionDuration)
        btnCalmingTechniques = findViewById(R.id.btnCalmingTechniques)
        
        // Switches
        switchAutoExtend = findViewById(R.id.switchAutoExtend)
        switchBackgroundSounds = findViewById(R.id.switchBackgroundSounds)
        switchSuggestivePrompts = findViewById(R.id.switchSuggestivePrompts)
        switchProgressTracking = findViewById(R.id.switchProgressTracking)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnSessionDuration.setOnClickListener {
            showSessionDurationDialog()
        }
        
        btnCalmingTechniques.setOnClickListener {
            showCalmingTechniquesDialog()
        }
    }
    
    private fun setupSwitches() {
        switchAutoExtend.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("auto_extend_sessions", isChecked).apply()
        }
        
        switchBackgroundSounds.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("background_sounds", isChecked).apply()
        }
        
        switchSuggestivePrompts.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("suggestive_prompts", isChecked).apply()
        }
        
        switchProgressTracking.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("progress_tracking", isChecked).apply()
        }
    }
    
    private fun loadSavedSettings() {
        // Load switch states
        switchAutoExtend.isChecked = sharedPreferences.getBoolean("auto_extend_sessions", false)
        switchBackgroundSounds.isChecked = sharedPreferences.getBoolean("background_sounds", true)
        switchSuggestivePrompts.isChecked = sharedPreferences.getBoolean("suggestive_prompts", true)
        switchProgressTracking.isChecked = sharedPreferences.getBoolean("progress_tracking", true)
    }
    
    private fun showSessionDurationDialog() {
        val durations = arrayOf("5 minutes", "10 minutes", "15 minutes", "20 minutes", "25 minutes", "30 minutes")
        val currentDuration = sharedPreferences.getInt("session_duration", 10)
        val currentIndex = (currentDuration / 5) - 1
        
        AlertDialog.Builder(this)
            .setTitle("Session Duration")
            .setSingleChoiceItems(durations, currentIndex) { _, which ->
                val duration = (which + 1) * 5
                sharedPreferences.edit().putInt("session_duration", duration).apply()
                Toast.makeText(this, "Session duration set to ${duration} minutes", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showCalmingTechniquesDialog() {
        val techniques = arrayOf("Breathing Exercises", "Progressive Relaxation", "Mindfulness", "Visualization", "Grounding")
        val selectedTechniques = mutableSetOf<String>()
        
        // Load saved techniques
        if (sharedPreferences.getBoolean("breathing_exercises", true)) selectedTechniques.add("Breathing Exercises")
        if (sharedPreferences.getBoolean("progressive_relaxation", true)) selectedTechniques.add("Progressive Relaxation")
        if (sharedPreferences.getBoolean("mindfulness", true)) selectedTechniques.add("Mindfulness")
        if (sharedPreferences.getBoolean("visualization", false)) selectedTechniques.add("Visualization")
        if (sharedPreferences.getBoolean("grounding", true)) selectedTechniques.add("Grounding")
        
        val checkedItems = BooleanArray(techniques.size) { techniques[it] in selectedTechniques }
        
        AlertDialog.Builder(this)
            .setTitle("Preferred Techniques")
            .setMultiChoiceItems(techniques, checkedItems) { _, which, isChecked ->
                if (isChecked) {
                    selectedTechniques.add(techniques[which])
                } else {
                    selectedTechniques.remove(techniques[which])
                }
            }
            .setPositiveButton("Save") { _, _ ->
                // Save selected techniques
                sharedPreferences.edit().apply {
                    putBoolean("breathing_exercises", "Breathing Exercises" in selectedTechniques)
                    putBoolean("progressive_relaxation", "Progressive Relaxation" in selectedTechniques)
                    putBoolean("mindfulness", "Mindfulness" in selectedTechniques)
                    putBoolean("visualization", "Visualization" in selectedTechniques)
                    putBoolean("grounding", "Grounding" in selectedTechniques)
                }.apply()
                Toast.makeText(this, "Techniques updated", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
} 