package br.me.vitorcsouza.esteticapremium.ui.professionals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.me.vitorcsouza.esteticapremium.data.model.Professional
import br.me.vitorcsouza.esteticapremium.databinding.ItemProfessionalBinding

class ProfessionalAdapter(
    private val professionals: List<Professional>,
    private val onProfessionalClick: (Professional) -> Unit
) : RecyclerView.Adapter<ProfessionalAdapter.ProfessionalViewHolder>() {

    inner class ProfessionalViewHolder(private val binding: ItemProfessionalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(professional: Professional) {
            binding.tvInitials.text = professional.initials
            binding.tvProfessionalName.text = professional.name
            binding.tvSpecialty.text = professional.specialty
            binding.tvRating.text = professional.rating.toString()
            
            binding.root.setOnClickListener {
                onProfessionalClick(professional)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessionalViewHolder {
        val binding = ItemProfessionalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProfessionalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfessionalViewHolder, position: Int) {
        holder.bind(professionals[position])
    }

    override fun getItemCount(): Int = professionals.size
}