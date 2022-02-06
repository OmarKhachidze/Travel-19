package ge.bootcamp.travel19.ui.fragments.airport_restriction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.domain.model.airports.RestrictionByAirport
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.domain.use_cases.restrictions.RestrictionsUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AirportRestrictionsViewModel @Inject constructor(
    private val restrictionsUseCases: RestrictionsUseCases,
) : ViewModel() {

    fun getAirportRestrictions(restrictionParam: RestrictionByAirport) =
        restrictionsUseCases.getRestrictionsByAirportUseCase(restrictionParam).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading()
        )

}