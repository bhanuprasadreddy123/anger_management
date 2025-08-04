package com.example.anger_management

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class PushUpsActivity : AppCompatActivity() {
    
    private lateinit var tvRepCounter: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var btnStart: MaterialButton
    private lateinit var btnComplete: MaterialButton
    private lateinit var btnReset: MaterialButton
    
    private var currentRep = 0
    private val totalReps = 20
    private var isActive = false
    
    private val instructions = listOf(
        "Start in a plank position with hands shoulder-width apart",
        "Lower your body until your chest nearly touches the floor",
        "Keep your core tight and body in a straight line",
        "Push back up to the starting position",
        "Breathe out as you push up, breathe in as you lower down",
        "Keep your elbows close to your body",
        "Maintain proper form throughout the exercise",
        "You're doing great! Keep going!",
        "Feel your chest and arm muscles working",
        "Stay focused on your breathing rhythm",
        "You're building strength and releasing tension",
        "Keep your body aligned from head to toes",
        "You're almost halfway there!",
        "Great job maintaining proper form!",
        "Feel the energy flowing through your muscles",
        "You're releasing built-up frustration",
        "Stay strong and keep pushing!",
        "You're doing amazing! Almost done!",
        "Last few reps - finish strong!",
        "Excellent work! You've completed all push-ups!"
    )
    
    private val introductions = listOf(
        "Push-ups are a powerful way to channel your frustration into strength. This 20-rep session will help you build physical power while releasing mental tension.",
        "Transform your anger into muscle with this push-up challenge. Each rep will make you stronger and help you feel more in control.",
        "Ready to push through that frustration? This push-up session is designed to help you release pent-up energy and build confidence.",
        "Push-ups are the ultimate stress reliever. This session will help you burn off excess energy and feel empowered through movement.",
        "Turn your anger into strength with this guided push-up session. You'll feel more powerful and centered with every rep."
    )
    
    private val benefits = listOf(
        "• Builds upper body strength\n• Releases built-up tension and energy\n• Improves core stability\n• Increases endorphins for mood boost\n• Helps channel frustration into strength",
        "• Strengthens chest, arms, and shoulders\n• Improves overall body composition\n• Enhances mental toughness\n• Boosts confidence and self-esteem\n• Provides immediate physical feedback",
        "• Channels negative energy into positive action\n• Improves functional strength\n• Enhances body awareness\n• Builds discipline and focus\n• Creates a sense of accomplishment",
        "• Increases testosterone and growth hormone\n• Improves posture and alignment\n• Strengthens the entire upper body\n• Enhances mental resilience\n• Provides a healthy outlet for emotions",
        "• Builds both physical and mental strength\n• Improves overall fitness level\n• Enhances body control and coordination\n• Helps you feel more capable\n• Creates lasting positive habits"
    )
    
    private val safetyTips = listOf(
        "• Keep your body in a straight line\n• Don't let your hips sag or rise\n• Breathe steadily throughout\n• Stop if you feel pain or discomfort\n• Start with modified push-ups if needed",
        "• Maintain proper hand placement\n• Keep your core engaged\n• Don't rush through the movement\n• Listen to your body's signals\n• Take breaks when necessary",
        "• Focus on quality over quantity\n• Keep your neck neutral\n• Don't lock your elbows at the top\n• Warm up properly before starting\n• Cool down with stretching",
        "• Start with a comfortable number\n• Progress gradually over time\n• Maintain good form throughout\n• Don't push through pain\n• Have a spotter if needed",
        "• Choose the right variation for your level\n• Focus on controlled movements\n• Stay hydrated during the session\n• Rest between sets if doing multiple\n• Enjoy the process and celebrate progress"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push_ups)
        
        initializeViews()
        setupClickListeners()
        updateDisplay()
        loadDynamicContent()
    }
    
    private fun initializeViews() {
        tvRepCounter = findViewById(R.id.tv_rep_counter)
        tvInstruction = findViewById(R.id.tv_instruction)
        btnStart = findViewById(R.id.btn_start_pushups)
        btnComplete = findViewById(R.id.btn_complete_pushup)
        btnReset = findViewById(R.id.btn_reset_pushups)
        
        // Set up back button
        findViewById<View>(R.id.btn_back).setOnClickListener {
            if (isActive) {
                showExitConfirmation()
            } else {
                finish()
            }
        }
    }
    
    private fun setupClickListeners() {
        btnStart.setOnClickListener {
            startPushUps()
        }
        
        btnComplete.setOnClickListener {
            completeRep()
        }
        
        btnReset.setOnClickListener {
            resetPushUps()
        }
    }
    
    private fun startPushUps() {
        isActive = true
        btnStart.visibility = View.GONE
        btnComplete.visibility = View.VISIBLE
        updateInstruction()
    }
    
    private fun completeRep() {
        if (isActive && currentRep < totalReps) {
            currentRep++
            updateDisplay()
            updateInstruction()
            
            if (currentRep >= totalReps) {
                completePushUps()
            }
        }
    }
    
    private fun resetPushUps() {
        isActive = false
        currentRep = 0
        btnStart.visibility = View.VISIBLE
        btnComplete.visibility = View.GONE
        updateDisplay()
        updateInstruction()
    }
    
    private fun updateDisplay() {
        tvRepCounter.text = "$currentRep / $totalReps"
    }
    
    private fun updateInstruction() {
        val instructionIndex = if (currentRep < instructions.size) currentRep else instructions.size - 1
        tvInstruction.text = instructions[instructionIndex]
    }
    
    private fun completePushUps() {
        isActive = false
        btnStart.visibility = View.VISIBLE
        btnComplete.visibility = View.GONE
        
        AlertDialog.Builder(this)
            .setTitle("Push-ups Complete! 💪")
            .setMessage("Congratulations! You've completed 20 push-ups. Great job building strength and releasing that energy!")
            .setPositiveButton("I feel stronger") { _, _ ->
                finish()
            }
            .setNegativeButton("Do more") { dialog, _ ->
                dialog.dismiss()
                resetPushUps()
            }
            .setCancelable(false)
            .show()
    }
    
    private fun showExitConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Exit Push-ups Session?")
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
} 