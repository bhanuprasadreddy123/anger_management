package com.example.anger_management

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class BreatheActivity : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var tvBreathingInstruction: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var btnPlayPause: ImageButton
    
    // Tips TextViews
    private lateinit var tvTip1: TextView
    private lateinit var tvTip2: TextView
    private lateinit var tvTip3: TextView
    
    // Progress dots
    private lateinit var dot1: View
    private lateinit var dot2: View
    private lateinit var dot3: View
    private lateinit var dot4: View
    private lateinit var dot5: View
    
    // Emoji containers
    private lateinit var emojiAngry: LinearLayout
    private lateinit var emojiUpset: LinearLayout
    private lateinit var emojiNeutral: LinearLayout
    private lateinit var emojiBetter: LinearLayout
    private lateinit var emojiCalm: LinearLayout
    
    // Timer variables
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false
    private var timeLeftInMillis: Long = 5 * 60 * 1000 // 5 minutes in milliseconds
    
    // Breathing instruction variables
    private var breathingInstructionTimer: CountDownTimer? = null
    private var isInhalePhase = true
    
    // Emoji selection
    private var selectedEmojiIndex = 4 // Calm emoji is selected by default
    private lateinit var emojiContainers: List<LinearLayout>
    
    // Tips for different moods
    private val moodTips = mapOf(
        0 to listOf(
            "• Take deep breaths and count to 10",
            "• Step away from the situation",
            "• Use positive self-talk"
        ),
        1 to listOf(
            "• Practice mindfulness meditation",
            "• Write down your feelings",
            "• Talk to a trusted friend"
        ),
        2 to listOf(
            "• Focus on the present moment",
            "• Practice gratitude",
            "• Take a short walk"
        ),
        3 to listOf(
            "• Continue with positive activities",
            "• Maintain your calm state",
            "• Help others feel better"
        ),
        4 to listOf(
            "• Enjoy this peaceful state",
            "• Practice regular breathing",
            "• Share your calm with others"
        )
    )
    
    // Timer durations for each dot (in minutes)
    private val timerDurations = listOf(5, 10, 15, 20, 25)
    private var currentDotIndex = 0
    
    // Sound player for breathing instructions
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breath)
        
        initializeViews()
        setupClickListeners()
        setupEmojiSelection()
        setupProgressDots()
        updateTipsForMood(selectedEmojiIndex)
        startTimer()
        startBreathingInstructions()
    }

    private fun initializeViews() {
        tvTimer = findViewById(R.id.tvTimer)
        tvBreathingInstruction = findViewById(R.id.tvBreathingInstruction)
        btnBack = findViewById(R.id.btnBack)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        
        // Initialize tips TextViews
        tvTip1 = findViewById(R.id.tvTip1)
        tvTip2 = findViewById(R.id.tvTip2)
        tvTip3 = findViewById(R.id.tvTip3)
        
        // Initialize progress dots
        dot1 = findViewById(R.id.dot1)
        dot2 = findViewById(R.id.dot2)
        dot3 = findViewById(R.id.dot3)
        dot4 = findViewById(R.id.dot4)
        dot5 = findViewById(R.id.dot5)
        
        // Initialize emoji containers
        emojiAngry = findViewById(R.id.emojiAngry)
        emojiUpset = findViewById(R.id.emojiUpset)
        emojiNeutral = findViewById(R.id.emojiNeutral)
        emojiBetter = findViewById(R.id.emojiBetter)
        emojiCalm = findViewById(R.id.emojiCalm)
        
        emojiContainers = listOf(emojiAngry, emojiUpset, emojiNeutral, emojiBetter, emojiCalm)
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            stopTimers()
            finish()
        }
        
        btnPlayPause.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                resumeTimer()
            }
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerDisplay()
            }

            override fun onFinish() {
                isTimerRunning = false
                tvTimer.text = "00:00"
                stopBreathingInstructions()
                btnPlayPause.setImageResource(R.drawable.ic_play)
            }
        }.start()
        isTimerRunning = true
        btnPlayPause.setImageResource(R.drawable.ic_pause)
    }

    private fun updateTimerDisplay() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        val timeFormatted = String.format("%02d:%02d", minutes, seconds)
        tvTimer.text = timeFormatted
    }

    private fun startBreathingInstructions() {
        breathingInstructionTimer = object : CountDownTimer(10000, 10000) { // 10 seconds interval
            override fun onTick(millisUntilFinished: Long) {
                // This will be called every 10 seconds
            }

            override fun onFinish() {
                // Toggle between inhale and exhale
                if (isInhalePhase) {
                    tvBreathingInstruction.text = "Exhale"
                    isInhalePhase = false
                    playBreathingSound()
                } else {
                    tvBreathingInstruction.text = "Inhale"
                    isInhalePhase = true
                    playBreathingSound()
                }
                
                // Restart the timer for the next instruction
                startBreathingInstructions()
            }
        }.start()
    }

    private fun stopBreathingInstructions() {
        breathingInstructionTimer?.cancel()
    }

    private fun stopTimers() {
        countDownTimer?.cancel()
        stopBreathingInstructions()
        stopSound()
    }
    
    private fun pauseTimer() {
        countDownTimer?.cancel()
        breathingInstructionTimer?.cancel()
        isTimerRunning = false
        btnPlayPause.setImageResource(R.drawable.ic_play)
    }
    
    private fun resumeTimer() {
        startTimer()
        startBreathingInstructions()
        btnPlayPause.setImageResource(R.drawable.ic_pause)
    }
    
    private fun setupEmojiSelection() {
        emojiContainers.forEachIndexed { index, container ->
            container.setOnClickListener {
                selectEmoji(index)
            }
        }
    }
    
    private fun selectEmoji(index: Int) {
        // Remove previous selection
        emojiContainers[selectedEmojiIndex].background = null
        emojiContainers[selectedEmojiIndex].setPadding(0, 0, 0, 0)
        
        // Set new selection
        selectedEmojiIndex = index
        emojiContainers[index].background = ContextCompat.getDrawable(this, R.drawable.emoji_selected_bg)
        emojiContainers[index].setPadding(32, 32, 32, 32)
        
        // Save mood selection
        saveMoodSelection(index)
    }

    private fun saveMoodSelection(moodIndex: Int) {
        val moodNames = listOf("Angry", "Upset", "Neutral", "Better", "Calm")
        // TODO: Save mood selection to SharedPreferences or database
        // Example: SharedPreferences.Editor.putString("current_mood", moodNames[moodIndex])
        
        // Update tips for the selected mood
        updateTipsForMood(moodIndex)
    }
    
    private fun updateTipsForMood(moodIndex: Int) {
        val tips = moodTips[moodIndex] ?: return
        if (tips.size >= 3) {
            tvTip1.text = tips[0]
            tvTip2.text = tips[1]
            tvTip3.text = tips[2]
        }
    }
    
    private fun setupProgressDots() {
        val dots = listOf(dot1, dot2, dot3, dot4, dot5)
        dots.forEachIndexed { index, dot ->
            dot.setOnClickListener {
                selectDot(index)
            }
        }
    }
    
    private fun selectDot(index: Int) {
        // Update dot appearance
        val dots = listOf(dot1, dot2, dot3, dot4, dot5)
        dots.forEachIndexed { i, dot ->
            if (i == index) {
                dot.background = ContextCompat.getDrawable(this, R.drawable.progress_dot_active_bg)
            } else {
                dot.background = ContextCompat.getDrawable(this, R.drawable.progress_dot_inactive_bg)
            }
        }
        
        // Update timer duration
        currentDotIndex = index
        val newDuration = timerDurations[index] * 60 * 1000L // Convert to milliseconds
        timeLeftInMillis = newDuration
        
        // Update timer display
        updateTimerDisplay()
        
        // Restart timer if it's running
        if (isTimerRunning) {
            pauseTimer()
            resumeTimer()
        }
    }
    
    private fun playBreathingSound() {
        try {
            // Stop any existing sound
            stopSound()
            
            // Create and play a simple notification sound
            mediaPlayer = MediaPlayer.create(this, android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
            mediaPlayer?.setOnCompletionListener {
                stopSound()
            }
            mediaPlayer?.start()
        } catch (e: Exception) {
            // If system notification sound fails, try a simple beep
            try {
                mediaPlayer = MediaPlayer.create(this, android.provider.Settings.System.DEFAULT_RINGTONE_URI)
                mediaPlayer?.setOnCompletionListener {
                    stopSound()
                }
                mediaPlayer?.start()
            } catch (e2: Exception) {
                // If all else fails, just log the error
                android.util.Log.e("BreatheActivity", "Failed to play breathing sound: ${e2.message}")
            }
        }
    }
    
    private fun stopSound() {
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.stop()
            }
            player.release()
        }
        mediaPlayer = null
    }

override fun onDestroy() {
    super.onDestroy()
    stopTimers()
    stopSound()
}
} 