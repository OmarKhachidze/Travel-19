package ge.bootcamp.travel19.ui.fragments.country_restrictions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.restrictions.RestrictionsRepositoryImpl
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.domain.use_cases.restrictions.RestrictionsUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class CountryRestrictionsViewModel @Inject constructor(
    private val restrictionsUseCases: RestrictionsUseCases
    ) :
    ViewModel() {

    fun data(countryCode: String) = restrictionsUseCases.getRestrictionsByCountryUseCase(countryCode)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading()
        )

}