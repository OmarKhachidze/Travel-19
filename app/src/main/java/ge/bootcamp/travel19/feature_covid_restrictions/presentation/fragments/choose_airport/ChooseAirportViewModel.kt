package ge.bootcamp.travel19.feature_covid_restrictions.presentation.fragments.choose_airport

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.feature_covid_restrictions.data.local.DataStoreManager
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans.PlanList
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans.TravelPlanModel
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans.TravelPlanResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.token.Success
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.AirportFormState
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.travel_plan.TravelPlanUseCases
import ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.user.UserUseCases
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

    private val _updatePlanState = MutableSharedFlow<Resource<TravelPlanResponse>>()
    val updatePlanState: SharedFlow<Resource<TravelPlanResponse>> = _updatePlanState

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
            val userToken = readUserInfo(stringPreferencesKey(USER_TOKEN_KEY))
            if (userToken != null) {
                travelPlanUseCases.getTravelPlanUseCase(userToken).collectLatest {
                    _getPlanState.emit(it)
                }
            } else {
                _getPlanState.emit(Resource.Empty())
            }
        }
    }

    fun saveTravelPlan(planModel: TravelPlanModel) {
        viewModelScope.launch {
            val userToken = readUserInfo(stringPreferencesKey(USER_TOKEN_KEY))
            if (userToken != null)
                travelPlanUseCases.saveTravelPlanUseCase(userToken, planModel).collectLatest {
                    _savePlanState.emit(it)
                } else {
                _savePlanState.emit(Resource.Empty())
            }
        }
    }

    fun updateTravelPlan(planId: String, planModel: TravelPlanModel) {
        viewModelScope.launch {
            val userToken = readUserInfo(stringPreferencesKey(USER_TOKEN_KEY))
            if (userToken != null)
                travelPlanUseCases.updateTravelPlanUseCase(planId, planModel, userToken)
                    .collectLatest {
                        _updatePlanState.emit(it)
                    } else {
                _updatePlanState.emit(Resource.Empty())
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