package ge.bootcamp.travel19.ui.fragments.search_country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.CountriesRepository
import ge.bootcamp.travel19.model.countriesv3.V3CountriesItem
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchCountryViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository
) :
    ViewModel() {

//    private val trigger = MutableStateFlow("")
//
//    fun setQuery(query: String) {
//        trigger.value = query
//    }
//
//    val results = trigger.filter { it.isNotEmpty() }.mapLatest { query ->
//        countriesRepository.getWantedCountry(query)
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000L),
//        initialValue = Resource.Loading(null)
//    )

    fun countries(name: String) = countriesRepository.getWantedCountry(name)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading(null)
        )


    val allCountries: StateFlow<Resource<List<V3CountriesItem>>> =
        countriesRepository.getAllCountry.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading(null)
        )

}