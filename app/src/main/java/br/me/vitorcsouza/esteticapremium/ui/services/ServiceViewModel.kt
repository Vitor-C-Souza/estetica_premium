package br.me.vitorcsouza.esteticapremium.ui.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.esteticapremium.data.model.Service
import br.me.vitorcsouza.esteticapremium.data.repository.ServiceRepository
import kotlinx.coroutines.launch

class ServiceViewModel : ViewModel() {

    private val repository = ServiceRepository()

    private val _services = MutableLiveData<List<Service>>()
    val services: LiveData<List<Service>> = _services

    fun loadServices() {
        viewModelScope.launch {
            val result = repository.getServices()
            _services.value = result
        }
    }
}