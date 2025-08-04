package com.example.anger_management

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NaturalSoundsActivity : AppCompatActivity() {
    
    private lateinit var soundsAdapter: SoundsAdapter
    private var currentPlayingSound: NaturalSound? = null
    private var isPlaying = false
    
    private val naturalSounds = listOf(
        NaturalSound("Ocean Waves", "Pacific Coast", "10:00", "Gentle ocean waves for deep relaxation", "https://www.youtube.com/watch?v=1ZYbU82GVz4"),
        NaturalSound("Forest Rain", "Amazon Rainforest", "8:30", "Soothing rain sounds in the forest", "https://www.youtube.com/watch?v=mPZkdNFkNps"),
        NaturalSound("Mountain Stream", "Alpine Creek", "12:15", "Flowing water for mental clarity", "https://www.youtube.com/watch?v=8HYLyuJZKno"),
        NaturalSound("Thunderstorm", "Distant Storm", "15:45", "Powerful thunder for emotional release", "https://www.youtube.com/watch?v=2f5m80dTTAY"),
        NaturalSound("Birdsong", "Morning Garden", "6:20", "Cheerful birds for uplifting mood", "https://www.youtube.com/watch?v=WPPPFqsECz0"),
        NaturalSound("Waterfall", "Cascade Falls", "9:45", "White noise for focus and calm", "https://www.youtube.com/watch?v=SEfs5TJZ6Nk"),
        NaturalSound("Wind Through Trees", "Pine Forest", "7:30", "Gentle wind for stress relief", "https://www.youtube.com/watch?v=9z8YhL3v6qI"),
        NaturalSound("Creek Water", "Mountain Brook", "11:20", "Babbling brook for tranquility", "https://www.youtube.com/watch?v=7QdpyO7ZFRw"),
        NaturalSound("Distant Thunder", "Storm Approaching", "13:15", "Low thunder for grounding", "https://www.youtube.com/watch?v=3NycM9lYdRI"),
        NaturalSound("Morning Birds", "Dawn Chorus", "5:45", "Early morning bird songs", "https://www.youtube.com/watch?v=6p_yaNFSYIY"),
        NaturalSound("River Flow", "Mountain River", "14:30", "Steady river flow for meditation", "https://www.youtube.com/watch?v=1A6C9HJJM2g"),
        NaturalSound("Light Rain", "Garden Rain", "8:15", "Soft rain for gentle relaxation", "https://www.youtube.com/watch?v=4EaMJOo1jks"),
        NaturalSound("Forest Ambience", "Deep Woods", "16:20", "Complete forest atmosphere", "https://www.youtube.com/watch?v=86m4RC_ADEY"),
        NaturalSound("Ocean Surf", "Beach Waves", "12:45", "Powerful ocean surf sounds", "https://www.youtube.com/watch?v=O-6f5wQXSu8"),
        NaturalSound("Mountain Wind", "Alpine Breeze", "9:30", "High altitude wind sounds", "https://www.youtube.com/watch?v=11TcRw3WgHg"),
        NaturalSound("Stream Flow", "Rocky Stream", "10:15", "Clear mountain stream", "https://www.youtube.com/watch?v=BVxEEn3w688"),
        NaturalSound("Thunder & Rain", "Stormy Night", "18:30", "Complete storm experience", "https://www.youtube.com/watch?v=5GSeWdjyr1c"),
        NaturalSound("Garden Birds", "Backyard Sanctuary", "7:45", "Domestic garden birds", "https://www.youtube.com/watch?v=8jLoDghLxFA"),
        NaturalSound("Waterfall Pool", "Cascade Basin", "11:50", "Waterfall with pool sounds", "https://www.youtube.com/watch?v=z6X5oEIg6Ak"),
        NaturalSound("Pine Forest", "Evergreen Woods", "13:40", "Pine tree wind sounds", "https://www.youtube.com/watch?v=inpok4MKVLM"),
        NaturalSound("Beach Waves", "Sandy Shore", "15:20", "Gentle beach waves", "https://www.youtube.com/watch?v=2f5m80dTTAY"),
        NaturalSound("Mountain Creek", "Alpine Stream", "8:50", "High mountain creek", "https://www.youtube.com/watch?v=WPPPFqsECz0"),
        NaturalSound("Distant Storm", "Horizon Thunder", "14:15", "Far away thunder", "https://www.youtube.com/watch?v=SEfs5TJZ6Nk"),
        NaturalSound("Forest Birds", "Woodland Chorus", "6:35", "Forest bird community", "https://www.youtube.com/watch?v=9z8YhL3v6qI"),
        NaturalSound("River Rapids", "White Water", "12:30", "Fast flowing river", "https://www.youtube.com/watch?v=7QdpyO7ZFRw"),
        NaturalSound("Gentle Rain", "Window Rain", "9:20", "Rain against window", "https://www.youtube.com/watch?v=3NycM9lYdRI"),
        NaturalSound("Ocean Tides", "Coastal Tides", "16:45", "Tidal ocean sounds", "https://www.youtube.com/watch?v=6p_yaNFSYIY"),
        NaturalSound("Mountain Echo", "Alpine Valley", "10:40", "Echoing mountain sounds", "https://www.youtube.com/watch?v=1A6C9HJJM2g"),
        NaturalSound("Stream Bubbles", "Bubbling Brook", "7:15", "Bubbling water sounds", "https://www.youtube.com/watch?v=4EaMJOo1jks"),
        NaturalSound("Thunder Echo", "Mountain Thunder", "13:25", "Echoing thunder", "https://www.youtube.com/watch?v=86m4RC_ADEY"),
        NaturalSound("Dawn Birds", "Sunrise Chorus", "5:30", "Early morning awakening", "https://www.youtube.com/watch?v=O-6f5wQXSu8"),
        NaturalSound("River Bend", "Curving River", "11:35", "River around bend", "https://www.youtube.com/watch?v=11TcRw3WgHg"),
        NaturalSound("Rain Drops", "Leaf Rain", "8:40", "Rain on leaves", "https://www.youtube.com/watch?v=BVxEEn3w688"),
        NaturalSound("Ocean Deep", "Deep Sea", "17:10", "Deep ocean sounds", "https://www.youtube.com/watch?v=5GSeWdjyr1c"),
        NaturalSound("Mountain Peak", "Summit Wind", "12:20", "High mountain winds", "https://www.youtube.com/watch?v=8jLoDghLxFA")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_natural_sounds)
        
        // Set up back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
        
        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_sounds)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        soundsAdapter = SoundsAdapter(naturalSounds) { sound ->
            playNaturalSound(sound)
        }
        recyclerView.adapter = soundsAdapter
    }
    
    private fun playNaturalSound(sound: NaturalSound) {
        // Stop any currently playing sound
        stopCurrentSound()
        
        // Show a dialog with sound information and play options
        android.app.AlertDialog.Builder(this)
            .setTitle("🌿 ${sound.title}")
            .setMessage("Location: ${sound.location}\nDuration: ${sound.duration}\nDescription: ${sound.description}\n\nThis natural sound is designed to help you manage anger and find inner peace.")
            .setPositiveButton("🎥 Watch Video") { _, _ ->
                openNaturalSoundVideo(sound)
            }
            .setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("📱 Open YouTube") { _, _ ->
                openYouTubeApp(sound)
            }
            .show()
    }
    
    private fun openNaturalSoundVideo(sound: NaturalSound) {
        try {
            // Try to open YouTube app with the video
            val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sound.videoUrl))
            youtubeIntent.putExtra("force_fullscreen", true)
            
            // Check if YouTube is installed
            if (youtubeIntent.resolveActivity(packageManager) != null) {
                startActivity(youtubeIntent)
                android.widget.Toast.makeText(this, "🎥 Opening: ${sound.title}", android.widget.Toast.LENGTH_SHORT).show()
            } else {
                // If YouTube not installed, open in browser
                openInBrowser(sound)
            }
            
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "❌ Error opening video: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    private fun openYouTubeApp(sound: NaturalSound) {
        try {
            // Try to open YouTube app specifically
            val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sound.videoUrl))
            youtubeIntent.setPackage("com.google.android.youtube")
            
            if (youtubeIntent.resolveActivity(packageManager) != null) {
                startActivity(youtubeIntent)
                android.widget.Toast.makeText(this, "📱 Opening YouTube: ${sound.title}", android.widget.Toast.LENGTH_SHORT).show()
            } else {
                openInBrowser(sound)
            }
            
        } catch (e: Exception) {
            openInBrowser(sound)
        }
    }
    
    private fun openInBrowser(sound: NaturalSound) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sound.videoUrl))
            startActivity(browserIntent)
            android.widget.Toast.makeText(this, "🌐 Opening in browser: ${sound.title}", android.widget.Toast.LENGTH_SHORT).show()
            
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "❌ Error opening browser: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    private fun stopCurrentSound() {
        isPlaying = false
        currentPlayingSound = null
    }
    
    data class NaturalSound(
        val title: String,
        val location: String,
        val duration: String,
        val description: String,
        val videoUrl: String
    )
    
    inner class SoundsAdapter(
        private val sounds: List<NaturalSound>,
        private val onSoundClick: (NaturalSound) -> Unit
    ) : RecyclerView.Adapter<SoundsAdapter.SoundViewHolder>() {
        
        inner class SoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvTitle: TextView = itemView.findViewById(R.id.tv_sound_title)
            private val tvLocation: TextView = itemView.findViewById(R.id.tv_sound_location)
            private val tvDuration: TextView = itemView.findViewById(R.id.tv_sound_duration)
            private val cardView: CardView = itemView.findViewById(R.id.card_sound)
            
            fun bind(sound: NaturalSound) {
                tvTitle.text = sound.title
                tvLocation.text = sound.location
                tvDuration.text = sound.duration
                
                cardView.setOnClickListener {
                    onSoundClick(sound)
                }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundViewHolder {
            val view = layoutInflater.inflate(R.layout.item_natural_sound, parent, false)
            return SoundViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: SoundViewHolder, position: Int) {
            holder.bind(sounds[position])
        }
        
        override fun getItemCount(): Int = sounds.size
    }
    
    override fun onDestroy() {
        super.onDestroy()
        stopCurrentSound()
    }
} 