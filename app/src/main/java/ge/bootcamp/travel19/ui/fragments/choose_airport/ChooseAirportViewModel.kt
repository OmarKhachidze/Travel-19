package ge.bootcamp.travel19.ui.fragments.choose_airport

import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.datastore.DataStoreManager
import ge.bootcamp.travel19.domain.model.airports.plans.*
import ge.bootcamp.travel19.domain.model.token.Success
import ge.bootcamp.travel19.domain.states.AirportFormState
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.domain.use_cases.travel_plan.TravelPlanUseCases
import ge.bootcamp.travel19.domain.use_cases.user.UserUseCases
import ge.bootcamp.travel19.utils.Constants.USER_TOKEN_KEY
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseAirportViewModel @Inject constructor(
    private val travelPlanUseCases: TravelPlanUseCases,
    private val userUseCases: UserUseCases,
    private val dataStore: DataStoreManager
) : ViewModel() {

    private val _savePlanState = MutableSharedFlow<Resource<TravelPlanResponse>>()
    val savePlanState: SharedFlow<Resource<TravelPlanResponse>> = _savePlanState

    private val _getPlanState = MutableSharedFlow<Resource<PlanList>>()
    val getPlanState: SharedFlow<Resource<PlanList>> = _getPlanState

    private val _deletePlanState = MutableSharedFlow<Resource<Success>>()
    val deletePlanState: SharedFlow<Resource<Success>> = _deletePlanState

    private val _airportsFormForm = MutableSharedFlow<AirportFormState>()
    val airportsFormState: SharedFlow<AirportFormState> = _airportsFormForm

    suspend fun <T> readUserInfo(key: Preferences.Key<T>) = dataStore.readValue(key)

    val nationalities = userUseCases.getNationalitiesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Resource.Loading()
    )

    val vaccines = userUseCases.getVaccinesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Resource.Loading()
    )

    val airports =
        userUseCases.getAirportsUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading()
        )

    fun getTravelPlan() {
        viewModelScope.launch {
            Log.d("user token", " awdawdawd ")
            readUserInfo(stringPreferencesKey(USER_TOKEN_KEY))?.let { token ->
                Log.d("user token", " $token ")
                travelPlanUseCases.getTravelPlanUseCase(token).collectLatest {
                    _getPlanState.emit(it)
                }
            }
        }
    }

    fun saveTravelPlan(plan: SaveTravelPlan) {
        viewModelScope.launch {
            readUserInfo(stringPreferencesKey(USER_TOKEN_KEY))?.let { token ->
                travelPlanUseCases.saveTravelPlanUseCase(token, plan).collectLatest {
                    _savePlanState.emit(it)
                }
            }
        }
    }

    fun deleteTravelPlan(planId: String) {
        viewModelScope.launch {
            readUserInfo(stringPreferencesKey(USER_TOKEN_KEY))?.let { token ->
                travelPlanUseCases.deleteTravelPlanUseCase(planId, token).collectLatest {
                    _deletePlanState.emit(it)
                }
            }
        }
    }

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

}