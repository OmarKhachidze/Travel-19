package ge.bootcamp.travel19.feature_covid_restrictions.presentation.fragments.profile

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.feature_covid_restrictions.data.local.DataStoreManager
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.user.UserUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val dataStore: DataStoreManager
) : ViewModel() {

    suspend fun getUserInfo(userToken: String) =
        userUseCases.getUserUseCase(userToken).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading()
        )

    suspend fun getUserToken(key: Preferences.Key<String>) =
        dataStore.readValue(key)


}