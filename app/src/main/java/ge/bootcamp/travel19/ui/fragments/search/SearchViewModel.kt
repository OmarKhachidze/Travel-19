package ge.bootcamp.travel19.ui.fragments.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.CountriesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val countriesRepository: CountriesRepository) : ViewModel() {

    fun countries(name: String) = countriesRepository.getWantedCountry(name)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

}