package ge.bootcamp.travel19.ui.fragments.airport_restriction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.RestrictionsRepository
import ge.bootcamp.travel19.data.repository.UserInfoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class AirportRestrictionViewModel @Inject constructor(
        private val restrictionsRepository: RestrictionsRepository,
        private val userRepository: UserInfoRepository) : ViewModel() {

    fun getAirports() = restrictionsRepository.getAllAirport()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun fetchRestrictionsByAirport(loc: String,
                                   dest: String,
                                   nationality: String,
                                   vaccine: String) =
            restrictionsRepository.getRestrictionsByAirportUserInfo(loc, dest, nationality, vaccine)
                    .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun getVaccine() = userRepository.getVaccines()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun nationalities() = userRepository.getNationalities()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
}