package br.me.vitorcsouza.esteticapremium.ui.services

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.me.vitorcsouza.esteticapremium.data.model.Service
import br.me.vitorcsouza.esteticapremium.databinding.ItemServiceBinding

class ServiceAdapter(
    private var services: List<Service>,
    private val onServiceClick: (Service) -> Unit
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    class ServiceViewHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(service: Service, onServiceClick: (Service) -> Unit) {
            binding.tvServiceName.text = service.name
            binding.tvServicePrice.text = service.price
            binding.tvServiceDuration.text = service.duration
            
            binding.root.setOnClickListener {
                onServiceClick(service)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(services[position], onServiceClick)
    }

    override fun getItemCount(): Int = services.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newServices: List<Service>) {
        services = newServices
        this.notifyDataSetChanged()
    }
}
