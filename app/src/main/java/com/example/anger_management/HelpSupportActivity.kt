package com.example.anger_management

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HelpSupportActivity : BaseActivity() {
    
    private lateinit var btnBack: ImageButton
    
    // Help Resources
    private lateinit var btnFAQs: LinearLayout
    private lateinit var btnUserGuide: LinearLayout
    private lateinit var btnVideoTutorials: LinearLayout
    
    // Contact Support
    private lateinit var btnContactSupport: LinearLayout
    private lateinit var btnEmailSupport: LinearLayout
    private lateinit var btnPhoneSupport: LinearLayout
    
    // Feedback & Community
    private lateinit var btnSendFeedback: LinearLayout
    private lateinit var btnRateApp: LinearLayout
    private lateinit var btnReportBug: LinearLayout
    private lateinit var btnCommunityForum: LinearLayout
    private lateinit var btnSocialMedia: LinearLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_help_support)
        
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
        
        // Help Resources
        btnFAQs = findViewById(R.id.btnFAQs)
        btnUserGuide = findViewById(R.id.btnUserGuide)
        btnVideoTutorials = findViewById(R.id.btnVideoTutorials)
        
        // Contact Support
        btnContactSupport = findViewById(R.id.btnContactSupport)
        btnEmailSupport = findViewById(R.id.btnEmailSupport)
        btnPhoneSupport = findViewById(R.id.btnPhoneSupport)
        
        // Feedback & Community
        btnSendFeedback = findViewById(R.id.btnSendFeedback)
        btnRateApp = findViewById(R.id.btnRateApp)
        btnReportBug = findViewById(R.id.btnReportBug)
        btnCommunityForum = findViewById(R.id.btnCommunityForum)
        btnSocialMedia = findViewById(R.id.btnSocialMedia)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        // Help Resources
        btnFAQs.setOnClickListener {
            showFAQsDialog()
        }
        
        btnUserGuide.setOnClickListener {
            showUserGuideDialog()
        }
        
        btnVideoTutorials.setOnClickListener {
            showVideoTutorialsDialog()
        }
        
        // Contact Support
        btnContactSupport.setOnClickListener {
            showContactSupportDialog()
        }
        
        btnEmailSupport.setOnClickListener {
            openEmailSupport()
        }
        
        btnPhoneSupport.setOnClickListener {
            showPhoneSupportDialog()
        }
        
        // Feedback & Community
        btnSendFeedback.setOnClickListener {
            showSendFeedbackDialog()
        }
        
        btnRateApp.setOnClickListener {
            openAppStore()
        }
        
        btnReportBug.setOnClickListener {
            showReportBugDialog()
        }
        
        btnCommunityForum.setOnClickListener {
            openCommunityForum()
        }
        
        btnSocialMedia.setOnClickListener {
            showSocialMediaDialog()
        }
    }
    
    private fun showFAQsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Frequently Asked Questions")
            .setMessage("Common questions and answers:\n\n" +
                    "Q: How do I start a meditation session?\n" +
                    "A: Go to the meditation section and select a session type.\n\n" +
                    "Q: Can I use the app offline?\n" +
                    "A: Yes, most features work without internet connection.\n\n" +
                    "Q: How do I track my progress?\n" +
                    "A: Use the progress section to view your statistics.\n\n" +
                    "Q: Is my data secure?\n" +
                    "A: Yes, we use encryption to protect your personal information.")
            .setPositiveButton("View All FAQs") { _, _ ->
                Toast.makeText(this, "Opening full FAQ section...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Close", null)
            .show()
    }
    
    private fun showUserGuideDialog() {
        AlertDialog.Builder(this)
            .setTitle("User Guide")
            .setMessage("Complete user guide sections:\n\n" +
                    "• Getting Started\n" +
                    "• Meditation Sessions\n" +
                    "• Mood Tracking\n" +
                    "• Journal Features\n" +
                    "• Settings & Preferences\n" +
                    "• Troubleshooting\n" +
                    "• Advanced Features")
            .setPositiveButton("Open Guide") { _, _ ->
                Toast.makeText(this, "Opening user guide...", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showVideoTutorialsDialog() {
        val tutorials = arrayOf("Getting Started", "Meditation Basics", "Mood Tracking", "Journal Writing", "Settings Guide")
        
        AlertDialog.Builder(this)
            .setTitle("Video Tutorials")
            .setItems(tutorials) { _, which ->
                val tutorial = tutorials[which]
                Toast.makeText(this, "Opening tutorial: $tutorial", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showContactSupportDialog() {
        AlertDialog.Builder(this)
            .setTitle("Contact Support")
            .setMessage("Get help from our support team:\n\n" +
                    "Available channels:\n" +
                    "• Email support (24-48 hour response)\n" +
                    "• Phone support (Business hours)\n" +
                    "• Live chat (Limited hours)\n" +
                    "• Community forum\n\n" +
                    "Before contacting support:\n" +
                    "• Check the FAQs\n" +
                    "• Review the user guide\n" +
                    "• Try restarting the app")
            .setPositiveButton("Contact Now") { _, _ ->
                showContactOptions()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showContactOptions() {
        val options = arrayOf("Email Support", "Phone Support", "Live Chat", "Community Forum")
        
        AlertDialog.Builder(this)
            .setTitle("Choose Contact Method")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openEmailSupport()
                    1 -> showPhoneSupportDialog()
                    2 -> Toast.makeText(this, "Live chat not available", Toast.LENGTH_SHORT).show()
                    3 -> openCommunityForum()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun openEmailSupport() {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:support@angermanagement.com")
                putExtra(Intent.EXTRA_SUBJECT, "Support Request - Anger Management App")
                putExtra(Intent.EXTRA_TEXT, "Hello,\n\nI need help with the Anger Management app.\n\nIssue:\n\nDevice: Android\nApp Version: 1.0.0\n\nThank you.")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showPhoneSupportDialog() {
        AlertDialog.Builder(this)
            .setTitle("Phone Support")
            .setMessage("Call our support team:\n\n" +
                    "Phone: +1-800-ANGER-HELP\n" +
                    "Hours: Monday-Friday, 9 AM - 6 PM EST\n" +
                    "Emergency: Available 24/7\n\n" +
                    "Please have ready:\n" +
                    "• Your device information\n" +
                    "• App version number\n" +
                    "• Description of the issue")
            .setPositiveButton("Call Now") { _, _ ->
                try {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:+1800264374357")
                    }
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Unable to make call", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showSendFeedbackDialog() {
        AlertDialog.Builder(this)
            .setTitle("Send Feedback")
            .setMessage("We value your feedback!\n\n" +
                    "What would you like to share?\n" +
                    "• Feature suggestions\n" +
                    "• App improvements\n" +
                    "• User experience feedback\n" +
                    "• General comments\n\n" +
                    "Your feedback helps us improve the app for everyone.")
            .setPositiveButton("Send Feedback") { _, _ ->
                try {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:feedback@angermanagement.com")
                        putExtra(Intent.EXTRA_SUBJECT, "App Feedback - Anger Management")
                        putExtra(Intent.EXTRA_TEXT, "Hello,\n\nI would like to share feedback about the Anger Management app:\n\nFeedback:\n\nThank you!")
                    }
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun openAppStore() {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=com.example.anger_management")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open app store", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showReportBugDialog() {
        AlertDialog.Builder(this)
            .setTitle("Report a Bug")
            .setMessage("Help us fix issues by reporting bugs:\n\n" +
                    "Please include:\n" +
                    "• Description of the problem\n" +
                    "• Steps to reproduce\n" +
                    "• Device and OS version\n" +
                    "• App version\n" +
                    "• Screenshots (if possible)\n\n" +
                    "We'll investigate and get back to you.")
            .setPositiveButton("Report Bug") { _, _ ->
                try {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:bugs@angermanagement.com")
                        putExtra(Intent.EXTRA_SUBJECT, "Bug Report - Anger Management App")
                        putExtra(Intent.EXTRA_TEXT, "Bug Report:\n\nDescription:\n\nSteps to reproduce:\n\nDevice: Android\nOS Version:\nApp Version: 1.0.0\n\nThank you for helping us improve!")
                    }
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun openCommunityForum() {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://community.angermanagement.com")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open community forum", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showSocialMediaDialog() {
        val platforms = arrayOf("Facebook", "Twitter", "Instagram", "LinkedIn", "YouTube")
        
        AlertDialog.Builder(this)
            .setTitle("Follow Us")
            .setMessage("Connect with us on social media:")
            .setItems(platforms) { _, which ->
                val platform = platforms[which]
                openSocialMedia(platform)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun openSocialMedia(platform: String) {
        val urls = mapOf(
            "Facebook" to "https://facebook.com/angermanagementapp",
            "Twitter" to "https://twitter.com/angermanagement",
            "Instagram" to "https://instagram.com/angermanagementapp",
            "LinkedIn" to "https://linkedin.com/company/angermanagement",
            "YouTube" to "https://youtube.com/angermanagementapp"
        )
        
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(urls[platform])
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open $platform", Toast.LENGTH_SHORT).show()
        }
    }
} 