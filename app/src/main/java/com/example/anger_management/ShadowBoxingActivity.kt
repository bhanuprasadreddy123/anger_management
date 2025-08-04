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

class ShadowBoxingActivity : AppCompatActivity() {
    
    private lateinit var tvTimer: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var btnStart: MaterialButton
    private lateinit var btnPause: MaterialButton
    private lateinit var btnReset: MaterialButton
    
    private var isRunning = false
    private var timeInSeconds = 0
    private val totalTimeInSeconds = 5 * 60 // 5 minutes
    private var handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    
    private val instructions = listOf(
        "Stand in a boxing stance with feet shoulder-width apart",
        "Keep your hands up to protect your face",
        "Start with light jabs - extend your left arm forward",
        "Follow with a right cross - rotate your hips",
        "Add some hooks - swing your arms in a circular motion",
        "Mix in uppercuts - punch upward from below",
        "Move around and stay light on your feet",
        "You're doing great! Keep the rhythm going!",
        "Feel the energy flowing through your punches",
        "Focus on your breathing - exhale with each punch",
        "You're releasing built-up tension and frustration",
        "Keep your core engaged and stay balanced",
        "You're almost halfway through!",
        "Great job maintaining your form!",
        "Feel the power in your movements",
        "You're channeling your energy positively",
        "Stay focused and keep punching!",
        "You're doing amazing! Almost done!",
        "Last minute - finish strong!",
        "Excellent work! You've completed your shadow boxing session!"
    )
    
    private val introductions = listOf(
        "Shadow boxing is a powerful way to channel your frustration into controlled movement. This 5-minute session will help you release energy while building confidence.",
        "Transform your anger into power with this shadow boxing session. Each punch will help you feel more in control and confident.",
        "Ready to fight through that frustration? This shadow boxing session is designed to help you release pent-up energy and build self-defense skills.",
        "Shadow boxing is the ultimate stress reliever. This session will help you burn off excess energy and feel empowered through movement.",
        "Turn your anger into strength with this guided shadow boxing session. You'll feel more powerful and centered with every punch."
    )
    
    private val benefits = listOf(
        "• Releases built-up energy and frustration\n• Improves coordination and reflexes\n• Builds confidence and self-defense skills\n• Increases cardiovascular fitness\n• Helps channel anger into positive energy",
        "• Enhances hand-eye coordination\n• Improves footwork and balance\n• Builds mental toughness\n• Increases overall fitness\n• Provides a healthy outlet for emotions",
        "• Channels negative energy into positive action\n• Improves reaction time\n• Enhances body awareness\n• Builds discipline and focus\n• Creates a sense of empowerment",
        "• Increases cardiovascular endurance\n• Improves muscle tone and strength\n• Enhances mental clarity\n• Builds self-confidence\n• Provides stress relief",
        "• Improves overall athleticism\n• Enhances mental and physical coordination\n• Builds both strength and agility\n• Helps you feel more capable\n• Creates lasting positive habits"
    )
    
    private val safetyTips = listOf(
        "• Keep your hands up to protect your face\n• Stay in a safe, open area\n• Don't overextend your punches\n• Breathe steadily throughout\n• Stop if you feel any pain or discomfort",
        "• Maintain proper boxing stance\n• Keep your movements controlled\n• Don't rush through combinations\n• Listen to your body's signals\n• Take breaks when necessary",
        "• Focus on technique over power\n• Keep your guard up at all times\n• Don't lock your joints\n• Warm up properly before starting\n• Cool down with stretching",
        "• Start with basic movements\n• Progress gradually over time\n• Maintain good form throughout\n• Don't push through pain\n• Have enough space around you",
        "• Choose the right intensity for your level\n• Focus on controlled movements\n• Stay hydrated during the session\n• Rest between rounds if needed\n• Enjoy the process and have fun"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shadow_boxing)
        
        initializeViews()
        setupClickListeners()
        updateTimerDisplay()
        updateInstruction()
        loadDynamicContent()
    }
    
    private fun initializeViews() {
        tvTimer = findViewById(R.id.tv_timer)
        tvInstruction = findViewById(R.id.tv_instruction)
        btnStart = findViewById(R.id.btn_start_shadowboxing)
        btnPause = findViewById(R.id.btn_pause_shadowboxing)
        btnReset = findViewById(R.id.btn_reset_shadowboxing)
        
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
            startShadowBoxing()
        }
        
        btnPause.setOnClickListener {
            pauseShadowBoxing()
        }
        
        btnReset.setOnClickListener {
            resetShadowBoxing()
        }
    }
    
    private fun startShadowBoxing() {
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
                            completeShadowBoxing()
                        } else {
                            handler.postDelayed(this, 1000)
                        }
                    }
                }
            }
            handler.post(runnable!!)
        }
    }
    
    private fun pauseShadowBoxing() {
        isRunning = false
        btnStart.visibility = View.VISIBLE
        btnPause.visibility = View.GONE
        runnable?.let { handler.removeCallbacks(it) }
    }
    
    private fun resetShadowBoxing() {
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
        val instructionIndex = (timeInSeconds / 15) % instructions.size // Change every 15 seconds
        tvInstruction.text = instructions[instructionIndex]
    }
    
    private fun completeShadowBoxing() {
        isRunning = false
        btnStart.visibility = View.VISIBLE
        btnPause.visibility = View.GONE
        
        AlertDialog.Builder(this)
            .setTitle("Shadow Boxing Complete! 🥊")
            .setMessage("Congratulations! You've completed 5 minutes of shadow boxing. Great job releasing that energy and building your confidence!")
            .setPositiveButton("I feel powerful") { _, _ ->
                finish()
            }
            .setNegativeButton("Do more") { dialog, _ ->
                dialog.dismiss()
                resetShadowBoxing()
            }
            .setCancelable(false)
            .show()
    }
    
    private fun showExitConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Exit Shadow Boxing Session?")
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