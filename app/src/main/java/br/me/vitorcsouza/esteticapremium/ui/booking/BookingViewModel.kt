package br.me.vitorcsouza.esteticapremium.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.esteticapremium.data.model.Booking
import br.me.vitorcsouza.esteticapremium.data.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {
    private val repository = BookingRepository()

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> = _isSaved

    fun confirmBooking(service: String, professional: String, date: String, time: String) {
        viewModelScope.launch {
            val booking = Booking(
                userId = "",
                serviceName = service,
                professionalName = professional,
                date = date,
                time = time
            )
            _isSaved.value = repository.saveBooking(booking)
        }
    }
}