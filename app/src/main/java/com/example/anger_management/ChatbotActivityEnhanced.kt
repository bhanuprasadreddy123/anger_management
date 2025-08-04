package com.example.anger_management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class ChatbotActivityEnhanced : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var backButton: MaterialButton
    
    private val messages = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)
        
        initializeViews()
        setupRecyclerView()
        setupClickListeners()
        setupKeyboardBehavior()
        addWelcomeMessage()
    }
    
    private fun initializeViews() {
        recyclerView = findViewById(R.id.recyclerView)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)
        backButton = findViewById(R.id.backButton)
    }
    
    private fun setupRecyclerView() {
        adapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    
    private fun setupClickListeners() {
        sendButton.setOnClickListener {
            sendMessage()
        }
        
        backButton.setOnClickListener {
            finish()
        }
        
        // Send message on Enter key
        messageInput.setOnEditorActionListener { _, _, _ ->
            sendMessage()
            true
        }
    }
    
    private fun setupKeyboardBehavior() {
        // Ensure the input area gets focus when clicked
        messageInput.setOnClickListener {
            messageInput.requestFocus()
        }
        
        // Auto-scroll to bottom when keyboard appears
        recyclerView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom < oldBottom) {
                recyclerView.postDelayed({
                    if (messages.isNotEmpty()) {
                        recyclerView.scrollToPosition(messages.size - 1)
                    }
                }, 100)
            }
        }
        
        // Request focus on input after a short delay to ensure layout is ready
        messageInput.postDelayed({
            messageInput.requestFocus()
        }, 300)
    }
    
    private fun addWelcomeMessage() {
        val welcomeMessage = ChatMessage(
            "👋 Hello! I'm your AI anger management assistant, here to support you 24/7.\n\n" +
            "I can help you with:\n" +
            "🚗 Traffic & road rage\n" +
            "💼 Work & professional stress\n" +
            "👨‍👩‍👧‍👦 Family & relationship conflicts\n" +
            "💻 Technology frustrations\n" +
            "💰 Financial stress\n" +
            "🧠 Mental health & anxiety\n" +
            "🎯 Social media stress\n" +
            "🏠 Home & daily life challenges\n\n" +
            "Just tell me what's bothering you, and I'll provide personalized, evidence-based strategies to help you find calm and peace! 🌟",
            false,
            getCurrentTime()
        )
        messages.add(welcomeMessage)
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }
    
    private fun sendMessage() {
        val userMessage = messageInput.text.toString().trim()
        if (userMessage.isEmpty()) return
        
        // Add user message
        val userChatMessage = ChatMessage(userMessage, true, getCurrentTime())
        messages.add(userChatMessage)
        adapter.notifyItemInserted(messages.size - 1)
        
        // Clear input
        messageInput.text.clear()
        
        // Generate bot response
        val botResponse = generateEnhancedBotResponse(userMessage.lowercase())
        val botMessage = ChatMessage(botResponse, false, getCurrentTime())
        
        // Add slight delay for realistic chat experience
        recyclerView.postDelayed({
            messages.add(botMessage)
            adapter.notifyItemInserted(messages.size - 1)
            recyclerView.scrollToPosition(messages.size - 1)
        }, 500)
    }
    
    private fun generateEnhancedBotResponse(userMessage: String): String {
        // First, check for positive emotions and provide encouraging responses
        if (isPositiveMessage(userMessage)) {
            return getPositiveResponse(userMessage)
        }
        
        // Then check for negative emotions and provide supportive responses
        if (isNegativeMessage(userMessage)) {
            return getNegativeEmotionResponse(userMessage)
        }
        
        val responses = when {
            // Traffic/Commute Anger
            userMessage.contains("traffic") || userMessage.contains("road") || userMessage.contains("driver") || userMessage.contains("commute") || userMessage.contains("driving") -> {
                listOf(
                    "🚗 Traffic stress is real! Here's your modern toolkit:\n\n" +
                    "🎵 **Audio Therapy**:\n" +
                    "• Create a calming playlist with nature sounds\n" +
                    "• Listen to mindfulness podcasts\n" +
                    "• Try guided meditation apps\n\n" +
                    "🧘 **Mindful Driving**:\n" +
                    "• Practice box breathing at red lights\n" +
                    "• Focus on the journey, not just the destination\n" +
                    "• Use traffic time for self-reflection\n\n" +
                    "💡 **Smart Strategies**:\n" +
                    "• Leave 15 minutes earlier to avoid time pressure\n" +
                    "• Use navigation apps to find alternative routes\n" +
                    "• Remember: Getting angry won't make traffic move faster\n\n" +
                    "Your safety and peace of mind are priceless! 🛡️",
                    
                    "😤 Road rage is a modern epidemic. Here's your defense plan:\n\n" +
                    "⚡ **Immediate Actions**:\n" +
                    "• Take 3 deep breaths at each stop\n" +
                    "• Turn on calming instrumental music\n" +
                    "• Focus on the scenery, not other drivers\n\n" +
                    "🧠 **Mental Reframing**:\n" +
                    "• Think: 'Everyone is trying to get somewhere'\n" +
                    "• Remember: You can't control other drivers\n" +
                    "• Practice empathy - maybe they're having a bad day too\n\n" +
                    "🎯 **Prevention**:\n" +
                    "• Plan your route the night before\n" +
                    "• Keep a stress ball in your car\n" +
                    "• Use traffic apps to avoid congestion\n\n" +
                    "You're in control of your reactions! 💪"
                )
            }
            
            // Work/Professional Anger
            userMessage.contains("work") || userMessage.contains("boss") || userMessage.contains("colleague") || userMessage.contains("meeting") || userMessage.contains("deadline") || userMessage.contains("office") -> {
                listOf(
                    "💼 Modern work stress needs modern solutions:\n\n" +
                    "🏃 **Physical Relief**:\n" +
                    "• Take a 5-minute walk outside\n" +
                    "• Do desk stretches every hour\n" +
                    "• Use a stress ball or fidget toy\n\n" +
                    "🧘 **Mindfulness at Work**:\n" +
                    "• Practice 4-7-8 breathing technique\n" +
                    "• Use the Pomodoro method (25min work, 5min break)\n" +
                    "• Take micro-breaks to look out the window\n\n" +
                    "💻 **Digital Wellness**:\n" +
                    "• Turn off notifications during focus time\n" +
                    "• Use apps like Forest to stay focused\n" +
                    "• Set boundaries with work communication\n\n" +
                    "Remember: Your mental health is your greatest asset! 🌟",
                    
                    "🏢 Workplace conflicts in the digital age:\n\n" +
                    "🤝 **Communication Skills**:\n" +
                    "• Use 'I feel' statements instead of blaming\n" +
                    "• Practice active listening\n" +
                    "• Ask clarifying questions\n\n" +
                    "⚖️ **Boundary Setting**:\n" +
                    "• Learn to say 'no' professionally\n" +
                    "• Set clear work-life boundaries\n" +
                    "• Use time-blocking techniques\n\n" +
                    "🧠 **Stress Management**:\n" +
                    "• Use the 10-10-10 rule: Will this matter in 10 minutes, 10 months, 10 years?\n" +
                    "• Focus on solutions, not problems\n" +
                    "• Practice gratitude for your job security\n\n" +
                    "You're building resilience every day! 💪"
                )
            }
            
            // Family/Relationship Anger
            userMessage.contains("family") || userMessage.contains("partner") || userMessage.contains("spouse") || userMessage.contains("child") || userMessage.contains("parent") || userMessage.contains("relationship") -> {
                listOf(
                    "👨‍👩‍👧‍👦 Family dynamics are complex. Here's your guide:\n\n" +
                    "⏰ **Time-Out Strategy**:\n" +
                    "• Take a 10-minute break in another room\n" +
                    "• Use a calming app or music\n" +
                    "• Practice deep breathing\n\n" +
                    "💬 **Healthy Communication**:\n" +
                    "• Use 'I feel' statements\n" +
                    "• Avoid 'you always' or 'you never'\n" +
                    "• Practice active listening\n\n" +
                    "❤️ **Emotional Intelligence**:\n" +
                    "• Remember: Love is more important than being right\n" +
                    "• Practice empathy and understanding\n" +
                    "• Choose your battles wisely\n\n" +
                    "Sometimes silence and space are the best responses. 🤗",
                    
                    "❤️ Relationship anger often stems from hurt feelings:\n\n" +
                    "🧠 **Emotional Awareness**:\n" +
                    "• Identify your triggers\n" +
                    "• Understand your partner's perspective\n" +
                    "• Practice emotional regulation\n\n" +
                    "🔄 **Conflict Resolution**:\n" +
                    "• Use the 10-10-10 rule\n" +
                    "• Take turns speaking and listening\n" +
                    "• Focus on the issue, not the person\n\n" +
                    "💝 **Love Languages**:\n" +
                    "• Learn each other's love languages\n" +
                    "• Show appreciation daily\n" +
                    "• Practice forgiveness\n\n" +
                    "Love and understanding heal faster than anger. 💕"
                )
            }
            
            // Technology/Frustration Anger
            userMessage.contains("computer") || userMessage.contains("phone") || userMessage.contains("internet") || userMessage.contains("app") || userMessage.contains("technology") || userMessage.contains("wifi") || userMessage.contains("device") -> {
                listOf(
                    "💻 Tech frustration in the digital age:\n\n" +
                    "🔄 **Immediate Actions**:\n" +
                    "• Step away from the device for 5 minutes\n" +
                    "• Take 10 deep breaths\n" +
                    "• Do some physical stretches\n\n" +
                    "🧠 **Mental Reframing**:\n" +
                    "• Remember: Technology is a tool, not worth your peace\n" +
                    "• Think: 'This is temporary'\n" +
                    "• Focus on what you can control\n\n" +
                    "💡 **Problem Solving**:\n" +
                    "• Try a different approach\n" +
                    "• Ask for help from tech support\n" +
                    "• Use alternative methods\n\n" +
                    "Your mental health is more valuable than any device! 🧘‍♀️",
                    
                    "📱 Digital stress management:\n\n" +
                    "🚫 **Digital Detox**:\n" +
                    "• Put your phone in another room for 30 minutes\n" +
                    "• Use apps like Forest to limit screen time\n" +
                    "• Set 'Do Not Disturb' during focus time\n\n" +
                    "🏃 **Physical Activity**:\n" +
                    "• Go for a short walk\n" +
                    "• Do some stretching exercises\n" +
                    "• Practice yoga or meditation\n\n" +
                    "🧠 **Mindful Tech Use**:\n" +
                    "• Remember: The internet will still be there later\n" +
                    "• Focus on one task at a time\n" +
                    "• Take regular breaks from screens\n\n" +
                    "Sometimes the best tech solution is to unplug! 🌿"
                )
            }
            
            // Social Media Stress
            userMessage.contains("social media") || userMessage.contains("instagram") || userMessage.contains("facebook") || userMessage.contains("twitter") || userMessage.contains("tiktok") || userMessage.contains("online") -> {
                listOf(
                    "📱 Social media can be overwhelming. Here's your digital wellness plan:\n\n" +
                    "🧠 **Mental Health Awareness**:\n" +
                    "• Remember: Social media is a highlight reel, not reality\n" +
                    "• Unfollow accounts that make you feel bad\n" +
                    "• Curate your feed for positivity\n\n" +
                    "⏰ **Time Management**:\n" +
                    "• Set app time limits on your phone\n" +
                    "• Use apps like Forest to stay focused\n" +
                    "• Take social media breaks\n\n" +
                    "💡 **Healthy Habits**:\n" +
                    "• Don't check social media first thing in the morning\n" +
                    "• Practice gratitude instead of comparison\n" +
                    "• Connect with real people offline\n\n" +
                    "Your real life is more important than your online presence! 🌟",
                    
                    "🎯 Social media stress is real. Here's how to cope:\n\n" +
                    "🔄 **Perspective Shift**:\n" +
                    "• Everyone posts their best moments\n" +
                    "• Real life has ups and downs\n" +
                    "• Focus on your own journey\n\n" +
                    "🧘 **Digital Mindfulness**:\n" +
                    "• Take deep breaths before posting\n" +
                    "• Think: 'Is this adding value to my life?'\n" +
                    "• Practice self-compassion\n\n" +
                    "💪 **Empowerment**:\n" +
                    "• You control your social media experience\n" +
                    "• Choose what you consume\n" +
                    "• Remember: You're enough as you are\n\n" +
                    "Your worth isn't measured by likes or followers! 💕"
                )
            }
            
            // Financial Stress Anger
            userMessage.contains("money") || userMessage.contains("bill") || userMessage.contains("financial") || userMessage.contains("expensive") || userMessage.contains("cost") || userMessage.contains("debt") -> {
                listOf(
                    "💰 Financial stress in today's economy:\n\n" +
                    "🧘 **Immediate Calming**:\n" +
                    "• Take deep breaths to center yourself\n" +
                    "• Focus on what you can control right now\n" +
                    "• Practice gratitude for what you have\n\n" +
                    "📊 **Financial Planning**:\n" +
                    "• Break problems into smaller, manageable pieces\n" +
                    "• Create a simple budget\n" +
                    "• Focus on one financial goal at a time\n\n" +
                    "💡 **Mindset Shift**:\n" +
                    "• Remember: Money problems are often temporary\n" +
                    "• Your worth is not measured by your bank account\n" +
                    "• Many people face similar challenges\n\n" +
                    "Financial stress is temporary - your peace of mind is priceless! 🌟",
                    
                    "💳 Money worries are valid. Here's your action plan:\n\n" +
                    "📝 **Organization**:\n" +
                    "• Write down all your financial concerns\n" +
                    "• Prioritize what needs immediate attention\n" +
                    "• Create a simple action plan\n\n" +
                    "🧠 **Mental Health**:\n" +
                    "• Practice stress-reduction techniques\n" +
                    "• Talk to a trusted friend or family member\n" +
                    "• Consider professional financial counseling\n\n" +
                    "💪 **Empowerment**:\n" +
                    "• Focus on what you can control\n" +
                    "• Celebrate small financial wins\n" +
                    "• Remember: You're not alone in this\n\n" +
                    "You have the strength to overcome financial challenges! 💪"
                )
            }
            
            // Mental Health & Anxiety
            userMessage.contains("anxiety") || userMessage.contains("depression") || userMessage.contains("mental health") || userMessage.contains("panic") || userMessage.contains("overwhelm") -> {
                listOf(
                    "🧠 Mental health matters. Here's your support toolkit:\n\n" +
                    "🫁 **Breathing Techniques**:\n" +
                    "• 4-7-8 breathing: Inhale 4, hold 7, exhale 8\n" +
                    "• Box breathing: 4 counts in, hold 4, out 4, hold 4\n" +
                    "• Belly breathing: Focus on your diaphragm\n\n" +
                    "🧘 **Grounding Exercises**:\n" +
                    "• 5-4-3-2-1 technique: Name things you see, touch, hear, smell, taste\n" +
                    "• Progressive muscle relaxation\n" +
                    "• Mindfulness meditation\n\n" +
                    "💡 **Self-Care**:\n" +
                    "• Get enough sleep\n" +
                    "• Exercise regularly\n" +
                    "• Connect with supportive people\n\n" +
                    "Remember: It's okay to not be okay. You're not alone! 💙",
                    
                    "🌟 Mental health challenges are real and valid:\n\n" +
                    "🎯 **Immediate Relief**:\n" +
                    "• Take one step at a time\n" +
                    "• Focus on the present moment\n" +
                    "• Practice self-compassion\n\n" +
                    "🔄 **Long-term Strategies**:\n" +
                    "• Consider professional therapy\n" +
                    "• Build a support network\n" +
                    "• Develop healthy coping mechanisms\n\n" +
                    "💪 **Empowerment**:\n" +
                    "• Your feelings are valid\n" +
                    "• Recovery is possible\n" +
                    "• You're stronger than you think\n\n" +
                    "You deserve support and understanding! 🌈"
                )
            }
            
            // General Anger/Stress
            userMessage.contains("angry") || userMessage.contains("mad") || userMessage.contains("furious") || userMessage.contains("rage") || userMessage.contains("irritated") -> {
                listOf(
                    "💙 I understand you're feeling angry right now, and that's completely okay. Anger is a natural emotion that tells us something needs attention. Let me help you navigate this:\n\n" +
                    "🫂 **First, Acknowledge Your Feelings**:\n" +
                    "• It's okay to feel angry - don't judge yourself\n" +
                    "• Your anger is valid and deserves to be heard\n" +
                    "• Take a moment to breathe and recognize what you're feeling\n\n" +
                    "⚡ **Immediate Calming Techniques**:\n" +
                    "• Take 5 deep breaths: Inhale 4, hold 4, exhale 4\n" +
                    "• Count backwards from 10 to 1 slowly\n" +
                    "• Step away from the situation for 5 minutes\n" +
                    "• Drink a glass of cold water\n\n" +
                    "🧠 **Understanding Your Anger**:\n" +
                    "• What triggered this feeling?\n" +
                    "• What do you need right now?\n" +
                    "• How can you express this healthily?\n\n" +
                    "💪 **Remember**: You have the power to choose how you respond. Your feelings matter, and you're doing great by seeking help! 🌟",
                    
                    "🤗 I hear your anger, and I want you to know that your feelings are important. Anger often masks other emotions like hurt, fear, or frustration. Let's work through this together:\n\n" +
                    "🧘 **Mindful Anger Management**:\n" +
                    "• Close your eyes and take 3 deep breaths\n" +
                    "• Tense and relax your muscles from toes to head\n" +
                    "• Repeat a calming phrase: 'I am safe and in control'\n" +
                    "• Focus on something pleasant for 30 seconds\n\n" +
                    "🎯 **Emotional Intelligence**:\n" +
                    "• Your anger is trying to protect you\n" +
                    "• It's okay to feel this way\n" +
                    "• You can choose how to express it\n\n" +
                    "💡 **Healthy Expression**:\n" +
                    "• Write down your feelings\n" +
                    "• Talk to someone you trust\n" +
                    "• Use physical activity to release energy\n\n" +
                    "💙 **You're not alone in this.** Every emotion serves a purpose, and you're learning to work with yours beautifully! 🌈"
                )
            }
            
            // Breathing/Calming Requests
            userMessage.contains("breath") || userMessage.contains("breathe") || userMessage.contains("calm") || userMessage.contains("relax") -> {
                listOf(
                    "🌬️ Let's practice modern breathing techniques:\n\n" +
                    "🫁 **4-7-8 Breathing** (for immediate calm):\n" +
                    "• Inhale through your nose for 4 counts\n" +
                    "• Hold your breath for 7 counts\n" +
                    "• Exhale through your mouth for 8 counts\n\n" +
                    "Repeat this 4 times. Feel the tension leaving your body with each breath.\n\n" +
                    "💡 **Pro Tip**: Practice this daily to build your stress resilience!",
                    
                    "🫁 **Box Breathing** (for focus and balance):\n\n" +
                    "📦 **The Technique**:\n" +
                    "• Inhale for 4 counts\n" +
                    "• Hold for 4 counts\n" +
                    "• Exhale for 4 counts\n" +
                    "• Hold for 4 counts\n\n" +
                    "Repeat 5 times. This creates a sense of balance and calm.\n\n" +
                    "🎯 **When to Use**: Perfect for work stress, before important meetings, or when you need to focus!",
                    
                    "🌸 **Mindful Breathing** (for everyday peace):\n\n" +
                    "🧘 **Simple Practice**:\n" +
                    "• Sit comfortably and close your eyes\n" +
                    "• Focus on your natural breath\n" +
                    "• Count each breath from 1 to 10\n" +
                    "• Start over when you reach 10\n\n" +
                    "Practice for 5-10 minutes daily.\n\n" +
                    "🌟 **Benefits**: Reduces stress, improves focus, and brings inner peace!"
                )
            }
            
            // Stress/Overwhelm
            userMessage.contains("stress") || userMessage.contains("overwhelm") || userMessage.contains("pressure") || userMessage.contains("burnout") -> {
                listOf(
                    "💙 I can feel how overwhelmed you are, and I want you to know that it's completely normal to feel this way. You're carrying a lot, and it's okay to need support. Let me help you find some relief:\n\n" +
                    "🫂 **First, Be Gentle With Yourself**:\n" +
                    "• You're doing the best you can\n" +
                    "• It's okay to feel overwhelmed\n" +
                    "• You don't have to handle everything alone\n\n" +
                    "🧘 **Immediate Relief - Progressive Muscle Relaxation**:\n" +
                    "• Tense your toes for 5 seconds, then relax\n" +
                    "• Move up to calves, thighs, stomach, arms, face\n" +
                    "• Feel the tension release with each relaxation\n\n" +
                    "🎯 **Breaking It Down**:\n" +
                    "• What's the most urgent thing right now?\n" +
                    "• What can wait until later?\n" +
                    "• What can you let go of?\n\n" +
                    "💪 **Remember**: You're stronger than you think, and it's okay to ask for help! 🌟",
                    
                    "🤗 I hear how stressed and overwhelmed you're feeling, and I want you to know that your feelings are valid. Stress is your body's way of telling you that you need care. Let's work through this together:\n\n" +
                    "🌊 **When Stress Feels Like a Wave - 5-4-3-2-1 Grounding Exercise**:\n" +
                    "• Name 5 things you can see\n" +
                    "• Name 4 things you can touch\n" +
                    "• Name 3 things you can hear\n" +
                    "• Name 2 things you can smell\n" +
                    "• Name 1 thing you can taste\n\n" +
                    "🧠 **Gentle Self-Talk**:\n" +
                    "• 'I'm doing the best I can'\n" +
                    "• 'This feeling will pass'\n" +
                    "• 'I can handle this one step at a time'\n\n" +
                    "💡 **Self-Care Reminders**:\n" +
                    "• Take breaks when you need them\n" +
                    "• Ask for help when possible\n" +
                    "• Remember: You're not a machine\n\n" +
                    "💙 **You're not alone in this.** Every step you take, no matter how small, is progress! 🌈"
                )
            }
            
            // Help/General Support
            userMessage.contains("help") || userMessage.contains("what can you do") || userMessage.contains("support") -> {
                listOf(
                    "🤖 I'm your AI anger management assistant! Here's how I can help:\n\n" +
                    "🎯 **Personalized Support**:\n" +
                    "• Situation-specific coping strategies\n" +
                    "• Evidence-based techniques\n" +
                    "• Modern stress management tools\n\n" +
                    "🧘 **Wellness Techniques**:\n" +
                    "• Guided breathing exercises\n" +
                    "• Mindfulness and meditation practices\n" +
                    "• Progressive muscle relaxation\n\n" +
                    "💡 **Modern Solutions**:\n" +
                    "• Digital wellness strategies\n" +
                    "• Work-life balance tips\n" +
                    "• Relationship communication skills\n\n" +
                    "Just tell me what's bothering you - I'm here 24/7! 🌟",
                    
                    "💙 I'm here to support your emotional well-being journey:\n\n" +
                    "📚 **Educational Resources**:\n" +
                    "• Understanding anger triggers\n" +
                    "• Emotional intelligence development\n" +
                    "• Stress management techniques\n\n" +
                    "🛠️ **Practical Tools**:\n" +
                    "• Immediate relief techniques\n" +
                    "• Long-term coping strategies\n" +
                    "• Lifestyle improvement tips\n\n" +
                    "🌟 **Modern Approaches**:\n" +
                    "• Digital wellness practices\n" +
                    "• Work-life integration\n" +
                    "• Mental health awareness\n\n" +
                    "What would you like to work on today? 💪"
                )
            }
            
            // Triggers/Understanding Anger
            userMessage.contains("trigger") || userMessage.contains("what makes me angry") || userMessage.contains("why am i angry") || userMessage.contains("cause") -> {
                listOf(
                    "🔍 Understanding your triggers is the first step to emotional mastery:\n\n" +
                    "🎯 **Common Modern Triggers**:\n" +
                    "• Feeling disrespected or ignored\n" +
                    "• Traffic and commuting stress\n" +
                    "• Work pressure and deadlines\n" +
                    "• Technology frustrations\n" +
                    "• Financial worries\n" +
                    "• Social media comparison\n" +
                    "• Sleep deprivation\n" +
                    "• Hunger or physical discomfort\n\n" +
                    "What specific situation triggered your anger today? 🤔",
                    
                    "🎯 Identifying triggers helps you prepare better responses:\n\n" +
                    "🧠 **Emotional Patterns**:\n" +
                    "• Work stress and deadlines\n" +
                    "• Family conflicts and misunderstandings\n" +
                    "• Technology and digital frustrations\n" +
                    "• Financial concerns and uncertainty\n" +
                    "• Health and wellness challenges\n" +
                    "• Social situations and expectations\n\n" +
                    "💡 **Self-Awareness**:\n" +
                    "• Keep an anger journal\n" +
                    "• Notice patterns in your reactions\n" +
                    "• Identify early warning signs\n\n" +
                    "Once you know your triggers, you can develop better coping strategies! 🌟"
                )
            }
            
            // Gratitude/Thanks
            userMessage.contains("thank") || userMessage.contains("thanks") -> {
                listOf(
                    "😊 You're very welcome! I'm honored to support you on your journey:\n\n" +
                    "🌟 **Remember**:\n" +
                    "• It's completely normal to feel angry sometimes\n" +
                    "• The key is how we choose to respond\n" +
                    "• Every step toward better anger management is progress\n" +
                    "• You're doing great by seeking help\n\n" +
                    "💪 **Keep Going**:\n" +
                    "• Practice the techniques regularly\n" +
                    "• Be patient with yourself\n" +
                    "• Celebrate your small wins\n\n" +
                    "Feel free to reach out anytime you need support! 🌈",
                    
                    "💙 Thank you for trusting me with your feelings! Here's what to remember:\n\n" +
                    "❤️ **Self-Compassion**:\n" +
                    "• Your emotions are valid and important\n" +
                    "• Managing anger is a skill that improves with practice\n" +
                    "• You're not alone in this journey\n" +
                    "• Every moment of calm is a victory\n\n" +
                    "🚀 **Moving Forward**:\n" +
                    "• Continue practicing the techniques\n" +
                    "• Build your emotional resilience\n" +
                    "• Share your progress with others\n\n" +
                    "Keep up the amazing work! You're building a better future! 🌟"
                )
            }
            
            // Default/General Support
            else -> {
                listOf(
                    "💭 I hear you. Sometimes it's hard to put feelings into words. Let me help you:\n\n" +
                    "🎯 **Explore Together**:\n" +
                    "• Try a breathing exercise to feel more centered\n" +
                    "• Talk about what's bothering you in more detail\n" +
                    "• Learn some quick calming techniques\n" +
                    "• Get help with a specific situation\n\n" +
                    "🧘 **Modern Approaches**:\n" +
                    "• Digital wellness strategies\n" +
                    "• Work-life balance techniques\n" +
                    "• Relationship communication skills\n\n" +
                    "I'm here to listen and help you find what works best for you! 🌟",
                    
                    "🤗 I understand that emotions can be complex in today's world. Let me support you:\n\n" +
                    "🔍 **Understanding**:\n" +
                    "• Explore what might be causing your feelings\n" +
                    "• Find techniques that work for your specific situation\n" +
                    "• Practice calming exercises together\n" +
                    "• Develop better coping strategies\n\n" +
                    "💡 **Modern Solutions**:\n" +
                    "• Technology stress management\n" +
                    "• Social media wellness\n" +
                    "• Work-life integration\n\n" +
                    "What would be most helpful for you right now? 💪",
                    
                    "🌟 It's okay to not have all the words. Let's work together to:\n\n" +
                    "🧠 **Self-Discovery**:\n" +
                    "• Identify what you're feeling\n" +
                    "• Find the right tools for your situation\n" +
                    "• Practice techniques that bring you peace\n" +
                    "• Build your emotional resilience\n\n" +
                    "🎯 **Modern Wellness**:\n" +
                    "• Digital mindfulness practices\n" +
                    "• Stress management in the digital age\n" +
                    "• Building healthy relationships with technology\n\n" +
                    "I'm here to support you every step of the way! 🌈"
                )
            }
        }
        
        // Return a random response from the appropriate list
        return responses.random()
    }
    
    private fun isPositiveMessage(message: String): Boolean {
        val positiveWords = listOf(
            "happy", "joy", "excited", "great", "wonderful", "amazing", "fantastic", "excellent",
            "good", "nice", "beautiful", "peaceful", "calm", "relaxed", "content", "satisfied",
            "grateful", "thankful", "blessed", "lucky", "fortunate", "successful", "achieved",
            "accomplished", "proud", "confident", "optimistic", "hopeful", "inspired", "motivated",
            "energetic", "refreshed", "renewed", "healed", "better", "improved", "progress",
            "breakthrough", "victory", "win", "success", "celebration", "party", "fun", "enjoy",
            "love", "adore", "cherish", "appreciate", "value", "treasure", "smile", "laugh",
            "cheerful", "bright", "sunny", "positive", "upbeat", "enthusiastic", "passionate",
            "fulfilled", "complete", "whole", "balanced", "harmonious", "serene", "tranquil"
        )
        
        return positiveWords.any { word -> message.contains(word, ignoreCase = true) }
    }
    
    private fun isNegativeMessage(message: String): Boolean {
        val negativeWords = listOf(
            "angry", "mad", "furious", "rage", "irritated", "annoyed", "frustrated", "upset",
            "sad", "depressed", "miserable", "unhappy", "disappointed", "hurt", "pain", "suffering",
            "anxious", "worried", "stressed", "overwhelmed", "exhausted", "tired", "burned out",
            "hopeless", "helpless", "desperate", "lonely", "isolated", "rejected", "abandoned",
            "betrayed", "cheated", "lied to", "disrespected", "ignored", "unappreciated",
            "worthless", "useless", "stupid", "idiot", "failure", "loser", "hate", "despise",
            "terrible", "awful", "horrible", "disgusting", "nasty", "mean", "cruel", "evil",
            "scared", "afraid", "terrified", "panic", "fear", "dread", "nervous", "tense",
            "confused", "lost", "stuck", "trapped", "impossible", "difficult", "hard", "challenging",
            "problem", "issue", "trouble", "disaster", "crisis", "emergency", "urgent", "critical"
        )
        
        return negativeWords.any { word -> message.contains(word, ignoreCase = true) }
    }
    
    private fun getPositiveResponse(message: String): String {
        val responses = listOf(
            "🌟 That's absolutely wonderful! Your positive energy is contagious! Here's how to keep this amazing feeling going:\n\n" +
            "💫 **Celebrate Your Success**:\n" +
            "• Take a moment to really savor this feeling\n" +
            "• Share your joy with someone you care about\n" +
            "• Write down what made you feel this way\n\n" +
            "🌱 **Nurture Your Growth**:\n" +
            "• Use this positive energy to help others\n" +
            "• Channel it into creative activities\n" +
            "• Practice gratitude to amplify the feeling\n\n" +
            "✨ **Remember**: Happiness is a skill you're mastering! Keep shining! 🌟",
            
            "😊 Your happiness is beautiful to see! Here's how to make it last:\n\n" +
            "🎯 **Mindful Appreciation**:\n" +
            "• Notice the physical sensations of joy\n" +
            "• Take deep breaths and let the happiness fill you\n" +
            "• Express your gratitude for this moment\n\n" +
            "💝 **Spread the Joy**:\n" +
            "• Your positive mood can brighten someone else's day\n" +
            "• Consider doing something kind for others\n" +
            "• Share your positive energy with the world\n\n" +
            "🌈 **You're doing amazing!** Keep embracing these beautiful moments! 💖",
            
            "🎉 Fantastic! Your positive mindset is a superpower! Here's how to harness it:\n\n" +
            "🚀 **Channel Your Energy**:\n" +
            "• Use this momentum to tackle goals\n" +
            "• Inspire others with your positive attitude\n" +
            "• Create something beautiful or meaningful\n\n" +
            "🧘 **Mindful Happiness**:\n" +
            "• Practice mindful breathing to deepen the feeling\n" +
            "• Notice how your body feels when you're happy\n" +
            "• Remember this feeling for challenging times\n\n" +
            "💪 **You're building emotional resilience!** This is exactly what we work toward! 🌟"
        )
        
        return responses.random()
    }
    
    private fun getNegativeEmotionResponse(message: String): String {
        val responses = listOf(
            "💙 I hear you, and your feelings are completely valid. It's okay to not be okay. Let me help you through this:\n\n" +
            "🫂 **Immediate Support**:\n" +
            "• Take a deep breath - you're not alone\n" +
            "• Your feelings are temporary, even if they don't feel that way\n" +
            "• It's brave to acknowledge difficult emotions\n\n" +
            "🧘 **Gentle Self-Care**:\n" +
            "• Be kind to yourself right now\n" +
            "• Don't judge your feelings - they're messengers\n" +
            "• Remember: This too shall pass\n\n" +
            "💪 **You're stronger than you think.** Let's work through this together. 🌈",
            
            "🤗 I understand this is really hard right now. Your pain is real, and I'm here to support you:\n\n" +
            "🌱 **Gentle Healing**:\n" +
            "• Allow yourself to feel what you're feeling\n" +
            "• Don't rush to 'fix' it - healing takes time\n" +
            "• Practice self-compassion: 'I'm doing the best I can'\n\n" +
            "🛡️ **Protective Steps**:\n" +
            "• Reach out to someone you trust\n" +
            "• Consider professional support if needed\n" +
            "• Remember: Asking for help is strength, not weakness\n\n" +
            "💙 **You deserve support and understanding.** I'm here for you. 🌟",
            
            "💜 It sounds like you're going through something really difficult. Let me offer you some comfort:\n\n" +
            "🧘 **Grounding Exercise**:\n" +
            "• Take 3 slow, deep breaths\n" +
            "• Feel your feet on the ground\n" +
            "• Remind yourself: 'I am safe in this moment'\n\n" +
            "💝 **Self-Kindness**:\n" +
            "• Treat yourself like you would a dear friend\n" +
            "• Your feelings are important and deserve attention\n" +
            "• Healing is not linear - be patient with yourself\n\n" +
            "🌈 **You're not alone in this.** Every step forward, no matter how small, is progress. 💪"
        )
        
        return responses.random()
    }
    
    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }
    
    data class ChatMessage(
        val message: String,
        val isUser: Boolean,
        val timestamp: String
    )
    
    class ChatAdapter(private val messages: List<ChatMessage>) : 
        RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {
        
        companion object {
            private const val VIEW_TYPE_USER = 1
            private const val VIEW_TYPE_BOT = 2
        }
        
        override fun getItemViewType(position: Int): Int {
            return if (messages[position].isUser) VIEW_TYPE_USER else VIEW_TYPE_BOT
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            val layout = if (viewType == VIEW_TYPE_USER) {
                R.layout.item_message_user
            } else {
                R.layout.item_message_bot
            }
            
            val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
            return MessageViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
            val message = messages[position]
            holder.bind(message)
        }
        
        override fun getItemCount(): Int = messages.size
        
        class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val messageText: TextView = itemView.findViewById(R.id.messageText)
            private val timestampText: TextView = itemView.findViewById(R.id.timestampText)
            
            fun bind(message: ChatMessage) {
                messageText.text = message.message
                timestampText.text = message.timestamp
            }
        }
    }
} 