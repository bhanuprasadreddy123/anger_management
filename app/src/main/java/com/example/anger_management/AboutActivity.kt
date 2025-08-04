package com.example.anger_management

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutActivity : BaseActivity() {
    
    private lateinit var btnBack: ImageButton
    private lateinit var btnDeveloperCredits: LinearLayout
    private lateinit var btnPrivacyPolicy: LinearLayout
    private lateinit var btnTermsOfService: LinearLayout
    private lateinit var btnLicenses: LinearLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)
        
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
        btnDeveloperCredits = findViewById(R.id.btnDeveloperCredits)
        btnPrivacyPolicy = findViewById(R.id.btnPrivacyPolicy)
        btnTermsOfService = findViewById(R.id.btnTermsOfService)
        btnLicenses = findViewById(R.id.btnLicenses)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnDeveloperCredits.setOnClickListener {
            showDeveloperCreditsDialog()
        }
        
        btnPrivacyPolicy.setOnClickListener {
            showPrivacyPolicyDialog()
        }
        
        btnTermsOfService.setOnClickListener {
            showTermsOfServiceDialog()
        }
        
        btnLicenses.setOnClickListener {
            showLicensesDialog()
        }
    }
    
    private fun showDeveloperCreditsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Developer Credits")
            .setMessage("Anger Management App\n\n" +
                    "Version: 1.0.0\n" +
                    "Build: 2024.01.15\n\n" +
                    "Developed by:\n" +
                    "• UI/UX Design Team\n" +
                    "• Backend Development Team\n" +
                    "• AI Integration Team\n" +
                    "• Quality Assurance Team\n\n" +
                    "Special thanks to:\n" +
                    "• Mental health professionals\n" +
                    "• Beta testers\n" +
                    "• Open source contributors")
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showPrivacyPolicyDialog() {
        AlertDialog.Builder(this)
            .setTitle("Privacy Policy")
            .setMessage("Your privacy is important to us.\n\n" +
                    "Data Collection:\n" +
                    "• We collect minimal data necessary for app functionality\n" +
                    "• Personal information is encrypted and stored securely\n" +
                    "• Usage analytics help improve the app experience\n\n" +
                    "Data Usage:\n" +
                    "• Your data is never sold to third parties\n" +
                    "• Information is used only for app functionality\n" +
                    "• You can request data deletion at any time\n\n" +
                    "Security:\n" +
                    "• All data transmission is encrypted\n" +
                    "• Regular security audits are conducted\n" +
                    "• We follow industry best practices")
            .setPositiveButton("Accept") { _, _ ->
                sharedPreferences.edit().putBoolean("privacy_policy_accepted", true).apply()
                Toast.makeText(this, "Privacy policy accepted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Decline", null)
            .show()
    }
    
    private fun showTermsOfServiceDialog() {
        AlertDialog.Builder(this)
            .setTitle("Terms of Service")
            .setMessage("By using this app, you agree to:\n\n" +
                    "1. Use the app for personal wellness only\n" +
                    "2. Not share your account with others\n" +
                    "3. Report any bugs or issues\n" +
                    "4. Accept app updates when available\n\n" +
                    "Limitations:\n" +
                    "• This app is not a substitute for professional medical advice\n" +
                    "• We are not liable for any misuse of the app\n" +
                    "• Service may be modified or discontinued\n\n" +
                    "Support:\n" +
                    "• Technical support is available via email\n" +
                    "• Community forums for user discussions\n" +
                    "• Regular updates and improvements")
            .setPositiveButton("Accept") { _, _ ->
                sharedPreferences.edit().putBoolean("terms_accepted", true).apply()
                Toast.makeText(this, "Terms of service accepted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Decline", null)
            .show()
    }
    
    private fun showLicensesDialog() {
        AlertDialog.Builder(this)
            .setTitle("Open Source Licenses")
            .setMessage("This app uses the following open source libraries:\n\n" +
                    "• AndroidX Libraries (Apache 2.0)\n" +
                    "• Material Design Components (Apache 2.0)\n" +
                    "• Kotlin Standard Library (Apache 2.0)\n" +
                    "• Retrofit (Apache 2.0)\n" +
                    "• OkHttp (Apache 2.0)\n" +
                    "• Gson (Apache 2.0)\n" +
                    "• Room Database (Apache 2.0)\n" +
                    "• Lifecycle Components (Apache 2.0)\n" +
                    "• Navigation Component (Apache 2.0)\n" +
                    "• Work Manager (Apache 2.0)\n\n" +
                    "Full license texts are available at:\n" +
                    "https://github.com/your-repo/licenses")
            .setPositiveButton("OK", null)
            .show()
    }
} 