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
class AirportRestrictionViewModel @Inject constructor(private val repository: RestrictionsRepository): ViewModel() {

    fun getAirports() = repository.getAllAirport()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun fetchRestrictionsByAirport(loc:String, dest: String) = repository.getRestrictionsByAirport(loc, dest)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
}