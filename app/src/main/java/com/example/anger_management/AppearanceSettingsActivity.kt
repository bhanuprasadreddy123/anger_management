package com.example.anger_management

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.switchmaterial.SwitchMaterial

class AppearanceSettingsActivity : BaseActivity() {
    
    private lateinit var btnBack: ImageButton
    
    // Theme Settings
    private lateinit var btnThemeMode: LinearLayout
    private lateinit var switchAutoTheme: SwitchMaterial
    
    // Font Settings
    private lateinit var btnFontSize: LinearLayout
    private lateinit var btnFontFamily: LinearLayout
    
    // Color Settings
    private lateinit var btnPrimaryColor: LinearLayout
    
    // Accessibility
    private lateinit var switchHighContrast: SwitchMaterial
    private lateinit var switchReduceMotion: SwitchMaterial
    private lateinit var switchSmoothTransitions: SwitchMaterial
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_appearance_settings)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setupClickListeners()
        setupSwitches()
        loadSavedSettings()
    }
    
    private fun initializeViews() {
        btnBack = findViewById(R.id.btnBack)
        
        // Theme Settings
        btnThemeMode = findViewById(R.id.btnThemeMode)
        switchAutoTheme = findViewById(R.id.switchAutoTheme)
        
        // Font Settings
        btnFontSize = findViewById(R.id.btnFontSize)
        btnFontFamily = findViewById(R.id.btnFontFamily)
        
        // Color Settings
        btnPrimaryColor = findViewById(R.id.btnPrimaryColor)
        
        // Accessibility
        switchHighContrast = findViewById(R.id.switchHighContrast)
        switchReduceMotion = findViewById(R.id.switchReduceMotion)
        switchSmoothTransitions = findViewById(R.id.switchSmoothTransitions)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnThemeMode.setOnClickListener {
            showThemeModeDialog()
        }
        
        btnFontSize.setOnClickListener {
            showFontSizeDialog()
        }
        
        btnFontFamily.setOnClickListener {
            showFontFamilyDialog()
        }
        
        btnPrimaryColor.setOnClickListener {
            showPrimaryColorDialog()
        }
    }
    
    private fun setupSwitches() {
        switchAutoTheme.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("auto_theme", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
        
        switchHighContrast.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("high_contrast", isChecked).apply()
        }
        
        switchReduceMotion.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("reduce_motion", isChecked).apply()
        }
        
        switchSmoothTransitions.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("smooth_transitions", isChecked).apply()
        }
    }
    
    private fun loadSavedSettings() {
        // Load switch states
        switchAutoTheme.isChecked = sharedPreferences.getBoolean("auto_theme", true)
        switchHighContrast.isChecked = sharedPreferences.getBoolean("high_contrast", false)
        switchReduceMotion.isChecked = sharedPreferences.getBoolean("reduce_motion", false)
        switchSmoothTransitions.isChecked = sharedPreferences.getBoolean("smooth_transitions", true)
    }
    
    private fun showThemeModeDialog() {
        val themes = arrayOf("Light", "Dark", "System Default")
        val currentTheme = sharedPreferences.getString("theme_mode", "System Default") ?: "System Default"
        val currentIndex = themes.indexOf(currentTheme).takeIf { it >= 0 } ?: 2
        
        AlertDialog.Builder(this)
            .setTitle("Theme Mode")
            .setSingleChoiceItems(themes, currentIndex) { _, which ->
                val selectedTheme = themes[which]
                sharedPreferences.edit().putString("theme_mode", selectedTheme).apply()
                
                when (which) {
                    0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                
                Toast.makeText(this, "Theme changed to $selectedTheme", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showFontSizeDialog() {
        val sizes = arrayOf("Small", "Medium", "Large", "Extra Large")
        val currentSize = sharedPreferences.getString("font_size", "Medium") ?: "Medium"
        val currentIndex = sizes.indexOf(currentSize).takeIf { it >= 0 } ?: 1
        
        AlertDialog.Builder(this)
            .setTitle("Font Size")
            .setSingleChoiceItems(sizes, currentIndex) { _, which ->
                val selectedSize = sizes[which]
                sharedPreferences.edit().putString("font_size", selectedSize).apply()
                Toast.makeText(this, "Font size changed to $selectedSize", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showFontFamilyDialog() {
        val fonts = arrayOf("Default", "Sans Serif", "Serif", "Monospace", "Cursive")
        val currentFont = sharedPreferences.getString("font_family", "Default") ?: "Default"
        val currentIndex = fonts.indexOf(currentFont).takeIf { it >= 0 } ?: 0
        
        AlertDialog.Builder(this)
            .setTitle("Font Family")
            .setSingleChoiceItems(fonts, currentIndex) { _, which ->
                val selectedFont = fonts[which]
                sharedPreferences.edit().putString("font_family", selectedFont).apply()
                Toast.makeText(this, "Font family changed to $selectedFont", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showPrimaryColorDialog() {
        val colors = arrayOf("Blue", "Green", "Purple", "Orange", "Red", "Teal")
        val currentColor = sharedPreferences.getString("primary_color", "Blue") ?: "Blue"
        val currentIndex = colors.indexOf(currentColor).takeIf { it >= 0 } ?: 0
        
        AlertDialog.Builder(this)
            .setTitle("Primary Color")
            .setSingleChoiceItems(colors, currentIndex) { _, which ->
                val selectedColor = colors[which]
                sharedPreferences.edit().putString("primary_color", selectedColor).apply()
                Toast.makeText(this, "Primary color changed to $selectedColor", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("OK", null)
            .show()
    }
} 