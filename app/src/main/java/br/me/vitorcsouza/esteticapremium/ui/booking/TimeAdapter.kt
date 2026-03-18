package br.me.vitorcsouza.esteticapremium.ui.booking

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.me.vitorcsouza.esteticapremium.R
import br.me.vitorcsouza.esteticapremium.databinding.ItemTimeBinding

class TimeAdapter(
    private val times: List<TimeModel>,
    private val onTimeSelected: (TimeModel) -> Unit
) : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    private var selectedPosition = -1

    inner class TimeViewHolder(private val binding: ItemTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(time: TimeModel, position: Int) {
            binding.tvTime.text = time.time

            val isSelected = position == selectedPosition

            if (isSelected) {
                binding.cvTime.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.gold))
                binding.tvTime.setTextColor(Color.WHITE)
            } else {
                binding.cvTime.setCardBackgroundColor(Color.parseColor("#F8F8F8"))
                binding.tvTime.setTextColor(Color.parseColor("#2E3A59"))
            }

            binding.root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onTimeSelected(time)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding = ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(times[position], position)
    }

    override fun getItemCount(): Int = times.size
}