package com.example.anger_management

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class MoodHistoryActivity : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: LinearLayout
    private lateinit var moodAdapter: MoodAdapter
    private val moodEntries = mutableListOf<MoodEntry>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_history)
        
        initializeViews()
        setupClickListeners()
        loadMoodEntries()
    }
    
    override fun onResume() {
        super.onResume()
        // Refresh the list when returning to this activity
        loadMoodEntries()
        moodAdapter.notifyDataSetChanged()
        updateUI()
    }
    
    private fun initializeViews() {
        recyclerView = findViewById(R.id.rv_moods)
        emptyState = findViewById(R.id.empty_state)
        
        // Setup RecyclerView
        moodAdapter = MoodAdapter(moodEntries) { moodEntry, position ->
            showDeleteConfirmationDialog(moodEntry, position)
        }
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = moodAdapter
    }
    
    private fun setupClickListeners() {
        // Back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
        
        // Start tracking button
        findViewById<MaterialButton>(R.id.btn_start_tracking).setOnClickListener {
            val intent = Intent(this, TrackMoodActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun loadMoodEntries() {
        moodEntries.clear()
        val sharedPreferences = getSharedPreferences("mood_tracking", MODE_PRIVATE)
        val entriesCount = sharedPreferences.getInt("mood_entries_count", 0)
        
        for (i in 0 until entriesCount) {
            val mood = sharedPreferences.getString("mood_type_$i", "") ?: ""
            val intensity = sharedPreferences.getInt("mood_intensity_$i", 0)
            val note = sharedPreferences.getString("mood_note_$i", "") ?: ""
            val timestamp = sharedPreferences.getLong("mood_timestamp_$i", 0)
            
            if (mood.isNotEmpty()) {
                moodEntries.add(
                    MoodEntry(
                        mood = mood,
                        intensity = intensity,
                        note = note,
                        timestamp = Date(timestamp)
                    )
                )
            }
        }
        
        // Sort by timestamp (newest first)
        moodEntries.sortByDescending { it.timestamp }
    }
    
    private fun updateUI() {
        if (moodEntries.isEmpty()) {
            emptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
    
    private fun showDeleteConfirmationDialog(moodEntry: MoodEntry, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Mood Entry")
            .setMessage("Are you sure you want to delete this mood entry? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                deleteMoodEntry(moodEntry, position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun deleteMoodEntry(moodEntry: MoodEntry, position: Int) {
        // Find and remove the entry from SharedPreferences
        val sharedPreferences = getSharedPreferences("mood_tracking", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        
        val totalEntries = sharedPreferences.getInt("mood_entries_count", 0)
        
        // Find the entry by matching timestamp and mood
        for (i in 0 until totalEntries) {
            val storedMood = sharedPreferences.getString("mood_type_$i", "") ?: ""
            val storedTimestamp = sharedPreferences.getLong("mood_timestamp_$i", 0)
            
            if (storedMood == moodEntry.mood && storedTimestamp == moodEntry.timestamp.time) {
                // Remove this entry
                editor.remove("mood_type_$i")
                editor.remove("mood_intensity_$i")
                editor.remove("mood_note_$i")
                editor.remove("mood_timestamp_$i")
                
                // Shift remaining entries down
                for (j in i until totalEntries - 1) {
                    val nextMood = sharedPreferences.getString("mood_type_${j + 1}", "") ?: ""
                    val nextIntensity = sharedPreferences.getInt("mood_intensity_${j + 1}", 0)
                    val nextNote = sharedPreferences.getString("mood_note_${j + 1}", "") ?: ""
                    val nextTimestamp = sharedPreferences.getLong("mood_timestamp_${j + 1}", 0)
                    
                    editor.putString("mood_type_$j", nextMood)
                    editor.putInt("mood_intensity_$j", nextIntensity)
                    editor.putString("mood_note_$j", nextNote)
                    editor.putLong("mood_timestamp_$j", nextTimestamp)
                }
                
                // Remove the last entry (now duplicated)
                editor.remove("mood_type_${totalEntries - 1}")
                editor.remove("mood_intensity_${totalEntries - 1}")
                editor.remove("mood_note_${totalEntries - 1}")
                editor.remove("mood_timestamp_${totalEntries - 1}")
                
                // Decrement count
                editor.putInt("mood_entries_count", totalEntries - 1)
                editor.apply()
                
                // Update the list
                moodEntries.removeAt(position)
                moodAdapter.notifyItemRemoved(position)
                updateUI()
                
                android.widget.Toast.makeText(this, "Mood entry deleted", android.widget.Toast.LENGTH_SHORT).show()
                break
            }
        }
    }
    
    data class MoodEntry(
        val mood: String,
        val intensity: Int,
        val note: String,
        val timestamp: Date
    )
    
    inner class MoodAdapter(
        private val moods: List<MoodEntry>,
        private val onDeleteClick: (MoodEntry, Int) -> Unit
    ) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {
        
        inner class MoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
            private val tvTime: TextView = itemView.findViewById(R.id.tv_time)
            private val tvMood: TextView = itemView.findViewById(R.id.tv_mood)
            private val tvNote: TextView = itemView.findViewById(R.id.tv_note)
            private val btnDelete: TextView = itemView.findViewById(R.id.btn_delete)
            
            // Dot views
            private val dots = listOf(
                itemView.findViewById<View>(R.id.dot_1),
                itemView.findViewById<View>(R.id.dot_2),
                itemView.findViewById<View>(R.id.dot_3),
                itemView.findViewById<View>(R.id.dot_4),
                itemView.findViewById<View>(R.id.dot_5)
            )
            
            fun bind(mood: MoodEntry) {
                // Format date and time
                val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
                val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
                
                tvDate.text = dateFormat.format(mood.timestamp)
                tvTime.text = timeFormat.format(mood.timestamp)
                tvMood.text = mood.mood
                
                // Set mood badge background
                val badgeBackground = when (mood.mood) {
                    "Calm" -> R.drawable.mood_badge_calm
                    "Irritated" -> R.drawable.mood_badge_irritated
                    "Frustrated" -> R.drawable.mood_badge_frustrated
                    "Angry" -> R.drawable.mood_badge_angry
                    else -> R.drawable.mood_badge_calm
                }
                tvMood.setBackgroundResource(badgeBackground)
                
                // Set intensity dots
                dots.forEachIndexed { index, dot ->
                    if (index < mood.intensity) {
                        dot.setBackgroundResource(R.drawable.dot_active)
                    } else {
                        dot.setBackgroundResource(R.drawable.dot_inactive)
                    }
                }
                
                // Show note if available
                if (mood.note.isNotEmpty()) {
                    tvNote.text = mood.note
                    tvNote.visibility = View.VISIBLE
                } else {
                    tvNote.visibility = View.GONE
                }
                
                // Delete button
                btnDelete.setOnClickListener {
                    onDeleteClick(mood, adapterPosition)
                }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mood_entry, parent, false)
            return MoodViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
            holder.bind(moods[position])
        }
        
        override fun getItemCount(): Int = moods.size
    }
} 