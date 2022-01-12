package ge.bootcamp.travel19.ui.fragments.airport_restriction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.RestrictionsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class AirportRestrictionsViewModel @Inject constructor(
        private val restrictionsRepository: RestrictionsRepository
) : ViewModel() {

    fun airportRestrictions(loc: String, dest: String, nationality: String, vaccine: String) = restrictionsRepository.getRestrictionsByAirportUserInfo(loc, dest, nationality, vaccine)
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

}