package ge.bootcamp.travel19.ui.fragments.country_restrictions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.RestrictionsRepository
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class CountryRestrictionsViewModel @Inject constructor(private val repository: RestrictionsRepository) :
    ViewModel() {

    fun data(countryCode: String) = repository.getCovidRestrictions(countryCode)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading(null)
        )

//    fun testData() = repository.getCovidRestrictionsTest()
//        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())


}