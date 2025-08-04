package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class home_page : BaseActivity() {
    
    private lateinit var tvTip1Title: TextView
    private lateinit var tvTip1Description: TextView
    private lateinit var tvTip2Title: TextView
    private lateinit var tvTip2Description: TextView
    private lateinit var cardTip1: CardView
    private lateinit var cardTip2: CardView
    private lateinit var btnGetHelp: LinearLayout
    private lateinit var btnBreatheNow: LinearLayout
    private lateinit var btnChatbot: LinearLayout
    private lateinit var btnTrackMood: LinearLayout
    
    // Current tips for navigation
    private var currentTip1: Tip = Tip("", "")
    private var currentTip2: Tip = Tip("", "")
    
    // Tips data
    private val tips = listOf(
        Tip("5 Quick Calming Techniques", "Simple exercises to help you stay calm in stressful situations."),
        Tip("Take Deep Breaths", "One of the most effective ways to calm down when you're angry is deep breathing."),
        Tip("Count to 10", "When anger rises, count slowly from 1 to 10 before reacting."),
        Tip("Practice Mindfulness", "Focus on the present moment to reduce stress and anger."),
        Tip("Use Positive Self-Talk", "Replace negative thoughts with positive affirmations."),
        Tip("Take a Timeout", "Step away from the situation to give yourself time to cool down."),
        Tip("Exercise Regularly", "Physical activity helps release tension and reduce anger."),
        Tip("Practice Gratitude", "Focus on what you're thankful for to shift your perspective."),
        Tip("Use Progressive Relaxation", "Tense and relax each muscle group to release tension."),
        Tip("Listen to Calming Music", "Music can help soothe your emotions and reduce stress."),
        Tip("Write in a Journal", "Express your feelings through writing to process emotions."),
        Tip("Practice Empathy", "Try to understand others' perspectives to reduce conflict."),
        Tip("Use Visualization", "Imagine a peaceful place to help calm your mind."),
        Tip("Set Healthy Boundaries", "Learn to say no and protect your emotional well-being."),
        Tip("Practice Active Listening", "Focus on understanding others without interrupting.")
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setRandomTips()
        setupClickListeners()
    }
    
    override fun onResume() {
        super.onResume()
        // Refresh tips when returning to this activity
        setRandomTips()
    }
    
    private fun initializeViews() {
        tvTip1Title = findViewById(R.id.tvTip1Title)
        tvTip1Description = findViewById(R.id.tvTip1Description)
        tvTip2Title = findViewById(R.id.tvTip2Title)
        tvTip2Description = findViewById(R.id.tvTip2Description)
        cardTip1 = findViewById(R.id.cardTip1)
        cardTip2 = findViewById(R.id.cardTip2)
        btnGetHelp = findViewById(R.id.btnGetHelp)
        btnBreatheNow = findViewById(R.id.btnBreatheNow)
        btnChatbot = findViewById(R.id.btnChatbot)
        btnTrackMood = findViewById(R.id.btnTrackMood)
    }
    
    private fun setRandomTips() {
        // Get two random tips that are different from each other
        val randomTips = tips.shuffled().take(2)
        
        // Set the first tip
        tvTip1Title.text = randomTips[0].title
        tvTip1Description.text = randomTips[0].description
        
        // Set the second tip
        tvTip2Title.text = randomTips[1].title
        tvTip2Description.text = randomTips[1].description
        
        // Store the tips for navigation
        currentTip1 = randomTips[0]
        currentTip2 = randomTips[1]
    }
    
    private fun setupClickListeners() {
        cardTip1.setOnClickListener {
            navigateToTipDetail(currentTip1)
        }
        
        cardTip2.setOnClickListener {
            navigateToTipDetail(currentTip2)
        }
        
        btnGetHelp.setOnClickListener {
            navigateToSettings()
        }
        
        btnBreatheNow.setOnClickListener {
            android.util.Log.d("HomePage", "Breathe Now button clicked")
            navigateToBreath()
        }
        
        btnChatbot.setOnClickListener {
            android.util.Log.d("HomePage", "Chatbot button clicked")
            navigateToChatbot()
        }
        
        btnTrackMood.setOnClickListener {
            android.util.Log.d("HomePage", "Track Mood button clicked")
            navigateToTrackMood()
        }
    }
    
    private fun navigateToTipDetail(tip: Tip) {
        val intent = Intent(this, TipsDetailActivity::class.java)
        intent.putExtra("tip_title", tip.title)
        intent.putExtra("tip_description", tip.description)
        startActivity(intent)
    }
    
    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToBreath() {
        android.util.Log.d("HomePage", "navigateToBreath() called")
        try {
            android.util.Log.d("HomePage", "Attempting to navigate to BreatheActivity")
            val intent = Intent(this, BreatheActivity::class.java)
            startActivity(intent)
            android.util.Log.d("HomePage", "Successfully started BreatheActivity")
        } catch (e: Exception) {
            android.util.Log.e("HomePage", "Failed to navigate to BreatheActivity: ${e.message}", e)
            // If BreatheActivity fails, try breath activity
            try {
                android.util.Log.d("HomePage", "Attempting to navigate to breath activity")
                val intent = Intent(this, breath::class.java)
                startActivity(intent)
                android.util.Log.d("HomePage", "Successfully started breath activity")
            } catch (e2: Exception) {
                android.util.Log.e("HomePage", "Failed to navigate to breath: ${e2.message}", e2)
            }
        }
    }
    
    private fun navigateToChatbot() {
        android.util.Log.d("HomePage", "navigateToChatbot() called")
        try {
            android.util.Log.d("HomePage", "Attempting to navigate to ChatbotActivityEnhanced")
            val intent = Intent(this, ChatbotActivityEnhanced::class.java)
            startActivity(intent)
            android.util.Log.d("HomePage", "Successfully started ChatbotActivityEnhanced")
        } catch (e: Exception) {
            android.util.Log.e("HomePage", "Failed to navigate to ChatbotActivityEnhanced: ${e.message}", e)
        }
    }
    
    private fun navigateToTrackMood() {
        android.util.Log.d("HomePage", "navigateToTrackMood() called")
        try {
            android.util.Log.d("HomePage", "Attempting to navigate to TrackMoodActivity")
            val intent = Intent(this, TrackMoodActivity::class.java)
            startActivity(intent)
            android.util.Log.d("HomePage", "Successfully started TrackMoodActivity")
        } catch (e: Exception) {
            android.util.Log.e("HomePage", "Failed to navigate to TrackMoodActivity: ${e.message}", e)
        }
    }
    

    
    data class Tip(
        val title: String,
        val description: String
    )
}