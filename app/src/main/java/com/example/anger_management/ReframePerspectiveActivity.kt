package com.example.anger_management

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class ReframePerspectiveActivity : AppCompatActivity() {
    
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etCurrentSituation: EditText
    private lateinit var etCustomReframe: EditText
    private lateinit var customReframeContainer: LinearLayout
    private lateinit var tvReframeTips: TextView
    
    private var selectedReframe: String = ""
    private var selectedReframeType: String = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reframe_perspective)
        
        sharedPreferences = getSharedPreferences("ReframePerspective", Context.MODE_PRIVATE)
        
        initializeViews()
        setupClickListeners()
        loadSavedReframe()
        loadDynamicTips()
    }
    
    private fun initializeViews() {
        etCurrentSituation = findViewById(R.id.et_current_situation)
        etCustomReframe = findViewById(R.id.et_custom_reframe)
        customReframeContainer = findViewById(R.id.custom_reframe_container)
        tvReframeTips = findViewById<TextView>(R.id.tv_reframe_tips)
        
        // Add text change listener to enable save button when situation is filled
        etCurrentSituation.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val hasSituation = s.toString().trim().isNotEmpty()
                val hasReframe = selectedReframe.isNotEmpty()
                findViewById<MaterialButton>(R.id.btn_save_reframe).isEnabled = hasSituation && hasReframe
            }
        })
    }
    
    private fun setupClickListeners() {
        // Back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
        
        // Reframe option 1: Temporary vs Permanent
        findViewById<LinearLayout>(R.id.reframe_option_1).setOnClickListener {
            selectReframe("This is temporary, not permanent", "temporary")
        }
        
        // Reframe option 2: Learning Opportunity
        findViewById<LinearLayout>(R.id.reframe_option_2).setOnClickListener {
            selectReframe("This is a learning opportunity", "learning")
        }
        
        // Reframe option 3: Not Personal
        findViewById<LinearLayout>(R.id.reframe_option_3).setOnClickListener {
            selectReframe("This isn't about me personally", "not_personal")
        }
        
        // Reframe option 4: Custom Reframe
        findViewById<LinearLayout>(R.id.reframe_option_4).setOnClickListener {
            selectCustomReframe()
        }
        
        // Save button
        findViewById<MaterialButton>(R.id.btn_save_reframe).setOnClickListener {
            saveReframe()
        }
        
        // Share button
        findViewById<MaterialButton>(R.id.btn_share_reframe).setOnClickListener {
            shareReframe()
        }
    }
    
    private fun selectReframe(reframe: String, type: String) {
        selectedReframe = reframe
        selectedReframeType = type
        customReframeContainer.visibility = View.GONE
        
        // Visual feedback
        android.widget.Toast.makeText(this, "Selected: $reframe", android.widget.Toast.LENGTH_SHORT).show()
        
        // Enable save button if situation is also filled
        val currentSituation = etCurrentSituation.text.toString().trim()
        if (currentSituation.isNotEmpty()) {
            findViewById<MaterialButton>(R.id.btn_save_reframe).isEnabled = true
        }
    }
    
    private fun selectCustomReframe() {
        selectedReframeType = "custom"
        customReframeContainer.visibility = View.VISIBLE
        etCustomReframe.requestFocus()
    }
    
    private fun loadSavedReframe() {
        try {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val savedDate = sharedPreferences.getString("last_saved_date", "")
            
            if (savedDate == today) {
                // Load today's saved reframe
                etCurrentSituation.setText(sharedPreferences.getString("current_situation", ""))
                selectedReframeType = sharedPreferences.getString("reframe_type", "") ?: ""
                
                when (selectedReframeType) {
                    "temporary" -> selectedReframe = "This is temporary, not permanent"
                    "learning" -> selectedReframe = "This is a learning opportunity"
                    "not_personal" -> selectedReframe = "This isn't about me personally"
                    "custom" -> {
                        selectedReframe = sharedPreferences.getString("custom_reframe", "") ?: ""
                        etCustomReframe.setText(selectedReframe)
                        customReframeContainer.visibility = View.VISIBLE
                    }
                }
                
                // Show a message if there's saved data
                val hasSavedData = (sharedPreferences.getString("current_situation", "") ?: "").isNotEmpty()
                if (hasSavedData) {
                    android.widget.Toast.makeText(this, "Loaded today's saved reframe", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "Error loading saved reframe: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    private fun saveReframe() {
        val currentSituation = etCurrentSituation.text.toString().trim()
        
        if (currentSituation.isEmpty()) {
            android.widget.Toast.makeText(this, "Please describe what's frustrating you", android.widget.Toast.LENGTH_SHORT).show()
            return
        }
        
        if (selectedReframeType.isEmpty()) {
            android.widget.Toast.makeText(this, "Please select a reframe option", android.widget.Toast.LENGTH_SHORT).show()
            return
        }
        
        if (selectedReframeType == "custom") {
            selectedReframe = etCustomReframe.text.toString().trim()
            if (selectedReframe.isEmpty()) {
                android.widget.Toast.makeText(this, "Please write your custom reframe", android.widget.Toast.LENGTH_SHORT).show()
                return
            }
        }
        
        try {
            // Save to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("last_saved_date", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
            editor.putString("current_situation", currentSituation)
            editor.putString("reframe_type", selectedReframeType)
            if (selectedReframeType == "custom") {
                editor.putString("custom_reframe", selectedReframe)
            }
            editor.apply()
            
            // Show immediate confirmation
            android.widget.Toast.makeText(this, "Reframe saved successfully! 🔄", android.widget.Toast.LENGTH_SHORT).show()
            
            showReframeConfirmation(currentSituation, selectedReframe)
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "Error saving reframe: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    private fun showReframeConfirmation(situation: String, reframe: String) {
        val message = buildString {
            appendLine("Your situation:")
            appendLine("$situation")
            appendLine()
            appendLine("Your new perspective:")
            appendLine("$reframe")
            appendLine()
            appendLine("Remember: Changing how you see a situation can change how you feel about it.")
        }
        
        AlertDialog.Builder(this)
            .setTitle("Perspective Reframed! 🔄")
            .setMessage(message)
            .setPositiveButton("I see it differently now") { _, _ ->
                updateParentProgress()
                finish()
            }
            .setNegativeButton("Reframe more") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun shareReframe() {
        val currentSituation = etCurrentSituation.text.toString().trim()
        
        if (currentSituation.isEmpty()) {
            android.widget.Toast.makeText(this, "Please describe what's frustrating you first", android.widget.Toast.LENGTH_SHORT).show()
            return
        }
        
        if (selectedReframe.isEmpty()) {
            android.widget.Toast.makeText(this, "Please select or write a reframe option first", android.widget.Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            val shareText = buildString {
                appendLine("My perspective reframe:")
                appendLine()
                appendLine("Situation: $currentSituation")
                appendLine()
                appendLine("New perspective: $selectedReframe")
                appendLine()
                appendLine("Reframing helps me see challenges from a more helpful angle.")
                appendLine()
                appendLine("Shared via Anger Management App")
            }
            
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            
            startActivity(Intent.createChooser(shareIntent, "Share Your Reframe"))
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "Error sharing reframe: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    private fun updateParentProgress() {
        // This could be used to update progress in the parent FrustrationReliefActivity
        android.widget.Toast.makeText(this, "Perspective reframed! Progress updated.", android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun loadDynamicTips() {
        val reframeTips = listOf(
            "• Look for the silver lining\n• Consider what you can control\n• Think about what you're learning\n• Remember your strengths\n• Focus on solutions, not problems",
            "• Ask yourself: 'What's the opportunity here?'\n• Consider the bigger picture\n• Think about what you can learn\n• Remember times you overcame similar challenges\n• Focus on your growth and resilience",
            "• Look for the lesson in the situation\n• Consider what you can influence\n• Think about your coping skills\n• Remember your past successes\n• Focus on what you can do differently",
            "• Ask: 'How can this make me stronger?'\n• Consider what you're gaining\n• Think about your problem-solving abilities\n• Remember your support network\n• Focus on your ability to adapt",
            "• Look for the hidden blessing\n• Consider what you can change\n• Think about your inner resources\n• Remember your values and priorities\n• Focus on what matters most"
        )
        
        val randomTip = reframeTips.random()
        tvReframeTips.text = randomTip
    }
} 