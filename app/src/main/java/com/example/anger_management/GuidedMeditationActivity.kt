package com.example.anger_management

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GuidedMeditationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var meditationAdapter: MeditationAdapter
    
    private val guidedMeditations = listOf(
        Meditation("5-Minute Anger Release", "Mindful Movement", "5:12", "Quick meditation to release anger and find calm", "https://www.youtube.com/watch?v=inpok4MKVLM"),
        Meditation("Let Go of Anger", "Calm Mind", "8:45", "Gentle meditation to release negative emotions", "https://www.youtube.com/watch?v=z6X5oEIg6Ak"),
        Meditation("Forgiveness Meditation", "Healing Heart", "12:30", "Learn to forgive yourself and others", "https://www.youtube.com/watch?v=8jLoDghLxFA"),
        Meditation("Stress Relief Breathing", "Breath Work", "6:18", "Deep breathing techniques for immediate relief", "https://www.youtube.com/watch?v=1ZYbU82GVz4"),
        Meditation("Inner Peace Journey", "Zen Garden", "15:22", "Guided journey to find inner peace", "https://www.youtube.com/watch?v=O-6f5wQXSu8"),
        Meditation("Emotional Balance", "Mindful Living", "10:45", "Balance your emotions and find stability", "https://www.youtube.com/watch?v=4EaMJOo1jks"),
        Meditation("Release Tension", "Body Scan", "7:33", "Progressive relaxation for body and mind", "https://www.youtube.com/watch?v=86m4RC_ADEY"),
        Meditation("Calm Your Mind", "Mental Clarity", "9:15", "Clear your mind and find mental peace", "https://www.youtube.com/watch?v=1A6C9HJJM2g"),
        Meditation("Anger Management", "Emotional Control", "11:28", "Learn to manage anger effectively", "https://www.youtube.com/watch?v=3NycM9lYdRI"),
        Meditation("Self-Compassion", "Loving Kindness", "13:42", "Practice self-compassion and self-love", "https://www.youtube.com/watch?v=11TcRw3WgHg"),
        Meditation("Mindful Awareness", "Present Moment", "8:56", "Stay present and aware of your emotions", "https://www.youtube.com/watch?v=6p_yaNFSYIY"),
        Meditation("Healing from Hurt", "Emotional Recovery", "14:18", "Heal emotional wounds and find peace", "https://www.youtube.com/watch?v=2f5m80dTTAY"),
        Meditation("Patience Practice", "Inner Strength", "6:45", "Develop patience and inner strength", "https://www.youtube.com/watch?v=9z8YhL3v6qI"),
        Meditation("Gratitude Meditation", "Thankful Heart", "5:33", "Cultivate gratitude and appreciation", "https://www.youtube.com/watch?v=WPPPFqsECz0"),
        Meditation("Acceptance Practice", "Letting Go", "9:28", "Learn to accept what you cannot change", "https://www.youtube.com/watch?v=BVxEEn3w688"),
        Meditation("Compassion for Others", "Kind Heart", "12:15", "Develop compassion for yourself and others", "https://www.youtube.com/watch?v=5GSeWdjyr1c"),
        Meditation("Mindful Breathing", "Breath Awareness", "7:52", "Focus on your breath for calm", "https://www.youtube.com/watch?v=SEfs5TJZ6Nk"),
        Meditation("Emotional Release", "Let It Flow", "10:18", "Release pent-up emotions safely", "https://www.youtube.com/watch?v=7QdpyO7ZFRw"),
        Meditation("Inner Wisdom", "Intuition", "8:42", "Connect with your inner wisdom", "https://www.youtube.com/watch?v=8HYLyuJZKno"),
        Meditation("Peaceful Mind", "Mental Calm", "11:33", "Find mental peace and tranquility", "https://www.youtube.com/watch?v=1ZYbU82GVz4"),
        Meditation("Stress Relief", "Relaxation", "6:28", "Relieve stress and tension", "https://www.youtube.com/watch?v=z6X5oEIg6Ak"),
        Meditation("Emotional Healing", "Recovery", "13:15", "Heal from emotional pain", "https://www.youtube.com/watch?v=2f5m80dTTAY"),
        Meditation("Mindful Living", "Daily Practice", "9:45", "Integrate mindfulness into daily life", "https://www.youtube.com/watch?v=4EaMJOo1jks"),
        Meditation("Inner Strength", "Resilience", "8:18", "Build inner strength and resilience", "https://www.youtube.com/watch?v=9z8YhL3v6qI"),
        Meditation("Calm Confidence", "Self-Assurance", "7:33", "Develop calm confidence", "https://www.youtube.com/watch?v=1A6C9HJJM2g"),
        Meditation("Mindful Communication", "Better Relationships", "10:52", "Improve communication skills", "https://www.youtube.com/watch?v=5GSeWdjyr1c"),
        Meditation("Emotional Intelligence", "EQ Development", "12:28", "Develop emotional intelligence", "https://www.youtube.com/watch?v=3NycM9lYdRI"),
        Meditation("Stress Management", "Coping Skills", "8:45", "Learn effective stress management", "https://www.youtube.com/watch?v=86m4C9HJJM2g"),
        Meditation("Mindful Decision Making", "Clear Thinking", "9:15", "Make better decisions mindfully", "https://www.youtube.com/watch?v=6p_yaNFSYIY"),
        Meditation("Emotional Regulation", "Self-Control", "11:42", "Learn to regulate your emotions", "https://www.youtube.com/watch?v=7QdpyO7ZFRw"),
        Meditation("Inner Peace", "Tranquility", "14:28", "Find lasting inner peace", "https://www.youtube.com/watch?v=O-6f5wQXSu8"),
        Meditation("Mindful Parenting", "Family Harmony", "13:15", "Parent mindfully and peacefully", "https://www.youtube.com/watch?v=11TcRw3WgHg"),
        Meditation("Workplace Calm", "Professional Peace", "8:33", "Find calm in the workplace", "https://www.youtube.com/watch?v=WPPPFqsECz0"),
        Meditation("Sleep Better", "Restful Mind", "15:45", "Improve sleep with meditation", "https://www.youtube.com/watch?v=BVxEEn3w688"),
        Meditation("Morning Mindfulness", "Start Your Day", "6:18", "Begin your day with mindfulness", "https://www.youtube.com/watch?v=SEfs5TJZ6Nk"),
        Meditation("Evening Relaxation", "End Your Day", "8:52", "Wind down peacefully", "https://www.youtube.com/watch?v=8HYLyuJZKno")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guided_meditation)

        initializeViews()
        setupRecyclerView()
    }
    
    private fun initializeViews() {
        // Set up back button
        findViewById<View>(R.id.btn_back).setOnClickListener {
            finish()
        }

        // Set up header title
        findViewById<TextView>(R.id.tv_header_title).text = "Guided Meditation"
        findViewById<TextView>(R.id.tv_header_subtitle).text = "35+ meditation videos for anger management"
    }
    
    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_meditation)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        meditationAdapter = MeditationAdapter(guidedMeditations) { meditation ->
            playMeditation(meditation)
        }
        
        recyclerView.adapter = meditationAdapter
    }
    
    private fun playMeditation(meditation: Meditation) {
        // Show a dialog with meditation information and play options
        android.app.AlertDialog.Builder(this)
            .setTitle("🧘‍♀️ ${meditation.title}")
            .setMessage("Instructor: ${meditation.instructor}\nDuration: ${meditation.duration}\nDescription: ${meditation.description}\n\nThis guided meditation is designed to help you manage anger and find inner peace.")
            .setPositiveButton("🎥 Watch Video") { _, _ ->
                openMeditationVideo(meditation)
            }
            .setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("📱 Open YouTube") { _, _ ->
                openYouTubeApp(meditation)
            }
            .show()
    }
    
    private fun openMeditationVideo(meditation: Meditation) {
        try {
            // Try to open YouTube app with the video
            val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(meditation.videoUrl))
            youtubeIntent.putExtra("force_fullscreen", true)
            
            // Check if YouTube is installed
            if (youtubeIntent.resolveActivity(packageManager) != null) {
                startActivity(youtubeIntent)
                android.widget.Toast.makeText(this, "🎥 Opening: ${meditation.title}", android.widget.Toast.LENGTH_SHORT).show()
        } else {
                // If YouTube not installed, open in browser
                openInBrowser(meditation)
            }
            
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "❌ Error opening video: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    private fun openYouTubeApp(meditation: Meditation) {
        try {
            // Try to open YouTube app specifically
            val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(meditation.videoUrl))
            youtubeIntent.setPackage("com.google.android.youtube")
            
            if (youtubeIntent.resolveActivity(packageManager) != null) {
                startActivity(youtubeIntent)
                android.widget.Toast.makeText(this, "📱 Opening YouTube: ${meditation.title}", android.widget.Toast.LENGTH_SHORT).show()
        } else {
                openInBrowser(meditation)
            }
            
        } catch (e: Exception) {
            openInBrowser(meditation)
        }
    }
    
    private fun openInBrowser(meditation: Meditation) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(meditation.videoUrl))
            startActivity(browserIntent)
            android.widget.Toast.makeText(this, "🌐 Opening in browser: ${meditation.title}", android.widget.Toast.LENGTH_SHORT).show()
            
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "❌ Error opening browser: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    data class Meditation(
        val title: String,
        val instructor: String,
        val duration: String,
        val description: String,
        val videoUrl: String
    )
    
    inner class MeditationAdapter(
        private val meditations: List<Meditation>,
        private val onMeditationClick: (Meditation) -> Unit
    ) : RecyclerView.Adapter<MeditationAdapter.MeditationViewHolder>() {
        
        inner class MeditationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvTitle: TextView = itemView.findViewById(R.id.tv_meditation_title)
            private val tvInstructor: TextView = itemView.findViewById(R.id.tv_meditation_instructor)
            private val tvDuration: TextView = itemView.findViewById(R.id.tv_meditation_duration)
            private val cardView: CardView = itemView.findViewById(R.id.card_meditation)
            
            fun bind(meditation: Meditation) {
                tvTitle.text = meditation.title
                tvInstructor.text = meditation.instructor
                tvDuration.text = meditation.duration
                
                cardView.setOnClickListener {
                    onMeditationClick(meditation)
                }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeditationViewHolder {
            val view = layoutInflater.inflate(R.layout.item_meditation, parent, false)
            return MeditationViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: MeditationViewHolder, position: Int) {
            holder.bind(meditations[position])
        }
        
        override fun getItemCount(): Int = meditations.size
    }
} 