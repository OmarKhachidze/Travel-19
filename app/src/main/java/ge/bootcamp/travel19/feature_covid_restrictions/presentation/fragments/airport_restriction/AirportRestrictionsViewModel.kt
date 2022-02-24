package ge.bootcamp.travel19.feature_covid_restrictions.presentation.fragments.airport_restriction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.RestrictionByAirport
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.restrictions.RestrictionsUseCases
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