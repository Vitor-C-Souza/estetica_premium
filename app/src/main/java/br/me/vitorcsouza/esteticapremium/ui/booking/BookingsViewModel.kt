package br.me.vitorcsouza.esteticapremium.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.esteticapremium.data.model.Booking
import br.me.vitorcsouza.esteticapremium.data.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingsViewModel : ViewModel() {
    private val repository = BookingRepository()

    private val _bookings = MutableLiveData<List<Booking>>()
    val bookings: LiveData<List<Booking>> = _bookings

    fun loadMyBookings() {
        viewModelScope.launch {
            val result = repository.getMyBookings()
            _bookings.value = result
        }
    }
}
