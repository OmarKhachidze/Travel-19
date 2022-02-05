package ge.bootcamp.travel19.ui.fragments.airport_restriction

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.restrictions.RestrictionsRepositoryImpl
import ge.bootcamp.travel19.datastore.DataStoreManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class AirportRestrictionsViewModel @Inject constructor(
//    private val restrictionsRepository: RestrictionsRepositoryImpl,
//    private val datastore: DataStoreManager,
) : ViewModel() {

//    fun airportRestrictions(loc: String, dest: String, nationality: String, vaccine: String) = restrictionsRepository.getRestrictionsByAirport(loc, dest, nationality, vaccine)
//            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
//
//    suspend fun checkTokenInDataStore(key: Preferences.Key<String>) {
//       val x = datastore.readValue(key)
//    }
}