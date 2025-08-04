package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class CalmSpaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calm_space)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        // Views are already initialized in the layout
    }

    private fun setupClickListeners() {
        // Back button
        findViewById<android.widget.ImageButton>(R.id.btn_back).setOnClickListener {
            finish()
        }

        // Quick Calm card
        findViewById<CardView>(R.id.card_quick_calm).setOnClickListener {
            startQuickCalm()
        }

        // Deep Breathing card
        findViewById<CardView>(R.id.card_deep_breathing).setOnClickListener {
            startDeepBreathing()
        }

        // Ambient Sounds card
        findViewById<CardView>(R.id.card_ambient_sounds).setOnClickListener {
            startAmbientSounds()
        }

        // Guided Meditation card
        findViewById<CardView>(R.id.card_guided_meditation).setOnClickListener {
            startGuidedMeditation()
        }
    }

    private fun startQuickCalm() {
        Toast.makeText(this, "Starting 1-minute Quick Calm exercise...", Toast.LENGTH_SHORT).show()
        // TODO: Implement 1-minute breathing exercise
    }

    private fun startDeepBreathing() {
        val intent = Intent(this, BreatheActivity::class.java)
        startActivity(intent)
    }

    private fun startAmbientSounds() {
        val intent = Intent(this, NaturalSoundsActivity::class.java)
        startActivity(intent)
    }

    private fun startGuidedMeditation() {
        val intent = android.content.Intent(this, GuidedMeditationActivity::class.java)
        startActivity(intent)
    }
} 