package br.me.vitorcsouza.esteticapremium.ui.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.me.vitorcsouza.esteticapremium.data.model.Booking
import br.me.vitorcsouza.esteticapremium.databinding.ItemBookingBinding

class BookingsAdapter(
    private var bookings: List<Booking>
) : RecyclerView.Adapter<BookingsAdapter.BookingViewHolder>() {

    inner class BookingViewHolder(private val binding: ItemBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(booking: Booking) {
            binding.tvServiceName.text = booking.serviceName
            binding.tvProfessionalName.text = booking.professionalName
            binding.tvDate.text = booking.date
            binding.tvTime.text = booking.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemBookingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.bind(bookings[position])
    }

    override fun getItemCount(): Int = bookings.size

    fun updateList(newList: List<Booking>) {
        bookings = newList
        notifyDataSetChanged()
    }
}
