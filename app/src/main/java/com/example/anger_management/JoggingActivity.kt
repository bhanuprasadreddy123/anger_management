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

class JoggingActivity : AppCompatActivity() {
    
    private lateinit var tvTimer: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var btnStart: MaterialButton
    private lateinit var btnPause: MaterialButton
    private lateinit var btnReset: MaterialButton
    
    private var isRunning = false
    private var timeInSeconds = 0
    private val totalTimeInSeconds = 20 * 60 // 20 minutes
    private var handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    
    private val instructions = listOf(
        "Start with a light warm-up walk for 2 minutes",
        "Begin jogging at a comfortable pace",
        "Focus on your breathing - inhale for 3 steps, exhale for 3 steps",
        "Keep your posture upright and relaxed",
        "Swing your arms naturally",
        "Land softly on your feet",
        "Maintain a steady rhythm",
        "You're doing great! Keep going!",
        "Feel the energy flowing through your body",
        "Release any tension as you move",
        "Focus on the rhythm of your movement",
        "You're building strength and releasing stress",
        "Stay hydrated and listen to your body",
        "You're almost halfway there!",
        "Great job maintaining your pace!",
        "Feel the endorphins kicking in",
        "You're releasing built-up energy",
        "Stay focused on your breathing",
        "You're doing amazing! Keep it up!",
        "Almost done! Finish strong!",
        "Excellent work! You've completed your jog!"
    )
    
    private val introductions = listOf(
        "Jogging is a powerful way to release built-up energy and tension. This 20-minute session will help you channel your frustration into positive physical activity.",
        "Transform your anger into energy with this jogging session. As you move, you'll release tension and feel the stress melting away with each step.",
        "Ready to run off that frustration? This jogging session is designed to help you release pent-up energy and return to a calmer state of mind.",
        "Jogging is nature's stress reliever. This session will help you burn off excess energy and find your inner peace through movement.",
        "Turn your anger into momentum with this guided jogging session. You'll feel stronger and more centered with every minute."
    )
    
    private val benefits = listOf(
        "• Releases built-up energy and tension\n• Increases endorphins for natural mood boost\n• Improves cardiovascular health\n• Helps clear your mind and reduce stress\n• Builds physical strength and endurance",
        "• Burns off excess adrenaline and cortisol\n• Improves blood circulation and oxygen flow\n• Enhances mental clarity and focus\n• Strengthens your heart and lungs\n• Creates a natural sense of accomplishment",
        "• Channels frustration into positive energy\n• Boosts your mood with natural endorphins\n• Improves your overall fitness level\n• Helps you process emotions through movement\n• Builds resilience and mental toughness",
        "• Reduces stress hormones in your body\n• Improves sleep quality and energy levels\n• Enhances your mood and outlook\n• Strengthens your immune system\n• Provides a healthy outlet for emotions",
        "• Increases your energy and vitality\n• Improves your mental and emotional balance\n• Strengthens your body and mind\n• Helps you feel more in control\n• Creates lasting positive habits"
    )
    
    private val safetyTips = listOf(
        "• Start slowly and gradually increase pace\n• Stay hydrated throughout your session\n• Listen to your body and stop if needed\n• Wear comfortable, supportive shoes\n• Choose a safe, well-lit area to jog",
        "• Warm up properly before starting\n• Maintain good posture while running\n• Breathe steadily and rhythmically\n• Take breaks if you feel tired\n• Cool down with a light walk afterward",
        "• Check the weather and dress appropriately\n• Run on even surfaces when possible\n• Keep your head up and stay aware\n• Don't push yourself too hard\n• Have a phone with you for emergencies",
        "• Start with a comfortable pace\n• Focus on your breathing pattern\n• Stay in well-populated areas\n• Wear reflective clothing if running at night\n• Listen to your body's signals",
        "• Choose the right time of day for you\n• Make sure you're well-rested before starting\n• Have a clear route planned\n• Stay within your fitness level\n• Enjoy the process and have fun"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogging)
        
        initializeViews()
        setupClickListeners()
        updateTimerDisplay()
        updateInstruction()
        loadDynamicContent()
    }
    
    private fun initializeViews() {
        tvTimer = findViewById(R.id.tv_timer)
        tvInstruction = findViewById(R.id.tv_instruction)
        btnStart = findViewById(R.id.btn_start_jogging)
        btnPause = findViewById(R.id.btn_pause_jogging)
        btnReset = findViewById(R.id.btn_reset_jogging)
        
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
            startJogging()
        }
        
        btnPause.setOnClickListener {
            pauseJogging()
        }
        
        btnReset.setOnClickListener {
            resetJogging()
        }
    }
    
    private fun startJogging() {
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
                            completeJogging()
                        } else {
                            handler.postDelayed(this, 1000)
                        }
                    }
                }
            }
            handler.post(runnable!!)
        }
    }
    
    private fun pauseJogging() {
        isRunning = false
        btnStart.visibility = View.VISIBLE
        btnPause.visibility = View.GONE
        runnable?.let { handler.removeCallbacks(it) }
    }
    
    private fun resetJogging() {
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
        val instructionIndex = (timeInSeconds / 60) % instructions.size
        tvInstruction.text = instructions[instructionIndex]
    }
    
    private fun completeJogging() {
        isRunning = false
        btnStart.visibility = View.VISIBLE
        btnPause.visibility = View.GONE
        
        AlertDialog.Builder(this)
            .setTitle("Jogging Complete! 🏃‍♂️")
            .setMessage("Congratulations! You've completed 20 minutes of jogging. Great job releasing that energy and building your fitness!")
            .setPositiveButton("I feel energized") { _, _ ->
                finish()
            }
            .setNegativeButton("Do more") { dialog, _ ->
                dialog.dismiss()
                resetJogging()
            }
            .setCancelable(false)
            .show()
    }
    
    private fun showExitConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Exit Jogging Session?")
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