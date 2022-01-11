package ge.bootcamp.travel19.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.AirportsRepository
import ge.bootcamp.travel19.data.repository.RestrictionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@HiltViewModel
class AirportsViewModel @Inject constructor(private val repository: AirportsRepository,
                                            private val vaccinesRepo: RestrictionsRepository,
): ViewModel() {

    fun getAirports() = repository.getAllAirport()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun fetchRestrictionsByAirport(loc:String, dest: String,
                                   nationality: String, vaccine:String) = repository.getRestrictionsByAirport(loc, dest, nationality, vaccine)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun getVaccine() = vaccinesRepo.getVaccines()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun nationalities() = vaccinesRepo.getNationalities()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
}