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
        val intent = Intent(this, CalmingMusicActivity::class.java)
        startActivity(intent)
    }
    
    private fun showPhysicalActivityOptions() {
        val options = arrayOf("Quick Walk", "Stretching", "Dance Break", "Jumping Jacks")
        
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Choose Physical Activity")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> startQuickWalk()
                    1 -> startStretching()
                    2 -> startDanceBreak()
                    3 -> startJumpingJacks()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        
        dialog.show()
    }
    
    private fun startQuickWalk() {
        val intent = Intent(this, PhysicalActivityGuideActivity::class.java)
        intent.putExtra("activity_type", "walk")
        intent.putExtra("activity_title", "Quick Walk")
        intent.putExtra("activity_description", "A 5-minute walk to clear your mind and release tension")
        startActivity(intent)
    }
    
    private fun startStretching() {
        val intent = Intent(this, PhysicalActivityGuideActivity::class.java)
        intent.putExtra("activity_type", "stretch")
        intent.putExtra("activity_title", "Stretching")
        intent.putExtra("activity_description", "Gentle stretches to release muscle tension")
        startActivity(intent)
    }
    
    private fun startDanceBreak() {
        val intent = Intent(this, PhysicalActivityGuideActivity::class.java)
        intent.putExtra("activity_type", "dance")
        intent.putExtra("activity_title", "Dance Break")
        intent.putExtra("activity_description", "Move your body to upbeat music for 3 minutes")
        startActivity(intent)
    }
    
    private fun startJumpingJacks() {
        val intent = Intent(this, PhysicalActivityGuideActivity::class.java)
        intent.putExtra("activity_type", "jumping")
        intent.putExtra("activity_title", "Jumping Jacks")
        intent.putExtra("activity_description", "Quick cardio exercise to boost energy and mood")
        startActivity(intent)
    }
}