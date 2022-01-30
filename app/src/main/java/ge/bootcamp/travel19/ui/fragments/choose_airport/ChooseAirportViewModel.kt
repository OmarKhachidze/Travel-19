package ge.bootcamp.travel19.ui.fragments.choose_airport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.TravelPlanRepository
import ge.bootcamp.travel19.model.airports.plans.PostTravelPlan
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChooseAirportViewModel @Inject constructor(val repository: TravelPlanRepository): ViewModel(){

    fun getTravelPlan(token:String) = repository.getPlans(token)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading(null)
        )

    fun postTravelPlan(token:String, plan: PostTravelPlan) = repository.postPlans(token, plan)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading(null)
        )}