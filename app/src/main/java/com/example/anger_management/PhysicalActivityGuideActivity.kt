package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PhysicalActivityGuideActivity : AppCompatActivity() {
    
    private lateinit var tvActivityTitle: TextView
    private lateinit var tvActivityDescription: TextView
    private lateinit var tvInstructions: TextView
    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button
    private lateinit var btnStop: Button
    private lateinit var btnBack: ImageButton
    
    private var activityType: String = ""
    private var activityTitle: String = ""
    private var activityDescription: String = ""
    private var isTimerRunning = false
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_physical_activity_guide)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        loadActivityData()
        initializeViews()
        setupClickListeners()
        updateUI()
    }
    
    private fun loadActivityData() {
        activityType = intent.getStringExtra("activity_type") ?: "walk"
        activityTitle = intent.getStringExtra("activity_title") ?: "Physical Activity"
        activityDescription = intent.getStringExtra("activity_description") ?: "Get moving to release tension"
    }
    
    private fun initializeViews() {
        tvActivityTitle = findViewById(R.id.tvActivityTitle)
        tvActivityDescription = findViewById(R.id.tvActivityDescription)
        tvInstructions = findViewById(R.id.tvInstructions)
        tvTimer = findViewById(R.id.tvTimer)
        btnStart = findViewById(R.id.btnStart)
        btnPause = findViewById(R.id.btnPause)
        btnStop = findViewById(R.id.btnStop)
        btnBack = findViewById(R.id.btnBack)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            stopTimer()
            finish()
        }
        
        btnStart.setOnClickListener {
            startTimer()
        }
        
        btnPause.setOnClickListener {
            pauseTimer()
        }
        
        btnStop.setOnClickListener {
            stopTimer()
        }
    }
    
    private fun updateUI() {
        tvActivityTitle.text = activityTitle
        tvActivityDescription.text = activityDescription
        
        val instructions = when (activityType) {
            "walk" -> "1. Find a safe place to walk\n2. Start with a slow, steady pace\n3. Focus on your breathing\n4. Notice your surroundings\n5. Walk for 5 minutes"
            "stretch" -> "1. Stand in a comfortable position\n2. Stretch your arms overhead\n3. Bend forward to touch your toes\n4. Stretch your neck gently\n5. Hold each stretch for 10 seconds"
            "dance" -> "1. Put on some upbeat music\n2. Move your body freely\n3. Don't worry about looking perfect\n4. Focus on having fun\n5. Dance for 3 minutes"
            "jumping" -> "1. Stand with feet together\n2. Jump while raising arms\n3. Land softly with feet apart\n4. Jump back to starting position\n5. Do 10 jumping jacks"
            else -> "Follow the instructions and enjoy your activity!"
        }
        
        tvInstructions.text = instructions
        
        // Set initial timer based on activity type
        timeLeftInMillis = when (activityType) {
            "walk" -> 5 * 60 * 1000L // 5 minutes
            "stretch" -> 3 * 60 * 1000L // 3 minutes
            "dance" -> 3 * 60 * 1000L // 3 minutes
            "jumping" -> 2 * 60 * 1000L // 2 minutes
            else -> 3 * 60 * 1000L // 3 minutes default
        }
        
        updateTimerDisplay()
    }
    
    private fun startTimer() {
        if (!isTimerRunning) {
            isTimerRunning = true
            btnStart.visibility = View.GONE
            btnPause.visibility = View.VISIBLE
            
            countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeftInMillis = millisUntilFinished
                    updateTimerDisplay()
                }
                
                override fun onFinish() {
                    isTimerRunning = false
                    timeLeftInMillis = 0
                    updateTimerDisplay()
                    showCompletionDialog()
                }
            }.start()
        }
    }
    
    private fun pauseTimer() {
        if (isTimerRunning) {
            countDownTimer?.cancel()
            isTimerRunning = false
            btnStart.visibility = View.VISIBLE
            btnPause.visibility = View.GONE
        }
    }
    
    private fun stopTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
        timeLeftInMillis = 0
        updateTimerDisplay()
        btnStart.visibility = View.VISIBLE
        btnPause.visibility = View.GONE
    }
    
    private fun updateTimerDisplay() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        tvTimer.text = String.format("%02d:%02d", minutes, seconds)
    }
    
    private fun showCompletionDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Great job! 🎉")
            .setMessage("You've completed your $activityTitle session. How do you feel now?")
            .setPositiveButton("Much Better") { _, _ ->
                finish()
            }
            .setNegativeButton("Still Need Help") { _, _ ->
                // Navigate to additional resources
                val intent = Intent(this, CalmingMusicActivity::class.java)
                startActivity(intent)
                finish()
            }
            .setCancelable(false)
            .show()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }
} 