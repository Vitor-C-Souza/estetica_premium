package br.me.vitorcsouza.esteticapremium.ui.booking

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.me.vitorcsouza.esteticapremium.R
import br.me.vitorcsouza.esteticapremium.databinding.ItemDateBinding

class DateAdapter(
    private val dates: List<DateModel>,
    private val onDateSelected: (DateModel) -> Unit
) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var selectedPosition = 2 // Defaulting to the one in the screenshot (Wednesday 17)

    inner class DateViewHolder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(date: DateModel, position: Int) {
            binding.tvDayName.text = date.dayName
            binding.tvDayNumber.text = date.dayNumber

            val isSelected = position == selectedPosition
            
            if (isSelected) {
                binding.cvDate.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.gold))
                binding.tvDayName.setTextColor(Color.WHITE)
                binding.tvDayNumber.setTextColor(Color.WHITE)
            } else {
                binding.cvDate.setCardBackgroundColor(Color.parseColor("#F8F8F8"))
                binding.tvDayName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray_text))
                binding.tvDayNumber.setTextColor(Color.parseColor("#2E3A59"))
            }

            binding.root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onDateSelected(date)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(dates[position], position)
    }

    override fun getItemCount(): Int = dates.size
}