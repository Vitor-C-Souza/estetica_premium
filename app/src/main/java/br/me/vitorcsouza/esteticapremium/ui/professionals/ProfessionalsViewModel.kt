package br.me.vitorcsouza.esteticapremium.ui.professionals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.esteticapremium.data.model.Professional
import br.me.vitorcsouza.esteticapremium.data.repository.ProfessionalRepository
import kotlinx.coroutines.launch

class ProfessionalsViewModel : ViewModel() {

    private val repository = ProfessionalRepository()

    private val _professionals = MutableLiveData<List<Professional>>()
    val professionals: LiveData<List<Professional>> = _professionals

    fun loadProfessionals() {
        viewModelScope.launch {
            _professionals.value = repository.getProfessionals()
        }
    }
}
