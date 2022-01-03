package ge.bootcamp.travel19.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.AirportsRepository
import ge.bootcamp.travel19.data.repository.RestrictionsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class AirportsViewModel @Inject constructor(private val repository: AirportsRepository,
                                            private val vaccinesRepo: RestrictionsRepository,
): ViewModel() {

    fun getAirports() = repository.getAllAirport()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun fetchRestrictionsByAirport(loc:String, dest: String) = repository.getRestrictionsByAirport(loc, dest)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun getVaccine() = vaccinesRepo.getVaccines()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun nationalities() = vaccinesRepo.getNationalities()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
}