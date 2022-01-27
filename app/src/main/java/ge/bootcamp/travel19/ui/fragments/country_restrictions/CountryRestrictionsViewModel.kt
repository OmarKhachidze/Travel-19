package ge.bootcamp.travel19.ui.fragments.country_restrictions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.RestrictionsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


@HiltViewModel
class CountryRestrictionsViewModel @Inject constructor(private val repository: RestrictionsRepository) :
    ViewModel() {

    fun data(countryCode: String) = repository.getCovidRestrictions(countryCode)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun testData() = repository.getCovidRestrictionsTest()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())


}