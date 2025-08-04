package com.example.anger_management

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import android.widget.Button

class FrustrationReliefActivity : AppCompatActivity() {
    
    private var sessionProgress = 25 // 25% progress
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Debug logging
        android.widget.Toast.makeText(this, "FrustrationReliefActivity onCreate started", android.widget.Toast.LENGTH_SHORT).show()
        
        setContentView(R.layout.activity_frustration_relief)
        
        // Confirm activity is opening
        android.widget.Toast.makeText(this, "Frustration Relief Activity Opened!", android.widget.Toast.LENGTH_SHORT).show()
        
        // Debug: Check if views are found
        try {
            val backButton = findViewById<android.widget.ImageView>(R.id.btn_back)
            android.widget.Toast.makeText(this, "Back button found successfully", android.widget.Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "Error finding back button: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
        
        initializeViews()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        // Back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
    }
    
    private fun setupClickListeners() {
        // Daily Quick Relief Button
        findViewById<MaterialButton>(R.id.btn_daily_quick_relief).setOnClickListener {
            val intent = Intent(this, DynamicQuickReliefActivity::class.java)
            startActivity(intent)
        }
        
        // Quick Relief Options
        setupQuickReliefListeners()
        
        // Mental Shift Options
        setupMentalShiftListeners()
    }
    
    private fun setupQuickReliefListeners() {
        // Take a 5-minute walk
        findViewById<LinearLayout>(R.id.quick_relief_container).getChildAt(0).setOnClickListener {
            showActionDialog("Take a 5-minute walk", "Walking helps release tension and clears your mind. Try walking around your room or outside if possible.")
        }
        
        // Stretch or shake your body
        findViewById<LinearLayout>(R.id.quick_relief_container).getChildAt(1).setOnClickListener {
            showActionDialog("Stretch or shake your body", "Physical movement helps release built-up tension. Try gentle stretches or shaking your arms and legs.")
        }
        
        // Sip cold water slowly
        findViewById<LinearLayout>(R.id.quick_relief_container).getChildAt(2).setOnClickListener {
            showActionDialog("Sip cold water slowly", "Cold water can help calm your nervous system. Take small sips and focus on the sensation.")
        }
        
        // Say affirmation aloud
        findViewById<LinearLayout>(R.id.quick_relief_container).getChildAt(3).setOnClickListener {
            showAffirmationDialog()
        }
    }
    
    private fun setupMentalShiftListeners() {
        // Write down 3 things going right
        findViewById<LinearLayout>(R.id.mental_shift_gratitude).setOnClickListener {
            val intent = Intent(this, GratitudeJournalActivity::class.java)
            startActivity(intent)
        }
        
        // 4-4-4 Breathing
        findViewById<LinearLayout>(R.id.mental_shift_breathing).setOnClickListener {
            val intent = Intent(this, BreathingExerciseActivity::class.java)
            startActivity(intent)
        }
        
        // Reframe it
        findViewById<LinearLayout>(R.id.mental_shift_reframe).setOnClickListener {
            val intent = Intent(this, ReframePerspectiveActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun showActionDialog(title: String, description: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(description)
            .setPositiveButton("I'll try this") { _, _ ->
                updateProgress()
            }
            .setNegativeButton("Maybe later") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun showAffirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_affirmation, null)
        val editText = dialogView.findViewById<EditText>(R.id.et_affirmation)
        
        AlertDialog.Builder(this)
            .setTitle("Say Your Affirmation")
            .setView(dialogView)
            .setPositiveButton("Say it aloud") { _, _ ->
                val affirmation = editText.text.toString().ifEmpty { "I'm frustrated, but I'll handle it" }
                showAffirmationConfirmation(affirmation)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun showAffirmationConfirmation(affirmation: String) {
        AlertDialog.Builder(this)
            .setTitle("Say it with confidence!")
            .setMessage("Take a deep breath and say: \"$affirmation\"")
            .setPositiveButton("I said it!") { _, _ ->
                updateProgress()
            }
            .setNegativeButton("Not yet") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun showGratitudeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_gratitude, null)
        val editText1 = dialogView.findViewById<EditText>(R.id.et_gratitude_1)
        val editText2 = dialogView.findViewById<EditText>(R.id.et_gratitude_2)
        val editText3 = dialogView.findViewById<EditText>(R.id.et_gratitude_3)
        
        AlertDialog.Builder(this)
            .setTitle("Write down 3 things going right")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val gratitude1 = editText1.text.toString()
                val gratitude2 = editText2.text.toString()
                val gratitude3 = editText3.text.toString()
                
                if (gratitude1.isNotEmpty() || gratitude2.isNotEmpty() || gratitude3.isNotEmpty()) {
                    showGratitudeConfirmation(gratitude1, gratitude2, gratitude3)
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun showGratitudeConfirmation(g1: String, g2: String, g3: String) {
        val message = buildString {
            append("Your positive aspects:\n\n")
            if (g1.isNotEmpty()) append("1. $g1\n")
            if (g2.isNotEmpty()) append("2. $g2\n")
            if (g3.isNotEmpty()) append("3. $g3\n")
            append("\nFocus on these good things!")
        }
        
        AlertDialog.Builder(this)
            .setTitle("Great job!")
            .setMessage(message)
            .setPositiveButton("I feel better") { _, _ ->
                updateProgress()
            }
            .show()
    }
    
    private fun showBreathingDialog() {
        AlertDialog.Builder(this)
            .setTitle("4-4-4 Breathing Exercise")
            .setMessage("Let's practice together:\n\n" +
                    "1. Inhale slowly for 4 seconds\n" +
                    "2. Hold your breath for 4 seconds\n" +
                    "3. Exhale slowly for 4 seconds\n\n" +
                    "Repeat this cycle 3-5 times.")
            .setPositiveButton("Start breathing") { _, _ ->
                // Could navigate to a breathing exercise activity here
                updateProgress()
            }
            .setNegativeButton("Not now") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun showReframeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_reframe, null)
        val editText = dialogView.findViewById<EditText>(R.id.et_reframe)
        
        AlertDialog.Builder(this)
            .setTitle("Reframe Your Situation")
            .setView(dialogView)
            .setPositiveButton("Reframe it") { _, _ ->
                val reframe = editText.text.toString().ifEmpty { "This is temporary, not permanent" }
                showReframeConfirmation(reframe)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun showReframeConfirmation(reframe: String) {
        AlertDialog.Builder(this)
            .setTitle("New Perspective")
            .setMessage("Your reframe: \"$reframe\"\n\nThis helps you see the situation differently!")
            .setPositiveButton("I see it differently now") { _, _ ->
                updateProgress()
            }
            .show()
    }
    
    private fun updateProgress() {
        sessionProgress += 15
        if (sessionProgress > 100) sessionProgress = 100
        
        // Update progress bar
        val progressBar = findViewById<LinearLayout>(R.id.progress_bar_container)
        val progressFill = progressBar.getChildAt(0) as LinearLayout
        val progressView = progressFill.getChildAt(0) as android.view.View
        
        val layoutParams = progressView.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = sessionProgress / 100f
        progressView.layoutParams = layoutParams
        
        // Update percentage text
        findViewById<TextView>(R.id.tv_progress_percentage).text = "$sessionProgress%"
        
        if (sessionProgress >= 100) {
            showCompletionDialog()
        }
    }
    
    private fun showCompletionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Great job! 🎉")
            .setMessage("You've completed the frustration relief session. You've learned valuable techniques to manage frustration.")
            .setPositiveButton("I feel better") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
} 