package ge.bootcamp.travel19.feature_covid_restrictions.presentation.fragments.search_country

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.countriesv3.V3CountriesItem
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.country.CountryUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCountryViewModel @Inject constructor(
    private val countryUseCases: CountryUseCases
) :
    ViewModel() {

    private val _countryState = MutableSharedFlow<Resource<List<V3CountriesItem>>>()
    val countryState get() = _countryState.asSharedFlow()

    fun countries(text: Editable?) = viewModelScope.launch {
        delay(500)
        countryUseCases.getCountryByNameUseCase(text).collectLatest {
            _countryState.emit(it)
        }
    }

    val allCountries: StateFlow<Resource<List<V3CountriesItem>>> =
        countryUseCases.getAllCountryUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading()
        )

}