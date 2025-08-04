package com.example.anger_management

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class DynamicQuickReliefActivity : AppCompatActivity() {
    
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var quickReliefItemsContainer: LinearLayout
    private lateinit var tvLastUpdated: TextView
    
    // Pool of quick relief techniques
    private val allQuickReliefTechniques = listOf(
        "Take 10 deep breaths with your eyes closed",
        "Stretch your arms and legs for 2 minutes",
        "Splash cold water on your face",
        "Listen to your favorite calming song",
        "Write down what's bothering you in one sentence",
        "Do 10 jumping jacks to release energy",
        "Call a friend and talk for 5 minutes",
        "Make yourself a cup of tea and sip slowly",
        "Look out the window and name 5 things you see",
        "Tense and relax your muscles from head to toe",
        "Say 'This too shall pass' three times",
        "Draw or doodle for 3 minutes",
        "Take a 5-minute walk around your space",
        "Hug yourself tightly for 30 seconds",
        "Count backwards from 100 by 7s",
        "Think of 3 things you're grateful for right now",
        "Do a quick meditation - focus on your breath",
        "Write a positive message to yourself",
        "Dance to your favorite upbeat song",
        "Practice progressive muscle relaxation",
        "Use a stress ball or squeeze a pillow",
        "Take a mental vacation - imagine your happy place",
        "Do some gentle neck and shoulder stretches",
        "Write down 3 solutions to your current problem",
        "Practice the 4-7-8 breathing technique",
        "Give yourself a pep talk in the mirror",
        "Do some quick yoga poses",
        "Listen to nature sounds for 2 minutes",
        "Write a letter to your future self",
        "Practice mindful eating with a small snack",
        "Do some quick mental math problems",
        "Create a playlist of your favorite songs"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_quick_relief)
        
        sharedPreferences = getSharedPreferences("DynamicQuickRelief", Context.MODE_PRIVATE)
        
        initializeViews()
        setupClickListeners()
        loadOrGenerateQuickReliefItems()
    }
    
    private fun initializeViews() {
        quickReliefItemsContainer = findViewById(R.id.quick_relief_items_container)
        tvLastUpdated = findViewById(R.id.tv_last_updated)
    }
    
    private fun setupClickListeners() {
        // Back button
        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
        
        // Refresh button
        findViewById<android.widget.ImageView>(R.id.btn_refresh).setOnClickListener {
            generateNewQuickReliefItems()
        }
        
        // Customize button
        findViewById<MaterialButton>(R.id.btn_customize).setOnClickListener {
            showCustomizeDialog()
        }
        
        // Share button
        findViewById<MaterialButton>(R.id.btn_share).setOnClickListener {
            shareQuickReliefItems()
        }
    }
    
    private fun loadOrGenerateQuickReliefItems() {
        val lastUpdateDate = sharedPreferences.getString("last_update_date", "")
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        
        if (lastUpdateDate != currentDate) {
            // Generate new items for today
            generateNewQuickReliefItems()
        } else {
            // Load saved items from today
            loadSavedQuickReliefItems()
        }
    }
    
    private fun generateNewQuickReliefItems() {
        val shuffledTechniques = allQuickReliefTechniques.shuffled()
        val selectedTechniques = shuffledTechniques.take(8)
        
        // Save to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("last_update_date", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
        editor.putStringSet("today_techniques", selectedTechniques.toSet())
        editor.apply()
        
        // Update UI
        displayQuickReliefItems(selectedTechniques)
        updateLastUpdatedText()
        
        android.widget.Toast.makeText(this, getString(R.string.new_techniques_generated), android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun loadSavedQuickReliefItems() {
        val savedTechniques = sharedPreferences.getStringSet("today_techniques", null)
        if (savedTechniques != null) {
            displayQuickReliefItems(savedTechniques.toList())
            updateLastUpdatedText()
        } else {
            generateNewQuickReliefItems()
        }
    }
    
    private fun displayQuickReliefItems(techniques: List<String>) {
        quickReliefItemsContainer.removeAllViews()
        
        techniques.forEachIndexed { index, technique ->
            val itemView = createQuickReliefItemView(index + 1, technique)
            quickReliefItemsContainer.addView(itemView)
        }
    }
    
    private fun createQuickReliefItemView(number: Int, technique: String): View {
        val itemView = LayoutInflater.from(this).inflate(
            R.layout.item_quick_relief_dynamic, 
            quickReliefItemsContainer, 
            false
        )
        
        val tvNumber = itemView.findViewById<TextView>(R.id.tv_item_number)
        val tvTechnique = itemView.findViewById<TextView>(R.id.tv_technique)
        val btnTry = itemView.findViewById<MaterialButton>(R.id.btn_try)
        
        tvNumber.text = number.toString()
        tvTechnique.text = technique
        
        btnTry.setOnClickListener {
            showTechniqueDialog(technique)
        }
        
        return itemView
    }
    
    private fun showTechniqueDialog(technique: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.try_this_technique))
            .setMessage(technique)
            .setPositiveButton(getString(R.string.ill_try_this)) { _, _ ->
                android.widget.Toast.makeText(this, getString(R.string.great_take_time), android.widget.Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.maybe_later)) { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton(getString(R.string.mark_as_completed)) { _, _ ->
                markTechniqueAsCompleted(technique)
            }
            .show()
    }
    
    private fun markTechniqueAsCompleted(technique: String) {
        val completedTechniques = sharedPreferences.getStringSet("completed_techniques", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        completedTechniques.add(technique)
        
        val editor = sharedPreferences.edit()
        editor.putStringSet("completed_techniques", completedTechniques)
        editor.apply()
        
        android.widget.Toast.makeText(this, getString(R.string.technique_marked_completed), android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun showCustomizeDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_customize_techniques, null)
        val editTexts = mutableListOf<EditText>()
        
        // Get current techniques
        val currentTechniques = sharedPreferences.getStringSet("today_techniques", null)?.toList() ?: listOf()
        
        // Set up edit texts
        for (i in 0 until 8) {
            val editText = dialogView.findViewById<EditText>(getEditTextId(i))
            editTexts.add(editText)
            if (i < currentTechniques.size) {
                editText.setText(currentTechniques[i])
            }
        }
        
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.customize_techniques_title))
            .setMessage(getString(R.string.customize_techniques_message))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.save_changes)) { _, _ ->
                val newTechniques = editTexts.map { it.text.toString().trim() }
                    .filter { it.isNotEmpty() }
                    .take(8)
                
                if (newTechniques.size == 8) {
                    saveCustomizedTechniques(newTechniques)
                } else {
                    android.widget.Toast.makeText(this, getString(R.string.please_fill_all_techniques), android.widget.Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton(getString(R.string.reset_to_default)) { _, _ ->
                generateNewQuickReliefItems()
            }
            .show()
    }
    
    private fun getEditTextId(index: Int): Int {
        return when (index) {
            0 -> R.id.et_technique_1
            1 -> R.id.et_technique_2
            2 -> R.id.et_technique_3
            3 -> R.id.et_technique_4
            4 -> R.id.et_technique_5
            5 -> R.id.et_technique_6
            6 -> R.id.et_technique_7
            7 -> R.id.et_technique_8
            else -> R.id.et_technique_1
        }
    }
    
    private fun saveCustomizedTechniques(techniques: List<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet("today_techniques", techniques.toSet())
        editor.putString("last_update_date", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
        editor.apply()
        
        displayQuickReliefItems(techniques)
        updateLastUpdatedText()
        
        android.widget.Toast.makeText(this, getString(R.string.techniques_customized), android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun shareQuickReliefItems() {
        val currentTechniques = sharedPreferences.getStringSet("today_techniques", null)?.toList() ?: listOf()
        
        val shareText = buildString {
            appendLine(getString(R.string.my_daily_techniques))
            appendLine()
            currentTechniques.forEachIndexed { index, technique ->
                appendLine("${index + 1}. $technique")
            }
            appendLine()
            appendLine(getString(R.string.generated_by_app))
        }
        
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_quick_relief)))
    }
    
    private fun updateLastUpdatedText() {
        val lastUpdateDate = sharedPreferences.getString("last_update_date", "")
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        
        if (lastUpdateDate == currentDate) {
            tvLastUpdated.text = getString(R.string.updated_today)
        } else {
            tvLastUpdated.text = "Last updated: $lastUpdateDate"
        }
    }
} 