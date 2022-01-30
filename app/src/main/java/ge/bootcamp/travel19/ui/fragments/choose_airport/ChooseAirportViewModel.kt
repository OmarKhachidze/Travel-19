package ge.bootcamp.travel19.ui.fragments.choose_airport

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.data.repository.TravelPlanRepository
import ge.bootcamp.travel19.data.repository.UserInfoRepository
import ge.bootcamp.travel19.datastore.DataStoreManager
import ge.bootcamp.travel19.model.airports.plans.PostTravelPlan
import ge.bootcamp.travel19.utils.AirportFormState
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseAirportViewModel @Inject constructor(
    private val travelPlanRepository: TravelPlanRepository,
    private val userRepository: UserInfoRepository,
    private val dataStore: DataStoreManager
) :
    ViewModel() {

    private val _airportsFormForm = MutableSharedFlow<AirportFormState>()
    val airportsFormState: SharedFlow<AirportFormState> = _airportsFormForm

    suspend fun <T> readUserInfo(key: Preferences.Key<T>) = dataStore.readValue(key)

    val vaccines = userRepository.getVaccines.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Resource.Loading(null)
    )
    val nationalities =
        userRepository.getNationalities.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading(null)
        )
    val airports =
        userRepository.getAllAirport.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading(null)
        )

    fun airportsDataChanged(location: String, destination: String) {
        viewModelScope.launch {
            when {
                location.isEmpty() -> {
                    _airportsFormForm.emit(AirportFormState(location = R.string.invalid_location))
                }
                destination.isEmpty() -> {
                    _airportsFormForm.emit(AirportFormState(destination = R.string.invalid_destination))
                }
                else -> _airportsFormForm.emit(AirportFormState(isDataValid = true))
            }
        }
    }

    fun getTravelPlan(token: String) = travelPlanRepository.getPlans(token)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading(null)
        )

    fun postTravelPlan(token: String, plan: PostTravelPlan) =
        travelPlanRepository.postPlans(token, plan)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = Resource.Loading(null)
            )

    fun deleteTravelPlan(planId: String, token: String) =
        travelPlanRepository.deletePlans(planId, token)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = Resource.Loading(null)
            )

}