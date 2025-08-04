package com.example.anger_management

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.anger_management.R

data class SoundItem(
    val name: String,
    val duration: String,
    val backgroundColor: Int,
    val icon: Int
)

class SoundAdapter(
    private val sounds: List<SoundItem>,
    private val onSoundClick: (SoundItem) -> Unit
) : RecyclerView.Adapter<SoundAdapter.SoundViewHolder>() {

    class SoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.sound_card_item)
        val tvSoundName: TextView = itemView.findViewById(R.id.sound_title)
        val tvDuration: TextView = itemView.findViewById(R.id.sound_duration)
        val ivSoundIcon: ImageView = itemView.findViewById(R.id.ivSoundIcon)
        val btnPlay: ImageButton = itemView.findViewById(R.id.btnPlay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sound_card_item, parent, false)
        return SoundViewHolder(view)
    }

    override fun onBindViewHolder(holder: SoundViewHolder, position: Int) {
        val sound = sounds[position]
        
        holder.tvSoundName.text = sound.name
        holder.tvDuration.text = sound.duration
        holder.ivSoundIcon.setImageResource(sound.icon)
        holder.cardView.setCardBackgroundColor(sound.backgroundColor)
        
        holder.btnPlay.setOnClickListener {
            onSoundClick(sound)
        }
        
        holder.cardView.setOnClickListener {
            onSoundClick(sound)
        }
    }

    override fun getItemCount(): Int = sounds.size
} 