package com.example.anger_management

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LovingKindnessMeditationActivity : AppCompatActivity() {

    private var toneGenerator: ToneGenerator? = null
    private var isPlaying = false
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 12 * 60 * 1000 // 12 minutes in milliseconds

    private lateinit var tvTimer: TextView
    private lateinit var btnPlayPause: ImageButton
    private lateinit var ivMeditationIcon: ImageView
    private lateinit var tvMeditationText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loving_kindness_meditation)

        initializeViews()
        setupClickListeners()
        updateTimerDisplay()
    }

    private fun initializeViews() {
        tvTimer = findViewById(R.id.tv_timer)
        btnPlayPause = findViewById(R.id.btn_play_pause)
        ivMeditationIcon = findViewById(R.id.iv_meditation_icon)
        tvMeditationText = findViewById(R.id.tv_meditation_text)
    }

    private fun setupClickListeners() {
        // Back button
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            stopMeditation()
            finish()
        }

        // Play/Pause button
        btnPlayPause.setOnClickListener {
            if (isPlaying) {
                pauseMeditation()
            } else {
                startMeditation()
            }
        }
    }

    private fun startMeditation() {
        if (timeLeftInMillis <= 0) {
            timeLeftInMillis = 12 * 60 * 1000 // Reset to 12 minutes
        }

        isPlaying = true
        updatePlayButton(true)

        // Start gentle meditation tones
        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 30)
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 1000)
        } catch (e: Exception) {
            Toast.makeText(this, "Error starting meditation audio", Toast.LENGTH_SHORT).show()
        }

        // Start countdown timer
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerDisplay()
            }

            override fun onFinish() {
                timeLeftInMillis = 0
                updateTimerDisplay()
                stopMeditation()
                Toast.makeText(this@LovingKindnessMeditationActivity, "Loving Kindness meditation completed!", Toast.LENGTH_LONG).show()
            }
        }.start()

        Toast.makeText(this, "Starting Loving Kindness Meditation...", Toast.LENGTH_SHORT).show()
    }

    private fun pauseMeditation() {
        isPlaying = false
        updatePlayButton(false)
        
        countDownTimer?.cancel()
        toneGenerator?.stopTone()
        toneGenerator?.release()
        toneGenerator = null

        Toast.makeText(this, "Meditation paused", Toast.LENGTH_SHORT).show()
    }

    private fun stopMeditation() {
        isPlaying = false
        updatePlayButton(false)
        
        countDownTimer?.cancel()
        countDownTimer = null
        
        toneGenerator?.stopTone()
        toneGenerator?.release()
        toneGenerator = null
    }

    private fun updatePlayButton(isPlaying: Boolean) {
        if (isPlaying) {
            btnPlayPause.setImageResource(R.drawable.ic_pause)
        } else {
            btnPlayPause.setImageResource(R.drawable.ic_play)
        }
    }

    private fun updateTimerDisplay() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        val timeFormatted = String.format("%02d:%02d", minutes, seconds)
        tvTimer.text = timeFormatted
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMeditation()
    }

    override fun onPause() {
        super.onPause()
        // Optionally pause when app goes to background
        // pauseMeditation()
    }
} 