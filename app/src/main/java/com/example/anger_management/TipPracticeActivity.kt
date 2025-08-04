package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TipPracticeActivity : AppCompatActivity() {
    
    private lateinit var tvTipTitle: TextView
    private lateinit var tvTipDescription: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var cardBreathingExercise: CardView
    private lateinit var cardMuscleRelaxation: CardView
    private lateinit var cardMindfulness: CardView
    private lateinit var cardGratitude: CardView
    private lateinit var cardPhysicalActivity: CardView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tip_practice)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setupClickListeners()
        loadTipDetails()
    }
    
    private fun initializeViews() {
        tvTipTitle = findViewById(R.id.tvTipTitle)
        tvTipDescription = findViewById(R.id.tvTipDescription)
        btnBack = findViewById(R.id.btnBack)
        cardBreathingExercise = findViewById(R.id.cardBreathingExercise)
        cardMuscleRelaxation = findViewById(R.id.cardMuscleRelaxation)
        cardMindfulness = findViewById(R.id.cardMindfulness)
        cardGratitude = findViewById(R.id.cardGratitude)
        cardPhysicalActivity = findViewById(R.id.cardPhysicalActivity)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        cardBreathingExercise.setOnClickListener {
            val intent = Intent(this, BreathingExerciseActivity::class.java)
            startActivity(intent)
        }
        
        cardMuscleRelaxation.setOnClickListener {
            showMuscleRelaxationDialog()
        }
        
        cardMindfulness.setOnClickListener {
            val intent = Intent(this, GuidedMeditationActivity::class.java)
            startActivity(intent)
        }
        
        cardGratitude.setOnClickListener {
            val intent = Intent(this, GratitudeJournalActivity::class.java)
            startActivity(intent)
        }
        
        cardPhysicalActivity.setOnClickListener {
            showPhysicalActivityOptions()
        }
    }
    
    private fun loadTipDetails() {
        val tipTitle = intent.getStringExtra("tip_title") ?: "Tip"
        val tipDescription = intent.getStringExtra("tip_description") ?: "Description"
        
        tvTipTitle.text = "Practice Session"
        tvTipDescription.text = "Based on today's tip: $tipTitle\n\n$tipDescription\n\nChoose an exercise below to start practicing:"
    }
    
    private fun showMuscleRelaxationDialog() {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Progressive Muscle Relaxation")
            .setMessage("This exercise helps you release tension from head to toe.")
            .setPositiveButton("Start Session") { _, _ ->
                // Start guided session
            }
            .setNegativeButton("Cancel", null)
            .create()
        
        dialog.show()
    }
    
    private fun showPhysicalActivityOptions() {
        val options = arrayOf("Quick Walk", "Stretching", "Dance Break", "Jumping Jacks")
        
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Choose Physical Activity")
            .setItems(options) { _, which ->
                // Handle selection
            }
            .setNegativeButton("Cancel", null)
            .create()
        
        dialog.show()
    }
}