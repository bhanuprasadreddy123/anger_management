package com.example.anger_management

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TipsDetailActivity : AppCompatActivity() {
    
    private lateinit var tvTipTitle: TextView
    private lateinit var tvTipDescription: TextView
    private lateinit var tvHowToPractice: TextView
    private lateinit var tvBenefits: TextView
    private lateinit var tvAdditionalTips: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var btnStartPractice: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tips_detail)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initializeViews()
        setupClickListeners()
        loadTipDetails()
    }
    
    private fun initializeViews() {
        tvTipTitle = findViewById(R.id.tvTipTitle)
        tvTipDescription = findViewById(R.id.tvTipDescription)
        tvHowToPractice = findViewById(R.id.tvHowToPractice)
        tvBenefits = findViewById(R.id.tvBenefits)
        tvAdditionalTips = findViewById(R.id.tvAdditionalTips)
        btnBack = findViewById(R.id.btnBack)
        btnStartPractice = findViewById(R.id.btnStartPractice)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnStartPractice.setOnClickListener {
            // Navigate to TipPracticeActivity with tip details
            val intent = Intent(this, TipPracticeActivity::class.java)
            intent.putExtra("tip_title", tvTipTitle.text.toString())
            intent.putExtra("tip_description", tvTipDescription.text.toString())
            startActivity(intent)
        }
    }
    
    private fun loadTipDetails() {
        val tipTitle = intent.getStringExtra("tip_title") ?: "Tip"
        val tipDescription = intent.getStringExtra("tip_description") ?: "Description"
        
        tvTipTitle.text = tipTitle
        tvTipDescription.text = tipDescription
        
        // Set detailed content based on the tip title
        when (tipTitle) {
            "5 Quick Calming Techniques" -> {
                tvHowToPractice.text = "1. Take 3 deep breaths\n2. Count to 10 slowly\n3. Tense and relax your muscles\n4. Visualize a peaceful place\n5. Use positive self-talk"
                tvBenefits.text = "• Reduces stress and anxiety\n• Helps you stay calm in difficult situations\n• Improves emotional regulation\n• Provides immediate relief from anger"
                tvAdditionalTips.text = "Practice these techniques regularly, even when you're not angry. This will make them more effective when you need them most."
            }
            "Take Deep Breaths" -> {
                tvHowToPractice.text = "1. Sit or stand comfortably\n2. Place one hand on your chest, one on your belly\n3. Inhale slowly through your nose for 4 counts\n4. Hold your breath for 4 counts\n5. Exhale slowly through your mouth for 6 counts\n6. Repeat 5-10 times"
                tvBenefits.text = "• Activates the parasympathetic nervous system\n• Reduces heart rate and blood pressure\n• Increases oxygen to the brain\n• Promotes relaxation and calmness"
                tvAdditionalTips.text = "Try to practice deep breathing for 5-10 minutes daily. You can do this anywhere - at home, work, or even in public."
            }
            "Count to 10" -> {
                tvHowToPractice.text = "1. When you feel anger rising, pause\n2. Take a deep breath\n3. Count slowly from 1 to 10\n4. Focus on each number as you count\n5. Take another deep breath\n6. Assess if you still need to react"
                tvBenefits.text = "• Gives you time to think before acting\n• Prevents impulsive reactions\n• Helps you respond instead of react\n• Reduces the intensity of anger"
                tvAdditionalTips.text = "If counting to 10 isn't enough, try counting to 20 or 50. The key is to give yourself time to calm down."
            }
            "Practice Mindfulness" -> {
                tvHowToPractice.text = "1. Find a quiet place to sit\n2. Close your eyes or focus on a point\n3. Pay attention to your breath\n4. Notice your thoughts without judging them\n5. Bring your focus back to your breath\n6. Practice for 5-20 minutes daily"
                tvBenefits.text = "• Reduces stress and anxiety\n• Improves emotional awareness\n• Helps you stay present\n• Enhances self-control"
                tvAdditionalTips.text = "Start with just 5 minutes daily and gradually increase. You can practice mindfulness while walking, eating, or doing daily activities."
            }
            "Use Positive Self-Talk" -> {
                tvHowToPractice.text = "1. Identify negative thoughts\n2. Challenge them with positive alternatives\n3. Replace 'I can't' with 'I can try'\n4. Use encouraging words\n5. Practice daily affirmations\n6. Be kind to yourself"
                tvBenefits.text = "• Improves self-esteem and confidence\n• Reduces negative emotions\n• Helps you stay motivated\n• Promotes positive thinking patterns"
                tvAdditionalTips.text = "Write down positive affirmations and read them daily. Examples: 'I am calm and in control' or 'I can handle this situation.'"
            }
            "Take a Timeout" -> {
                tvHowToPractice.text = "1. Recognize when you're getting angry\n2. Excuse yourself politely\n3. Go to a quiet, safe place\n4. Take deep breaths\n5. Use calming techniques\n6. Return when you're calmer"
                tvBenefits.text = "• Prevents escalation of conflicts\n• Gives you time to think clearly\n• Reduces the risk of saying things you'll regret\n• Helps you respond more rationally"
                tvAdditionalTips.text = "Let others know that you need a timeout when you're feeling overwhelmed. This is a healthy coping mechanism."
            }
            "Exercise Regularly" -> {
                tvHowToPractice.text = "1. Choose an activity you enjoy\n2. Start with 20-30 minutes daily\n3. Include cardio, strength, and flexibility\n4. Exercise outdoors when possible\n5. Find a workout buddy\n6. Make it a habit"
                tvBenefits.text = "• Releases endorphins (feel-good hormones)\n• Reduces stress and tension\n• Improves mood and energy\n• Helps you sleep better"
                tvAdditionalTips.text = "You don't need a gym membership. Walking, dancing, yoga, or even cleaning can be great forms of exercise."
            }
            "Practice Gratitude" -> {
                tvHowToPractice.text = "1. Keep a gratitude journal\n2. Write down 3 things you're thankful for daily\n3. Express gratitude to others\n4. Notice small positive moments\n5. Reflect on past blessings\n6. Share gratitude with family"
                tvBenefits.text = "• Shifts focus from negative to positive\n• Improves overall happiness\n• Reduces stress and anxiety\n• Strengthens relationships"
                tvAdditionalTips.text = "Try writing a thank-you note to someone who has helped you. This practice benefits both you and the recipient."
            }
            "Use Progressive Relaxation" -> {
                tvHowToPractice.text = "1. Lie down in a comfortable position\n2. Start with your toes\n3. Tense the muscles for 5 seconds\n4. Release and relax for 10 seconds\n5. Move up to each muscle group\n6. Continue until you reach your head"
                tvBenefits.text = "• Reduces muscle tension\n• Promotes deep relaxation\n• Helps with sleep problems\n• Reduces physical symptoms of stress"
                tvAdditionalTips.text = "Practice this technique before bed to improve sleep quality. You can also use it during the day when you feel tense."
            }
            "Listen to Calming Music" -> {
                tvHowToPractice.text = "1. Create a playlist of calming songs\n2. Choose instrumental or nature sounds\n3. Listen with headphones for better focus\n4. Practice deep breathing while listening\n5. Use during stressful situations\n6. Make it part of your daily routine"
                tvBenefits.text = "• Reduces heart rate and blood pressure\n• Promotes relaxation\n• Improves mood\n• Helps with concentration"
                tvAdditionalTips.text = "Experiment with different types of music - classical, nature sounds, or even your favorite calming songs."
            }
            "Write in a Journal" -> {
                tvHowToPractice.text = "1. Set aside 10-15 minutes daily\n2. Write about your feelings and experiences\n3. Don't worry about grammar or spelling\n4. Be honest with yourself\n5. Reflect on your entries\n6. Use prompts if needed"
                tvBenefits.text = "• Helps process emotions\n• Provides clarity and perspective\n• Reduces stress and anxiety\n• Tracks your progress over time"
                tvAdditionalTips.text = "Try different types of journaling - gratitude, reflection, or even creative writing. Find what works best for you."
            }
            "Practice Empathy" -> {
                tvHowToPractice.text = "1. Listen actively without interrupting\n2. Try to understand others' perspectives\n3. Put yourself in their shoes\n4. Ask questions to learn more\n5. Validate their feelings\n6. Respond with compassion"
                tvBenefits.text = "• Improves relationships\n• Reduces conflicts\n• Increases understanding\n• Promotes emotional intelligence"
                tvAdditionalTips.text = "Remember that understanding someone's perspective doesn't mean you have to agree with them."
            }
            "Use Visualization" -> {
                tvHowToPractice.text = "1. Find a quiet, comfortable place\n2. Close your eyes\n3. Imagine a peaceful place (beach, forest, etc.)\n4. Use all your senses in the visualization\n5. Stay in this place for 5-10 minutes\n6. Practice regularly"
                tvBenefits.text = "• Reduces stress and anxiety\n• Promotes relaxation\n• Improves mood\n• Helps with sleep"
                tvAdditionalTips.text = "Create a mental 'safe place' that you can visit whenever you need to calm down."
            }
            "Set Healthy Boundaries" -> {
                tvHowToPractice.text = "1. Identify what you need and want\n2. Communicate your boundaries clearly\n3. Say 'no' when necessary\n4. Be consistent with your boundaries\n5. Respect others' boundaries\n6. Practice self-care"
                tvBenefits.text = "• Reduces stress and resentment\n• Improves self-esteem\n• Creates healthier relationships\n• Prevents burnout"
                tvAdditionalTips.text = "Remember that setting boundaries is not selfish - it's necessary for your well-being."
            }
            "Practice Active Listening" -> {
                tvHowToPractice.text = "1. Give your full attention\n2. Maintain eye contact\n3. Don't interrupt\n4. Ask clarifying questions\n5. Reflect back what you heard\n6. Show empathy and understanding"
                tvBenefits.text = "• Improves communication\n• Strengthens relationships\n• Reduces misunderstandings\n• Promotes mutual respect"
                tvAdditionalTips.text = "Active listening is a skill that improves with practice. Start with short conversations and build up."
            }
            else -> {
                tvHowToPractice.text = "Practice this technique regularly to see the best results. Start with small steps and gradually increase your practice time."
                tvBenefits.text = "This technique can help you manage anger, reduce stress, and improve your overall emotional well-being."
                tvAdditionalTips.text = "Remember that change takes time. Be patient with yourself and celebrate small progress."
            }
        }
    }
} 