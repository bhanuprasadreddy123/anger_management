package com.example.anger_management

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MoodSaveActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_saved)
        
        // Initialize views
        val btnBack = findViewById<android.widget.ImageView>(R.id.btn_back)
        
        // Set click listeners
        btnBack.setOnClickListener {
            finish()
        }
        
        // You can add click listeners for the action cards here
        // The cards can be made clickable by adding android:clickable="true"
        // and android:focusable="true" to the CardView elements
    }
}
