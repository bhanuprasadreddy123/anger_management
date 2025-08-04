package com.example.anger_management

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.switchmaterial.SwitchMaterial

class AudioSettingsActivity : BaseActivity() {
    
    private lateinit var btnBack: ImageButton
    
    // Volume Controls
    private lateinit var seekBarMasterVolume: SeekBar
    private lateinit var seekBarBackgroundMusic: SeekBar
    private lateinit var seekBarVoiceGuidance: SeekBar
    
    // Audio Switches
    private lateinit var switchBackgroundMusic: SwitchMaterial
    private lateinit var switchVoiceGuidance: SwitchMaterial
    private lateinit var switchWhiteNoise: SwitchMaterial
    private lateinit var switchHeadphoneMode: SwitchMaterial
    
    // Settings Buttons
    private lateinit var btnAudioQuality: LinearLayout
    private lateinit var btnCalmPreset: LinearLayout
    private lateinit var btnFocusPreset: LinearLayout
    private lateinit var btnSleepPreset: LinearLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audio_settings)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setupClickListeners()
        setupSwitches()
        setupSeekBars()
        loadSavedSettings()
    }
    
    private fun initializeViews() {
        btnBack = findViewById(R.id.btnBack)
        
        // Volume Controls
        seekBarMasterVolume = findViewById(R.id.seekBarMasterVolume)
        seekBarBackgroundMusic = findViewById(R.id.seekBarBackgroundMusic)
        seekBarVoiceGuidance = findViewById(R.id.seekBarVoiceGuidance)
        
        // Audio Switches
        switchBackgroundMusic = findViewById(R.id.switchBackgroundMusic)
        switchVoiceGuidance = findViewById(R.id.switchVoiceGuidance)
        switchWhiteNoise = findViewById(R.id.switchWhiteNoise)
        switchHeadphoneMode = findViewById(R.id.switchHeadphoneMode)
        
        // Settings Buttons
        btnAudioQuality = findViewById(R.id.btnAudioQuality)
        btnCalmPreset = findViewById(R.id.btnCalmPreset)
        btnFocusPreset = findViewById(R.id.btnFocusPreset)
        btnSleepPreset = findViewById(R.id.btnSleepPreset)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnAudioQuality.setOnClickListener {
            showAudioQualityDialog()
        }
        
        btnCalmPreset.setOnClickListener {
            applyCalmPreset()
        }
        
        btnFocusPreset.setOnClickListener {
            applyFocusPreset()
        }
        
        btnSleepPreset.setOnClickListener {
            applySleepPreset()
        }
    }
    
    private fun setupSwitches() {
        switchBackgroundMusic.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("background_music_enabled", isChecked).apply()
            seekBarBackgroundMusic.isEnabled = isChecked
        }
        
        switchVoiceGuidance.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("voice_guidance_enabled", isChecked).apply()
            seekBarVoiceGuidance.isEnabled = isChecked
        }
        
        switchWhiteNoise.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("white_noise_enabled", isChecked).apply()
        }
        
        switchHeadphoneMode.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("headphone_mode", isChecked).apply()
        }
    }
    
    private fun setupSeekBars() {
        seekBarMasterVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    sharedPreferences.edit().putInt("master_volume", progress).apply()
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        seekBarBackgroundMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    sharedPreferences.edit().putInt("background_music_volume", progress).apply()
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        seekBarVoiceGuidance.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    sharedPreferences.edit().putInt("voice_guidance_volume", progress).apply()
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    
    private fun loadSavedSettings() {
        // Load volume settings
        seekBarMasterVolume.progress = sharedPreferences.getInt("master_volume", 70)
        seekBarBackgroundMusic.progress = sharedPreferences.getInt("background_music_volume", 50)
        seekBarVoiceGuidance.progress = sharedPreferences.getInt("voice_guidance_volume", 80)
        
        // Load switch states
        switchBackgroundMusic.isChecked = sharedPreferences.getBoolean("background_music_enabled", true)
        switchVoiceGuidance.isChecked = sharedPreferences.getBoolean("voice_guidance_enabled", true)
        switchWhiteNoise.isChecked = sharedPreferences.getBoolean("white_noise_enabled", false)
        switchHeadphoneMode.isChecked = sharedPreferences.getBoolean("headphone_mode", false)
        
        // Update seekbar states based on switches
        seekBarBackgroundMusic.isEnabled = switchBackgroundMusic.isChecked
        seekBarVoiceGuidance.isEnabled = switchVoiceGuidance.isChecked
    }
    
    private fun showAudioQualityDialog() {
        val qualities = arrayOf("Low (64 kbps)", "Medium (128 kbps)", "High (256 kbps)", "Lossless")
        val currentQuality = sharedPreferences.getString("audio_quality", "Medium (128 kbps)") ?: "Medium (128 kbps)"
        val currentIndex = qualities.indexOf(currentQuality).takeIf { it >= 0 } ?: 1
        
        AlertDialog.Builder(this)
            .setTitle("Audio Quality")
            .setSingleChoiceItems(qualities, currentIndex) { _, which ->
                val selectedQuality = qualities[which]
                sharedPreferences.edit().putString("audio_quality", selectedQuality).apply()
                Toast.makeText(this, "Audio quality set to $selectedQuality", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun applyCalmPreset() {
        AlertDialog.Builder(this)
            .setTitle("Apply Calm Preset")
            .setMessage("This will set audio settings optimized for calm sessions:\n\n" +
                    "• Master Volume: 60%\n" +
                    "• Background Music: 40%\n" +
                    "• Voice Guidance: 70%\n" +
                    "• Background Music: Enabled\n" +
                    "• Voice Guidance: Enabled\n" +
                    "• White Noise: Disabled")
            .setPositiveButton("Apply") { _, _ ->
                seekBarMasterVolume.progress = 60
                seekBarBackgroundMusic.progress = 40
                seekBarVoiceGuidance.progress = 70
                switchBackgroundMusic.isChecked = true
                switchVoiceGuidance.isChecked = true
                switchWhiteNoise.isChecked = false
                
                // Save settings
                sharedPreferences.edit().apply {
                    putInt("master_volume", 60)
                    putInt("background_music_volume", 40)
                    putInt("voice_guidance_volume", 70)
                    putBoolean("background_music_enabled", true)
                    putBoolean("voice_guidance_enabled", true)
                    putBoolean("white_noise_enabled", false)
                }.apply()
                
                Toast.makeText(this, "Calm preset applied", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun applyFocusPreset() {
        AlertDialog.Builder(this)
            .setTitle("Apply Focus Preset")
            .setMessage("This will set audio settings optimized for focus sessions:\n\n" +
                    "• Master Volume: 50%\n" +
                    "• Background Music: 30%\n" +
                    "• Voice Guidance: 90%\n" +
                    "• Background Music: Enabled\n" +
                    "• Voice Guidance: Enabled\n" +
                    "• White Noise: Enabled")
            .setPositiveButton("Apply") { _, _ ->
                seekBarMasterVolume.progress = 50
                seekBarBackgroundMusic.progress = 30
                seekBarVoiceGuidance.progress = 90
                switchBackgroundMusic.isChecked = true
                switchVoiceGuidance.isChecked = true
                switchWhiteNoise.isChecked = true
                
                // Save settings
                sharedPreferences.edit().apply {
                    putInt("master_volume", 50)
                    putInt("background_music_volume", 30)
                    putInt("voice_guidance_volume", 90)
                    putBoolean("background_music_enabled", true)
                    putBoolean("voice_guidance_enabled", true)
                    putBoolean("white_noise_enabled", true)
                }.apply()
                
                Toast.makeText(this, "Focus preset applied", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun applySleepPreset() {
        AlertDialog.Builder(this)
            .setTitle("Apply Sleep Preset")
            .setMessage("This will set audio settings optimized for sleep sessions:\n\n" +
                    "• Master Volume: 40%\n" +
                    "• Background Music: 20%\n" +
                    "• Voice Guidance: 60%\n" +
                    "• Background Music: Enabled\n" +
                    "• Voice Guidance: Disabled\n" +
                    "• White Noise: Enabled")
            .setPositiveButton("Apply") { _, _ ->
                seekBarMasterVolume.progress = 40
                seekBarBackgroundMusic.progress = 20
                seekBarVoiceGuidance.progress = 60
                switchBackgroundMusic.isChecked = true
                switchVoiceGuidance.isChecked = false
                switchWhiteNoise.isChecked = true
                
                // Save settings
                sharedPreferences.edit().apply {
                    putInt("master_volume", 40)
                    putInt("background_music_volume", 20)
                    putInt("voice_guidance_volume", 60)
                    putBoolean("background_music_enabled", true)
                    putBoolean("voice_guidance_enabled", false)
                    putBoolean("white_noise_enabled", true)
                }.apply()
                
                Toast.makeText(this, "Sleep preset applied", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
} 