package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class JournalListActivity : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: View
    private lateinit var btnStartWriting: MaterialButton
    private lateinit var journalAdapter: JournalAdapter
    private lateinit var etSearch: com.google.android.material.textfield.TextInputEditText
    private lateinit var btnClearSearch: MaterialButton
    
    // Sample data - in a real app, this would come from a database
    private val journalEntries = mutableListOf<JournalEntry>()
    private val allJournalEntries = mutableListOf<JournalEntry>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_list)
        
        initializeViews()
        setupClickListeners()
        loadJournalEntries()
        setupRecyclerView()
        updateUI()
    }
    
    override fun onResume() {
        super.onResume()
        // Refresh the list when returning to this activity
        loadJournalEntries()
        journalAdapter.notifyDataSetChanged()
        updateUI()
    }
    
    private fun initializeViews() {
        recyclerView = findViewById(R.id.rv_journals)
        emptyState = findViewById(R.id.empty_state)
        btnStartWriting = findViewById(R.id.btn_start_writing)
        etSearch = findViewById(R.id.et_search)
        btnClearSearch = findViewById(R.id.btn_clear_search)
    }
    
    private fun setupClickListeners() {
        // Back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
        
        // Add new entry button
        findViewById<android.widget.ImageView>(R.id.btn_add_new).setOnClickListener {
            val intent = Intent(this, DailyJournalActivity::class.java)
            startActivity(intent)
        }
        
        // Start writing button
        btnStartWriting.setOnClickListener {
            val intent = Intent(this, DailyJournalActivity::class.java)
            startActivity(intent)
        }
        
        // Search functionality
        etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                filterJournalEntries(s.toString())
            }
        })
        
        // Clear search button
        btnClearSearch.setOnClickListener {
            etSearch.text?.clear()
            filterJournalEntries("")
        }
    }
    
    private fun loadJournalEntries() {
        // Load actual saved journal entries from SharedPreferences
        journalEntries.clear()
        allJournalEntries.clear()
        
        val sharedPreferences = getSharedPreferences("journal_entries", MODE_PRIVATE)
        val entriesCount = sharedPreferences.getInt("entries_count", 0)
        
        for (i in 0 until entriesCount) {
            val text = sharedPreferences.getString("entry_text_$i", "") ?: ""
            val mood = sharedPreferences.getString("entry_mood_$i", "") ?: ""
            val timestamp = sharedPreferences.getLong("entry_timestamp_$i", 0)
            
            if (text.isNotEmpty() && mood.isNotEmpty()) {
                val entry = JournalEntry(
                    text = text,
                    mood = mood,
                    timestamp = java.util.Date(timestamp)
                )
                journalEntries.add(entry)
                allJournalEntries.add(entry)
            }
        }
        
        // Sort entries by timestamp (newest first)
        journalEntries.sortByDescending { it.timestamp }
        allJournalEntries.sortByDescending { it.timestamp }
    }
    
    private fun setupRecyclerView() {
        journalAdapter = JournalAdapter(journalEntries) { journalEntry ->
            // Handle journal entry click - show full entry
            showJournalDetail(journalEntry)
        }
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = journalAdapter
    }
    
    private fun updateUI() {
        if (journalEntries.isEmpty()) {
            emptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
    
    private fun showJournalDetail(journalEntry: JournalEntry) {
        // Navigate to journal detail page
        val intent = Intent(this, JournalDetailActivity::class.java)
        intent.putExtra("journal_text", journalEntry.text)
        intent.putExtra("journal_mood", journalEntry.mood)
        intent.putExtra("journal_date", formatDate(journalEntry.timestamp))
        startActivity(intent)
    }
    
    private fun showDeleteConfirmationDialog(journalEntry: JournalEntry, position: Int) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Delete Journal Entry")
            .setMessage("Are you sure you want to delete this journal entry? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                deleteJournalEntry(journalEntry, position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun deleteJournalEntry(journalEntry: JournalEntry, position: Int) {
        // Find and remove the entry from SharedPreferences
        val sharedPreferences = getSharedPreferences("journal_entries", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        
        val totalEntries = sharedPreferences.getInt("entries_count", 0)
        
        // Find the entry by matching timestamp and text
        for (i in 0 until totalEntries) {
            val storedText = sharedPreferences.getString("entry_text_$i", "") ?: ""
            val storedTimestamp = sharedPreferences.getLong("entry_timestamp_$i", 0)
            
            if (storedText == journalEntry.text && storedTimestamp == journalEntry.timestamp.time) {
                // Remove this entry
                editor.remove("entry_text_$i")
                editor.remove("entry_mood_$i")
                editor.remove("entry_timestamp_$i")
                
                // Shift remaining entries down
                for (j in i until totalEntries - 1) {
                    val nextText = sharedPreferences.getString("entry_text_${j + 1}", "") ?: ""
                    val nextMood = sharedPreferences.getString("entry_mood_${j + 1}", "") ?: ""
                    val nextTimestamp = sharedPreferences.getLong("entry_timestamp_${j + 1}", 0)
                    
                    editor.putString("entry_text_$j", nextText)
                    editor.putString("entry_mood_$j", nextMood)
                    editor.putLong("entry_timestamp_$j", nextTimestamp)
                }
                
                // Remove the last entry (now duplicated)
                editor.remove("entry_text_${totalEntries - 1}")
                editor.remove("entry_mood_${totalEntries - 1}")
                editor.remove("entry_timestamp_${totalEntries - 1}")
                
                // Decrement count
                editor.putInt("entries_count", totalEntries - 1)
                editor.apply()
                
                // Update the list
                journalEntries.removeAt(position)
                journalAdapter.notifyItemRemoved(position)
                updateUI()
                
                android.widget.Toast.makeText(this, "Journal entry deleted", android.widget.Toast.LENGTH_SHORT).show()
                break
            }
        }
    }
    
    private fun formatDate(date: java.util.Date): String {
        val dateFormat = java.text.SimpleDateFormat("EEEE, MMMM d, yyyy", java.util.Locale.getDefault())
        return dateFormat.format(date)
    }
    
    private fun formatTime(date: java.util.Date): String {
        val timeFormat = java.text.SimpleDateFormat("h:mm a", java.util.Locale.getDefault())
        return timeFormat.format(date)
    }
    
    private fun filterJournalEntries(query: String) {
        journalEntries.clear()
        
        if (query.isEmpty()) {
            // Show all entries
            journalEntries.addAll(allJournalEntries)
            btnClearSearch.visibility = android.view.View.GONE
        } else {
            // Filter entries based on query
            val filteredEntries = allJournalEntries.filter { entry ->
                entry.text.contains(query, ignoreCase = true) ||
                entry.mood.contains(query, ignoreCase = true) ||
                formatDate(entry.timestamp).contains(query, ignoreCase = true)
            }
            journalEntries.addAll(filteredEntries)
            btnClearSearch.visibility = android.view.View.VISIBLE
        }
        
        journalAdapter.notifyDataSetChanged()
        updateUI()
    }
    
    // RecyclerView Adapter
    inner class JournalAdapter(
        private val journals: List<JournalEntry>,
        private val onItemClick: (JournalEntry) -> Unit
    ) : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {
        
        inner class JournalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
            private val tvTime: TextView = itemView.findViewById(R.id.tv_time)
            private val tvMood: TextView = itemView.findViewById(R.id.tv_mood)
            private val tvPreview: TextView = itemView.findViewById(R.id.tv_journal_preview)
            private val btnReadMore: TextView = itemView.findViewById(R.id.btn_read_more)
            private val btnDelete: TextView = itemView.findViewById(R.id.btn_delete)
            
            fun bind(journal: JournalEntry) {
                tvDate.text = formatDate(journal.timestamp)
                tvTime.text = formatTime(journal.timestamp)
                tvMood.text = journal.mood
                
                // Set mood badge background
                val moodColor = when (journal.mood) {
                    "Calm" -> R.drawable.mood_badge_calm
                    "Irritated" -> R.drawable.mood_badge_irritated
                    "Frustrated" -> R.drawable.mood_badge_frustrated
                    "Angry" -> R.drawable.mood_badge_angry
                    else -> R.drawable.mood_badge_calm
                }
                tvMood.setBackgroundResource(moodColor)
                
                // Create preview text (first 100 characters)
                val preview = if (journal.text.length > 100) {
                    journal.text.substring(0, 100) + "..."
                } else {
                    journal.text
                }
                tvPreview.text = preview
                
                // Set click listeners
                itemView.setOnClickListener { onItemClick(journal) }
                btnReadMore.setOnClickListener { onItemClick(journal) }
                btnDelete.setOnClickListener { 
                    showDeleteConfirmationDialog(journal, adapterPosition)
                }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_journal_entry, parent, false)
            return JournalViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
            holder.bind(journals[position])
        }
        
        override fun getItemCount(): Int = journals.size
    }
    
    data class JournalEntry(
        val text: String,
        val mood: String,
        val timestamp: java.util.Date
    )
} 