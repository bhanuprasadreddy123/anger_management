package com.example.anger_management

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyRecordsActivity : AppCompatActivity() {
    
    private lateinit var btnBack: ImageButton
    private lateinit var btnCalendar: ImageButton
    private lateinit var btnAddRecord: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_records)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        btnBack = findViewById(R.id.btnBack)
        btnCalendar = findViewById(R.id.btnCalendar)
        btnAddRecord = findViewById(R.id.btnAddRecord)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnCalendar.setOnClickListener {
            Toast.makeText(this, "Calendar feature coming soon!", Toast.LENGTH_SHORT).show()
        }
        
        btnAddRecord.setOnClickListener {
            Toast.makeText(this, "Add New Record feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
} 