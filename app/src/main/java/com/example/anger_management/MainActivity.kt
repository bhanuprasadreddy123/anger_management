package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Navigate directly to the front page activity
        val intent = Intent(this, fornt_page::class.java)
        startActivity(intent)
        finish() // Close MainActivity so it's not in the back stack
    }
}
