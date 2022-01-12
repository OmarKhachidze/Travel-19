package ge.bootcamp.travel19.ui.fragments.search_country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.CountriesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class SearchCountryViewModel @Inject constructor(private val countriesRepository: CountriesRepository) :
    ViewModel() {

    fun countries(name: String) = countriesRepository.getWantedCountry(name)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun allCountries() = countriesRepository.getEveryCountry()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())


}