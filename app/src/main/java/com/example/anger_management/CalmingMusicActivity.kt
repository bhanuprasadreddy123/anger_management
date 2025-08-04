package com.example.anger_management

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CalmingMusicActivity : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var musicAdapter: MusicAdapter
    private var mediaPlayer: MediaPlayer? = null
    private var currentPlayingSong: Song? = null
    private var isPlaying = false
    private val handler = Handler(Looper.getMainLooper())
    
    private val calmingSongs = listOf(
        Song("Weightless", "Marconi Union", "8:10", "Scientifically proven to reduce anxiety by 65%", "spotify:track:2I825VW8fksM5IMN6ML9LB"),
        Song("Claire de Lune", "Debussy", "5:28", "Classical piano masterpiece for relaxation", "spotify:track:0Qa0d5TK8kH6vRm1q2xUux"),
        Song("River Flows in You", "Yiruma", "3:10", "Beautiful piano melody for inner peace", "spotify:track:7ySbfLwdCwl1EM0zNCJZ38"),
        Song("Gymnopédie No.1", "Erik Satie", "3:21", "Minimalist piano for deep relaxation", "spotify:track:2iAD15fR8PJzXKJ7WZ5o3m"),
        Song("Moonlight Sonata", "Beethoven", "15:28", "Classical masterpiece for emotional balance", "spotify:track:4Cw9MJDXMiKT8zNDo2ifX6"),
        Song("The Sound of Silence", "Disturbed", "4:08", "Powerful cover for emotional release", "spotify:track:1j8z4TTjJ1YOdoFEDwJTQa"),
        Song("Hallelujah", "Jeff Buckley", "6:53", "Soulful ballad for finding peace", "spotify:track:3pRaLNL3b8x5uBOcsgvdqM"),
        Song("Mad World", "Gary Jules", "3:09", "Melancholic beauty for reflection", "spotify:track:3bNv3VuUOKgrf5huOVYjI3"),
        Song("Fix You", "Coldplay", "4:55", "Uplifting melody for healing", "spotify:track:7LVHVU3tWfcxj5aiPFEW4Q"),
        Song("Let It Be", "The Beatles", "3:50", "Timeless wisdom for acceptance", "spotify:track:7iN1s7xHE4ifF5povM6A48"),
        Song("Bridge Over Troubled Water", "Simon & Garfunkel", "4:52", "Comforting melody for support", "spotify:track:6l8EbYRtQMg8ycgT0rwgYp"),
        Song("Imagine", "John Lennon", "3:03", "Vision of peace and harmony", "spotify:track:7pKfPomDEeI4TPT6EOYjn9"),
        Song("What a Wonderful World", "Louis Armstrong", "2:21", "Gratitude and appreciation", "spotify:track:29U7stRjqHU6rMiS8BfaI9"),
        Song("Don't Stop Believin'", "Journey", "4:10", "Hope and perseverance", "spotify:track:4bHsxqR3GMrXTxEPL5v5pY"),
        Song("Lean On Me", "Bill Withers", "4:18", "Support and community", "spotify:track:3M8FzayQWtkvOhqMn2V4T2"),
        Song("You've Got a Friend", "James Taylor", "4:28", "Friendship and support", "spotify:track:1lkqEWM6OnX2DSuYZ6VgXM"),
        Song("The Rose", "Bette Midler", "3:40", "Beauty and growth through adversity", "spotify:track:562JrM9b7jiu8LXiV4SWQF"),
        Song("Both Sides Now", "Joni Mitchell", "4:34", "Perspective and wisdom", "spotify:track:3NW1q4qr6iltg8jIuQf6pp"),
        Song("Fire and Rain", "James Taylor", "3:20", "Processing difficult emotions", "spotify:track:65Firtly2Jj5Xj9J4OvZcM"),
        Song("The Long and Winding Road", "The Beatles", "3:38", "Life's journey and patience", "spotify:track:56bybP0a1F10d4X02w9V6w"),
        Song("Yesterday", "The Beatles", "2:05", "Reflection and acceptance", "spotify:track:3BQHpFgAp4l80e1XslIjNI"),
        Song("Here Comes the Sun", "The Beatles", "3:05", "Hope and new beginnings", "spotify:track:6dGnYIeXmHdcikdzNNDMm2"),
        Song("Blackbird", "The Beatles", "2:18", "Freedom and overcoming obstacles", "spotify:track:5jgFfDIR6FR0gvlA56Nakr"),
        Song("Hey Jude", "The Beatles", "7:11", "Comfort and encouragement", "spotify:track:0aym2LBJBk9DAYuHHutrIl"),
        Song("Let It Go", "Idina Menzel", "3:44", "Release and freedom", "spotify:track:2RttW7RAu5nOAfq6YFvApB"),
        Song("Shake It Off", "Taylor Swift", "3:39", "Letting go of negativity", "spotify:track:5WfhXUtNBWsi6Qt8lTdcjB"),
        Song("Brave", "Sara Bareilles", "3:40", "Courage and authenticity", "spotify:track:6U6uPdlUNJQaIcK6Q0Z1VZ"),
        Song("Roar", "Katy Perry", "3:42", "Finding your voice and strength", "spotify:track:6F5c58TMEs1byxUstkzVeM"),
        Song("Fight Song", "Rachel Platten", "3:22", "Inner strength and determination", "spotify:track:37f4ITSlgPX81ad2EiLVlp"),
        Song("Rise Up", "Andra Day", "4:13", "Resilience and overcoming", "spotify:track:0tMK8IC5UUfUWWv7yG1qur"),
        Song("Stronger", "Kelly Clarkson", "3:41", "Growth through adversity", "spotify:track:0j2T0R9dR9qdJYsB7ciXhf"),
        Song("I Will Survive", "Gloria Gaynor", "3:18", "Survival and strength", "spotify:track:7afCN6I0cUl4PdHiTDyth5"),
        Song("Eye of the Tiger", "Survivor", "4:05", "Determination and focus", "spotify:track:2HHtWyy5CgaQbC7XSoOb0e"),
        Song("We Will Rock You", "Queen", "2:02", "Power and confidence", "spotify:track:54flyrjXDQIMw5XuDBVoAh")
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calming_music)
        
        initializeViews()
        setupRecyclerView()
    }
    
    private fun initializeViews() {
        // Set up back button
        findViewById<View>(R.id.btn_back).setOnClickListener {
            finish()
        }
        
        // Set up header title
        findViewById<TextView>(R.id.tv_header_title).text = "Calming Music"
        findViewById<TextView>(R.id.tv_header_subtitle).text = "25+ motivational songs for anger management"
        
        // Set up stop music button
        findViewById<View>(R.id.btn_stop_music).setOnClickListener {
            stopMusic()
            hideNowPlayingBar()
        }
    }
    
    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_music)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        musicAdapter = MusicAdapter(calmingSongs) { song ->
            // Handle song selection
            playSong(song)
        }
        
        recyclerView.adapter = musicAdapter
    }
    
    private fun playSong(song: Song) {
        // Stop any currently playing music
        stopMusic()
        
        // Show a dialog with song information and play options
        android.app.AlertDialog.Builder(this)
            .setTitle("🎵 ${song.title}")
            .setMessage("Artist: ${song.artist}\nDuration: ${song.duration}\nDescription: ${song.description}\n\nThis song is designed to help you manage anger and find inner peace.")
            .setPositiveButton("🎵 Play on Spotify") { _, _ ->
                startMusicPlayback(song)
            }
            .setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("🎼 Play Calming Music") { _, _ ->
                playFallbackMusic(song)
            }
            .show()
    }
    
    private fun startMusicPlayback(song: Song) {
        try {
            stopMusic() // Stop any existing playback
            
            currentPlayingSong = song
            isPlaying = true
            
            // Show playing status
            android.widget.Toast.makeText(this, "🎵 Now playing: ${song.title}", android.widget.Toast.LENGTH_SHORT).show()
            
            // Show now playing bar
            showNowPlayingBar(song)
            
            // Try to play the actual audio file
            playAudioFile(song)
            
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "❌ Error playing music: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    private fun playAudioFile(song: Song) {
        try {
            // Try to open Spotify with the song
            openSpotifyWithSong(song)
            
        } catch (e: Exception) {
            // If Spotify not available, play fallback calming music
            android.widget.Toast.makeText(this, "📝 Spotify not available, playing calming music...", android.widget.Toast.LENGTH_SHORT).show()
            playFallbackMusic(song)
        }
    }
    
    private fun openSpotifyWithSong(song: Song) {
        try {
            // Create Spotify URI
            val spotifyUri = song.spotifyUri
            
            // Try to open Spotify app with the song
            val spotifyIntent = Intent(Intent.ACTION_VIEW, Uri.parse(spotifyUri))
            spotifyIntent.putExtra("play", true) // Auto-play the song
            
            // Check if Spotify is installed
            if (spotifyIntent.resolveActivity(packageManager) != null) {
                startActivity(spotifyIntent)
                android.widget.Toast.makeText(this, "🎵 Opening Spotify: ${song.title}", android.widget.Toast.LENGTH_SHORT).show()
            } else {
                // If Spotify not installed, open in web browser
                openSpotifyInBrowser(song)
            }
            
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "❌ Error opening Spotify: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
            playFallbackMusic(song)
        }
    }
    
    private fun openSpotifyInBrowser(song: Song) {
        try {
            // Convert Spotify URI to web URL
            val spotifyWebUrl = song.spotifyUri.replace("spotify:track:", "https://open.spotify.com/track/")
            
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(spotifyWebUrl))
            startActivity(browserIntent)
            
            android.widget.Toast.makeText(this, "🌐 Opening Spotify Web: ${song.title}", android.widget.Toast.LENGTH_SHORT).show()
            
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "❌ Error opening Spotify Web: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
            playFallbackMusic(song)
        }
    }
    
    private fun playFallbackMusic(song: Song) {
        try {
            // Create a calming melody using ToneGenerator with musical tones
            startCalmingMelody(song)
            
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "❌ Could not play music: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
    
    private fun startCalmingMelody(song: Song) {
        // Create a calming melody using different tones
        val melodyTones = listOf(
            android.media.ToneGenerator.TONE_DTMF_1, // C note
            android.media.ToneGenerator.TONE_DTMF_2, // D note  
            android.media.ToneGenerator.TONE_DTMF_3, // E note
            android.media.ToneGenerator.TONE_DTMF_4, // F note
            android.media.ToneGenerator.TONE_DTMF_5, // G note
            android.media.ToneGenerator.TONE_DTMF_6, // A note
            android.media.ToneGenerator.TONE_DTMF_7, // B note
            android.media.ToneGenerator.TONE_DTMF_8, // C note (higher)
            android.media.ToneGenerator.TONE_DTMF_9, // D note (higher)
            android.media.ToneGenerator.TONE_DTMF_0, // E note (higher)
            android.media.ToneGenerator.TONE_DTMF_A, // F note (higher)
            android.media.ToneGenerator.TONE_DTMF_B  // G note (higher)
        )
        
        var toneIndex = 0
        val toneGenerator = android.media.ToneGenerator(android.media.AudioManager.STREAM_MUSIC, 30)
        
        val melodyRunnable = object : Runnable {
            override fun run() {
                if (isPlaying && toneIndex < melodyTones.size) {
                    // Play each tone for 300ms with a gentle volume
                    toneGenerator.startTone(melodyTones[toneIndex], 300)
                    toneIndex++
                    handler.postDelayed(this, 400) // Wait 400ms before next tone
                } else if (isPlaying) {
                    // Restart the melody
                    toneIndex = 0
                    handler.postDelayed(this, 800) // Wait 800ms before restarting
                } else {
                    // Stop and release tone generator
                    toneGenerator.release()
                }
            }
        }
        
        handler.post(melodyRunnable)
    }
    
    private fun stopMusic() {
        isPlaying = false
        handler.removeCallbacksAndMessages(null)
        
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.stop()
            }
            player.release()
        }
        mediaPlayer = null
        
        if (currentPlayingSong != null) {
            android.widget.Toast.makeText(this, "⏹️ Stopped playing: ${currentPlayingSong?.title ?: "Music"}", android.widget.Toast.LENGTH_SHORT).show()
        }
        currentPlayingSong = null
        hideNowPlayingBar()
    }
    
    private fun showNowPlayingBar(song: Song) {
        findViewById<View>(R.id.now_playing_bar).visibility = View.VISIBLE
        findViewById<TextView>(R.id.tv_now_playing_title).text = song.title
        findViewById<TextView>(R.id.tv_now_playing_artist).text = song.artist
    }
    
    private fun hideNowPlayingBar() {
        findViewById<View>(R.id.now_playing_bar).visibility = View.GONE
    }
    
    data class Song(
        val title: String,
        val artist: String,
        val duration: String,
        val description: String,
        val spotifyUri: String
    )
    
    inner class MusicAdapter(
        private val songs: List<Song>,
        private val onSongClick: (Song) -> Unit
    ) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {
        
        inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvTitle: TextView = itemView.findViewById(R.id.tv_song_title)
            private val tvArtist: TextView = itemView.findViewById(R.id.tv_song_artist)
            private val tvDuration: TextView = itemView.findViewById(R.id.tv_song_duration)
            private val cardView: CardView = itemView.findViewById(R.id.card_song)
            
            fun bind(song: Song) {
                tvTitle.text = song.title
                tvArtist.text = song.artist
                tvDuration.text = song.duration
                
                cardView.setOnClickListener {
                    onSongClick(song)
                }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
            val view = layoutInflater.inflate(R.layout.item_song, parent, false)
            return MusicViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
            holder.bind(songs[position])
        }
        
        override fun getItemCount(): Int = songs.size
    }
    
    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }
} 