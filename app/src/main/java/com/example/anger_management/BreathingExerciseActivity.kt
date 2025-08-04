package com.example.anger_management

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class BreathingExerciseActivity : AppCompatActivity() {
    
    private lateinit var breathingCircle: LinearLayout
    private lateinit var tvBreathingInstruction: TextView
    private lateinit var tvBreathingPhase: TextView
    private lateinit var tvCycleCount: TextView
    private lateinit var btnStartBreathing: MaterialButton
    private lateinit var btnPauseBreathing: MaterialButton
    private lateinit var btnResetBreathing: MaterialButton
    private lateinit var breathingProgressFill: View
    private lateinit var tvBreathingProgress: TextView
    private lateinit var tvBreathingBenefits: TextView
    
    private var isBreathingActive = false
    private var currentCycle = 1
    private val totalCycles = 5
    private var currentPhase = 0 // 0: inhale, 1: hold, 2: exhale
    private var currentTime = 0
    private val phaseDuration = 4 // 4 seconds per phase
    
    private val handler = Handler(Looper.getMainLooper())
    private var breathingRunnable: Runnable? = null
    private var scaleAnimator: ValueAnimator? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breathing_exercise)
        
        initializeViews()
        setupClickListeners()
        updateProgress()
        loadDynamicBenefits()
    }
    
    private fun initializeViews() {
        breathingCircle = findViewById(R.id.breathing_circle)
        tvBreathingInstruction = findViewById(R.id.tv_breathing_instruction)
        tvBreathingPhase = findViewById(R.id.tv_breathing_phase)
        tvCycleCount = findViewById(R.id.tv_cycle_count)
        btnStartBreathing = findViewById(R.id.btn_start_breathing)
        btnPauseBreathing = findViewById(R.id.btn_pause_breathing)
        btnResetBreathing = findViewById(R.id.btn_reset_breathing)
        breathingProgressFill = findViewById(R.id.breathing_progress_fill)
        tvBreathingProgress = findViewById(R.id.tv_breathing_progress)
        tvBreathingBenefits = findViewById(R.id.tv_breathing_benefits)
    }
    
    private fun setupClickListeners() {
        // Back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            if (isBreathingActive) {
                showExitConfirmation()
            } else {
                finish()
            }
        }
        
        // Start breathing button
        btnStartBreathing.setOnClickListener {
            startBreathing()
        }
        
        // Pause breathing button
        btnPauseBreathing.setOnClickListener {
            pauseBreathing()
        }
        
        // Reset breathing button
        btnResetBreathing.setOnClickListener {
            resetBreathing()
        }
    }
    
    private fun startBreathing() {
        isBreathingActive = true
        btnStartBreathing.visibility = View.GONE
        btnPauseBreathing.visibility = View.VISIBLE
        
        startBreathingCycle()
    }
    
    private fun pauseBreathing() {
        isBreathingActive = false
        btnStartBreathing.visibility = View.VISIBLE
        btnPauseBreathing.visibility = View.GONE
        
        // Stop animations and timers
        breathingRunnable?.let { handler.removeCallbacks(it) }
        scaleAnimator?.cancel()
        
        tvBreathingInstruction.text = "Paused"
        tvBreathingPhase.text = "Take a moment"
    }
    
    private fun resetBreathing() {
        isBreathingActive = false
        currentCycle = 1
        currentPhase = 0
        currentTime = 0
        
        // Stop animations and timers
        breathingRunnable?.let { handler.removeCallbacks(it) }
        scaleAnimator?.cancel()
        
        // Reset UI
        btnStartBreathing.visibility = View.VISIBLE
        btnPauseBreathing.visibility = View.GONE
        tvBreathingInstruction.text = "Inhale"
        tvBreathingPhase.text = "Inhale for 4 seconds"
        tvCycleCount.text = "Cycle 1 of $totalCycles"
        
        // Reset circle size
        breathingCircle.scaleX = 1.0f
        breathingCircle.scaleY = 1.0f
        
        updateProgress()
    }
    
    private fun startBreathingCycle() {
        breathingRunnable = object : Runnable {
            override fun run() {
                if (!isBreathingActive) return
                
                currentTime++
                
                when (currentPhase) {
                    0 -> { // Inhale
                        if (currentTime <= phaseDuration) {
                            val progress = currentTime.toFloat() / phaseDuration
                            animateCircle(1.0f, 1.3f, progress)
                            tvBreathingPhase.text = "Inhale for 4 seconds"
                        } else {
                            currentPhase = 1
                            currentTime = 0
                            tvBreathingInstruction.text = "Hold"
                            tvBreathingPhase.text = "Hold for 4 seconds"
                        }
                    }
                    1 -> { // Hold
                        if (currentTime <= phaseDuration) {
                            tvBreathingPhase.text = "Hold for 4 seconds"
                        } else {
                            currentPhase = 2
                            currentTime = 0
                            tvBreathingInstruction.text = "Exhale"
                            tvBreathingPhase.text = "Exhale for 4 seconds"
                        }
                    }
                    2 -> { // Exhale
                        if (currentTime <= phaseDuration) {
                            val progress = currentTime.toFloat() / phaseDuration
                            animateCircle(1.3f, 1.0f, progress)
                            tvBreathingPhase.text = "Exhale for 4 seconds"
                        } else {
                            // Complete cycle
                            currentCycle++
                            currentPhase = 0
                            currentTime = 0
                            tvBreathingInstruction.text = "Inhale"
                            tvBreathingPhase.text = "Inhale for 4 seconds"
                            
                            if (currentCycle > totalCycles) {
                                completeBreathingSession()
                                return
                            }
                            
                            tvCycleCount.text = "Cycle $currentCycle of $totalCycles"
                            updateProgress()
                        }
                    }
                }
                
                if (isBreathingActive) {
                    handler.postDelayed(this, 1000) // 1 second intervals
                }
            }
        }
        
        handler.post(breathingRunnable!!)
    }
    
    private fun animateCircle(fromScale: Float, toScale: Float, progress: Float) {
        scaleAnimator?.cancel()
        
        scaleAnimator = ValueAnimator.ofFloat(fromScale, toScale).apply {
            duration = 1000 // 1 second animation
            addUpdateListener { animator ->
                val scale = animator.animatedValue as Float
                breathingCircle.scaleX = scale
                breathingCircle.scaleY = scale
            }
            start()
        }
    }
    
    private fun updateProgress() {
        val progress = ((currentCycle - 1) * 100f / totalCycles).toInt()
        val layoutParams = breathingProgressFill.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = progress / 100f
        breathingProgressFill.layoutParams = layoutParams
        tvBreathingProgress.text = "$progress%"
    }
    
    private fun completeBreathingSession() {
        isBreathingActive = false
        btnStartBreathing.visibility = View.VISIBLE
        btnPauseBreathing.visibility = View.GONE
        
        tvBreathingInstruction.text = "Complete!"
        tvBreathingPhase.text = "Great job!"
        tvCycleCount.text = "Session finished"
        
        // Reset circle size
        breathingCircle.scaleX = 1.0f
        breathingCircle.scaleY = 1.0f
        
        updateProgress()
        
        showCompletionDialog()
    }
    
    private fun showCompletionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Breathing Session Complete! 🌬️")
            .setMessage("You've successfully completed 5 cycles of 4-4-4 breathing. This technique helps calm your nervous system and reduce frustration.")
            .setPositiveButton("I feel calmer") { _, _ ->
                finish()
            }
            .setNegativeButton("Practice more") { dialog, _ ->
                dialog.dismiss()
                resetBreathing()
            }
            .setCancelable(false)
            .show()
    }
    
    private fun showExitConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Exit Breathing Session?")
            .setMessage("Are you sure you want to exit? Your progress will be lost.")
            .setPositiveButton("Exit") { _, _ ->
                finish()
            }
            .setNegativeButton("Continue") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        breathingRunnable?.let { handler.removeCallbacks(it) }
        scaleAnimator?.cancel()
    }
    
    private fun loadDynamicBenefits() {
        val breathingBenefits = listOf(
            "• Activates the parasympathetic nervous system\n• Reduces stress hormones\n• Lowers heart rate and blood pressure\n• Improves focus and concentration\n• Helps manage anxiety and frustration",
            "• Calms your mind and body naturally\n• Increases oxygen flow to your brain\n• Reduces muscle tension\n• Improves emotional regulation\n• Creates a sense of inner peace",
            "• Balances your nervous system\n• Reduces cortisol levels\n• Improves sleep quality\n• Enhances mental clarity\n• Builds resilience to stress",
            "• Promotes relaxation response\n• Reduces fight-or-flight response\n• Improves breathing efficiency\n• Enhances mindfulness\n• Supports emotional well-being",
            "• Creates a pause in your day\n• Helps you respond, not react\n• Improves self-awareness\n• Reduces physical tension\n• Builds stress management skills"
        )
        
        val randomBenefit = breathingBenefits.random()
        tvBreathingBenefits.text = randomBenefit
    }
} 