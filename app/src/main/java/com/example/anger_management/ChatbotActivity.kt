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

class ChatbotActivity : AppCompatActivity() {
    
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
    
    private fun addWelcomeMessage() {
        val welcomeMessage = ChatMessage(
            "Hello! I'm your anger management assistant. I'm here to help you with:\n\n" +
            "• Breathing exercises\n" +
            "• Coping strategies\n" +
            "• Stress relief techniques\n" +
            "• Understanding your emotions\n\n" +
            "How can I help you today?",
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
        val botResponse = generateBotResponse(userMessage.lowercase())
        val botMessage = ChatMessage(botResponse, false, getCurrentTime())
        
        // Add slight delay for realistic chat experience
        recyclerView.postDelayed({
            messages.add(botMessage)
            adapter.notifyItemInserted(messages.size - 1)
            recyclerView.scrollToPosition(messages.size - 1)
        }, 500)
    }
    
    private fun generateBotResponse(userMessage: String): String {
        val responses = when {
            // Traffic/Commute Anger
            userMessage.contains("traffic") || userMessage.contains("road") || userMessage.contains("driver") || userMessage.contains("commute") -> {
                listOf(
                    "🚗 Traffic can be incredibly frustrating! Try this:\n\n" +
                    "• Put on calming music or a podcast\n" +
                    "• Practice deep breathing at red lights\n" +
                    "• Remind yourself that getting angry won't make traffic move faster\n" +
                    "• Use the time to call a friend or plan your day\n\n" +
                    "Remember: You'll get there when you get there!",
                    
                    "😤 Road rage is common but dangerous. Here's what to do:\n\n" +
                    "• Turn on relaxing music\n" +
                    "• Take 3 deep breaths at each stop\n" +
                    "• Focus on the scenery instead of other drivers\n" +
                    "• Leave 10 minutes earlier to avoid time pressure\n\n" +
                    "Your safety is more important than being on time!"
                )
            }
            
            // Work/Professional Anger
            userMessage.contains("work") || userMessage.contains("boss") || userMessage.contains("colleague") || userMessage.contains("meeting") || userMessage.contains("deadline") -> {
                listOf(
                    "💼 Work stress can be overwhelming. Try these techniques:\n\n" +
                    "• Take a 5-minute walk outside\n" +
                    "• Practice the 4-7-8 breathing technique\n" +
                    "• Write down your frustrations in a private note\n" +
                    "• Use positive self-talk: 'I can handle this'\n\n" +
                    "Remember: This is temporary, and you're capable!",
                    
                    "🏢 Workplace conflicts are challenging. Here's how to cope:\n\n" +
                    "• Step away from the situation for 10 minutes\n" +
                    "• Focus on solutions, not problems\n" +
                    "• Practice active listening when others speak\n" +
                    "• Set healthy boundaries with colleagues\n\n" +
                    "Your mental health matters more than any job!"
                )
            }
            
            // Family/Relationship Anger
            userMessage.contains("family") || userMessage.contains("partner") || userMessage.contains("spouse") || userMessage.contains("child") || userMessage.contains("parent") -> {
                listOf(
                    "👨‍👩‍👧‍👦 Family conflicts can be deeply emotional. Try this:\n\n" +
                    "• Take a timeout - go to another room for 10 minutes\n" +
                    "• Write down your feelings before responding\n" +
                    "• Use 'I feel' statements instead of blaming\n" +
                    "• Remember: Love is more important than being right\n\n" +
                    "Sometimes the best response is silence and space.",
                    
                    "❤️ Relationship anger often comes from hurt feelings. Here's what helps:\n\n" +
                    "• Practice empathy - try to see their perspective\n" +
                    "• Use the 10-10-10 rule: Will this matter in 10 minutes, 10 months, 10 years?\n" +
                    "• Take deep breaths before responding\n" +
                    "• Choose your battles wisely\n\n" +
                    "Love and understanding heal faster than anger."
                )
            }
            
            // Technology/Frustration Anger
            userMessage.contains("computer") || userMessage.contains("phone") || userMessage.contains("internet") || userMessage.contains("app") || userMessage.contains("technology") -> {
                listOf(
                    "💻 Tech frustration is real! Here's how to handle it:\n\n" +
                    "• Step away from the device for 5 minutes\n" +
                    "• Take 10 deep breaths\n" +
                    "• Remember: Technology is a tool, not worth your peace\n" +
                    "• Try a different approach or ask for help\n\n" +
                    "Your mental health is more important than any device!",
                    
                    "📱 Digital stress can be overwhelming. Try these steps:\n\n" +
                    "• Put your phone in another room for 30 minutes\n" +
                    "• Do something physical - stretch or walk\n" +
                    "• Remember: The internet will still be there later\n" +
                    "• Focus on what you can control\n\n" +
                    "Sometimes the best tech solution is to unplug!"
                )
            }
            
            // Financial Stress Anger
            userMessage.contains("money") || userMessage.contains("bill") || userMessage.contains("financial") || userMessage.contains("expensive") || userMessage.contains("cost") -> {
                listOf(
                    "💰 Financial stress can trigger intense emotions. Here's what helps:\n\n" +
                    "• Take a moment to breathe deeply\n" +
                    "• Focus on what you can control right now\n" +
                    "• Make a simple plan - even small steps help\n" +
                    "• Remember: Money problems are temporary\n\n" +
                    "Your worth is not measured by your bank account!",
                    
                    "💳 Money worries are common and valid. Try this approach:\n\n" +
                    "• Write down your financial concerns\n" +
                    "• Break problems into smaller, manageable pieces\n" +
                    "• Focus on one step at a time\n" +
                    "• Practice gratitude for what you do have\n\n" +
                    "Financial stress is temporary - your peace of mind is priceless!"
                )
            }
            
            // General Anger/Stress
            userMessage.contains("angry") || userMessage.contains("mad") || userMessage.contains("furious") || userMessage.contains("rage") -> {
                listOf(
                    "😤 I understand you're feeling angry. Let's try some immediate techniques:\n\n" +
                    "🔥 Quick Anger Relief:\n" +
                    "• Take 5 deep breaths - inhale for 4, hold for 4, exhale for 4\n" +
                    "• Count backwards from 10 to 1 slowly\n" +
                    "• Step away from the situation for 5 minutes\n" +
                    "• Drink a glass of cold water\n\n" +
                    "Would you like me to guide you through a specific technique?",
                    
                    "⚡ Anger is a natural emotion, but we can manage it. Try this:\n\n" +
                    "🧘‍♀️ Immediate Calming Steps:\n" +
                    "• Close your eyes and take 3 deep breaths\n" +
                    "• Tense and relax your muscles from toes to head\n" +
                    "• Repeat a calming phrase: 'I am in control'\n" +
                    "• Focus on something pleasant for 30 seconds\n\n" +
                    "You have the power to choose your response!"
                )
            }
            
            // Breathing/Calming Requests
            userMessage.contains("breath") || userMessage.contains("breathe") || userMessage.contains("calm") -> {
                listOf(
                    "🌬️ Let's do a powerful breathing exercise:\n\n" +
                    "4-7-8 Breathing Technique:\n" +
                    "• Inhale through your nose for 4 counts\n" +
                    "• Hold your breath for 7 counts\n" +
                    "• Exhale through your mouth for 8 counts\n\n" +
                    "Repeat this 4 times. Feel the tension leaving your body with each breath.",
                    
                    "🫁 Here's a quick calming breath pattern:\n\n" +
                    "Box Breathing:\n" +
                    "• Inhale for 4 counts\n" +
                    "• Hold for 4 counts\n" +
                    "• Exhale for 4 counts\n" +
                    "• Hold for 4 counts\n\n" +
                    "Repeat 5 times. This creates a sense of balance and calm."
                )
            }
            
            // Stress/Overwhelm
            userMessage.contains("stress") || userMessage.contains("overwhelm") || userMessage.contains("pressure") -> {
                listOf(
                    "😰 Stress can feel overwhelming. Here's immediate relief:\n\n" +
                    "🧘 Progressive Muscle Relaxation:\n" +
                    "• Tense your toes for 5 seconds, then relax\n" +
                    "• Move up to calves, thighs, stomach, arms, face\n" +
                    "• Feel the tension release with each relaxation\n\n" +
                    "This technique helps your body and mind relax together.",
                    
                    "🌊 When stress feels like a wave, try this:\n\n" +
                    "🌸 5-4-3-2-1 Grounding Exercise:\n" +
                    "• Name 5 things you can see\n" +
                    "• Name 4 things you can touch\n" +
                    "• Name 3 things you can hear\n" +
                    "• Name 2 things you can smell\n" +
                    "• Name 1 thing you can taste\n\n" +
                    "This brings you back to the present moment."
                )
            }
            
            // Help/General Support
            userMessage.contains("help") || userMessage.contains("what can you do") -> {
                listOf(
                    "🤖 I'm your anger management assistant! I can help with:\n\n" +
                    "📝 Specific coping strategies for different situations\n" +
                    "🫁 Guided breathing exercises\n" +
                    "🧠 Mindfulness and grounding techniques\n" +
                    "💪 Stress relief methods\n" +
                    "📊 Understanding your anger triggers\n\n" +
                    "Just tell me what's bothering you - I'm here to listen and help!",
                    
                    "💙 I'm here to support your emotional well-being. I can offer:\n\n" +
                    "🎯 Situation-specific anger management techniques\n" +
                    "🌬️ Various breathing exercises\n" +
                    "🧘‍♀️ Relaxation and mindfulness practices\n" +
                    "💡 Coping strategies for different triggers\n" +
                    "📚 Educational content about anger management\n\n" +
                    "What would you like to work on today?"
                )
            }
            
            // Triggers/Understanding Anger
            userMessage.contains("trigger") || userMessage.contains("what makes me angry") || userMessage.contains("why am i angry") -> {
                listOf(
                    "🔍 Understanding your triggers is a great step! Common anger triggers include:\n\n" +
                    "• Feeling disrespected or ignored\n" +
                    "• Traffic or delays\n" +
                    "• Criticism or judgment\n" +
                    "• Feeling out of control\n" +
                    "• Fatigue, hunger, or physical discomfort\n" +
                    "• Unmet expectations\n\n" +
                    "What specific situation triggered your anger today?",
                    
                    "🎯 Identifying triggers helps you prepare better responses. Common triggers:\n\n" +
                    "• Work stress and deadlines\n" +
                    "• Family conflicts\n" +
                    "• Technology frustrations\n" +
                    "• Financial worries\n" +
                    "• Health concerns\n" +
                    "• Social situations\n\n" +
                    "Once you know your triggers, you can develop better coping strategies!"
                )
            }
            
            // Gratitude/Thanks
            userMessage.contains("thank") -> {
                listOf(
                    "😊 You're very welcome! I'm glad I could help. Remember:\n\n" +
                    "• It's completely normal to feel angry sometimes\n" +
                    "• The key is how we choose to respond\n" +
                    "• Every step toward better anger management is progress\n" +
                    "• You're doing great by seeking help\n\n" +
                    "Feel free to reach out anytime you need support!",
                    
                    "💙 Thank you for trusting me with your feelings! Here's what to remember:\n\n" +
                    "• Your emotions are valid and important\n" +
                    "• Managing anger is a skill that improves with practice\n" +
                    "• You're not alone in this journey\n" +
                    "• Every moment of calm is a victory\n\n" +
                    "Keep up the great work! 🌟"
                )
            }
            
            // Default/General Support
            else -> {
                listOf(
                    "💭 I hear you. Sometimes it's hard to put feelings into words. Would you like to:\n\n" +
                    "• Try a breathing exercise to help you feel more centered?\n" +
                    "• Talk about what's bothering you in more detail?\n" +
                    "• Learn some quick calming techniques?\n" +
                    "• Get help with a specific situation?\n\n" +
                    "I'm here to listen and help you find what works best for you.",
                    
                    "🤗 I understand that emotions can be complex. Let me help you:\n\n" +
                    "• Explore what might be causing your feelings\n" +
                    "• Find techniques that work for your specific situation\n" +
                    "• Practice calming exercises together\n" +
                    "• Develop better coping strategies\n\n" +
                    "What would be most helpful for you right now?",
                    
                    "🌟 It's okay to not have all the words. Let's work together to:\n\n" +
                    "• Identify what you're feeling\n" +
                    "• Find the right tools for your situation\n" +
                    "• Practice techniques that bring you peace\n" +
                    "• Build your emotional resilience\n\n" +
                    "I'm here to support you every step of the way."
                )
            }
        }
        
        // Return a random response from the appropriate list
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