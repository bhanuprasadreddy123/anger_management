package com.example.anger_management

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.util.*

class DanceActivity : AppCompatActivity() {
    
    private lateinit var tvTimer: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var btnStart: MaterialButton
    private lateinit var btnPause: MaterialButton
    private lateinit var btnReset: MaterialButton
    
    private var isRunning = false
    private var timeInSeconds = 0
    private val totalTimeInSeconds = 10 * 60 // 10 minutes
    private var handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    
    private val instructions = listOf(
        "Put on your favorite upbeat music",
        "Start with simple movements - sway your hips",
        "Move your arms freely to the rhythm",
        "Add some jumping or bouncing movements",
        "Let your body flow naturally with the music",
        "Try some spins or twirls if you have space",
        "Move your feet - step side to side, forward and back",
        "You're doing great! Keep the energy flowing!",
        "Feel the music and let it guide your movements",
        "Express yourself through dance - there's no wrong way!",
        "You're releasing built-up energy and tension",
        "Let your emotions flow through your movements",
        "You're almost halfway through!",
        "Great job keeping the rhythm!",
        "Feel the joy and freedom of movement",
        "You're channeling your energy positively",
        "Keep dancing and having fun!",
        "You're doing amazing! Almost done!",
        "Last few minutes - dance like nobody's watching!",
        "Excellent work! You've completed your dance session!"
    )
    
    private val introductions = listOf(
        "Dance is a beautiful way to express your emotions through movement. This 10-minute session will help you release energy and find joy through free expression.",
        "Transform your emotions into movement with this dance session. Let your body flow and express what words cannot.",
        "Ready to dance through that frustration? This session is designed to help you release pent-up energy and find your rhythm.",
        "Dance is the ultimate form of self-expression. This session will help you burn off excess energy and feel liberated through movement.",
        "Turn your emotions into art with this guided dance session. You'll feel more connected and joyful with every movement."
    )
    
    private val benefits = listOf(
        "• Expresses emotions through movement\n• Releases built-up energy and tension\n• Increases endorphins for natural mood boost\n• Improves coordination and rhythm\n• Helps channel frustration into joy",
        "• Enhances body awareness and control\n• Improves cardiovascular fitness\n• Builds confidence and self-expression\n• Increases flexibility and mobility\n• Provides a creative outlet for emotions",
        "• Channels negative energy into positive action\n• Improves mood and emotional well-being\n• Enhances coordination and balance\n• Builds self-confidence\n• Creates a sense of freedom and joy",
        "• Increases energy and vitality\n• Improves mental and emotional balance\n• Enhances creativity and expression\n• Strengthens the mind-body connection\n• Provides stress relief and relaxation",
        "• Improves overall fitness and health\n• Enhances emotional intelligence\n• Builds both physical and mental strength\n• Helps you feel more alive and connected\n• Creates lasting positive habits"
    )
    
    private val safetyTips = listOf(
        "• Clear the area around you\n• Wear comfortable clothes and shoes\n• Stay hydrated throughout\n• Listen to your body and take breaks\n• Have fun and express yourself freely",
        "• Choose music that makes you feel good\n• Start with simple movements\n• Don't worry about looking perfect\n• Take breaks when you need them\n• Enjoy the process and let go",
        "• Focus on how it feels, not how it looks\n• Move at your own pace\n• Don't push yourself too hard\n• Have enough space to move freely\n• Trust your body's natural rhythm",
        "• Start with familiar movements\n• Progress to more complex moves gradually\n• Maintain good posture\n• Don't compare yourself to others\n• Celebrate your unique style",
        "• Choose the right music for your mood\n• Make sure you're well-rested before starting\n• Have a clear space to dance\n• Stay within your comfort level\n• Enjoy the freedom of movement"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dance)
        
        initializeViews()
        setupClickListeners()
        updateTimerDisplay()
        updateInstruction()
        loadDynamicContent()
    }
    
    private fun initializeViews() {
        tvTimer = findViewById(R.id.tv_timer)
        tvInstruction = findViewById(R.id.tv_instruction)
        btnStart = findViewById(R.id.btn_start_dance)
        btnPause = findViewById(R.id.btn_pause_dance)
        btnReset = findViewById(R.id.btn_reset_dance)
        
        // Set up back button
        findViewById<View>(R.id.btn_back).setOnClickListener {
            if (isRunning) {
                showExitConfirmation()
            } else {
                finish()
            }
        }
    }
    
    private fun setupClickListeners() {
        btnStart.setOnClickListener {
            startDancing()
        }
        
        btnPause.setOnClickListener {
            pauseDancing()
        }
        
        btnReset.setOnClickListener {
            resetDancing()
        }
    }
    
    private fun startDancing() {
        if (!isRunning) {
            isRunning = true
            btnStart.visibility = View.GONE
            btnPause.visibility = View.VISIBLE
            
            runnable = object : Runnable {
                override fun run() {
                    if (isRunning && timeInSeconds < totalTimeInSeconds) {
                        timeInSeconds++
                        updateTimerDisplay()
                        updateInstruction()
                        
                        if (timeInSeconds >= totalTimeInSeconds) {
                            completeDancing()
                        } else {
                            handler.postDelayed(this, 1000)
                        }
                    }
                }
            }
            handler.post(runnable!!)
        }
    }
    
    private fun pauseDancing() {
        isRunning = false
        btnStart.visibility = View.VISIBLE
        btnPause.visibility = View.GONE
        runnable?.let { handler.removeCallbacks(it) }
    }
    
    private fun resetDancing() {
        isRunning = false
        timeInSeconds = 0
        btnStart.visibility = View.VISIBLE
        btnPause.visibility = View.GONE
        runnable?.let { handler.removeCallbacks(it) }
        updateTimerDisplay()
        updateInstruction()
    }
    
    private fun updateTimerDisplay() {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        tvTimer.text = String.format("%02d:%02d", minutes, seconds)
    }
    
    private fun updateInstruction() {
        val instructionIndex = (timeInSeconds / 30) % instructions.size // Change every 30 seconds
        tvInstruction.text = instructions[instructionIndex]
    }
    
    private fun completeDancing() {
        isRunning = false
        btnStart.visibility = View.VISIBLE
        btnPause.visibility = View.GONE
        
        AlertDialog.Builder(this)
            .setTitle("Dance Session Complete! 💃")
            .setMessage("Congratulations! You've completed 10 minutes of free movement. Great job expressing yourself and releasing that energy!")
            .setPositiveButton("I feel joyful") { _, _ ->
                finish()
            }
            .setNegativeButton("Dance more") { dialog, _ ->
                dialog.dismiss()
                resetDancing()
            }
            .setCancelable(false)
            .show()
    }
    
    private fun showExitConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Exit Dance Session?")
            .setMessage("Are you sure you want to exit? Your progress will be lost.")
            .setPositiveButton("Continue") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("Exit") { _, _ ->
                finish()
            }
            .show()
    }
    
    private fun loadDynamicContent() {
        val randomIntro = introductions.random()
        val randomBenefits = benefits.random()
        val randomSafety = safetyTips.random()
        
        // Update the layout with dynamic content
        findViewById<TextView>(R.id.tv_introduction).text = randomIntro
        findViewById<TextView>(R.id.tv_benefits).text = randomBenefits
        findViewById<TextView>(R.id.tv_safety_tips).text = randomSafety
    }
    
    override fun onDestroy() {
        super.onDestroy()
        runnable?.let { handler.removeCallbacks(it) }
    }
} 