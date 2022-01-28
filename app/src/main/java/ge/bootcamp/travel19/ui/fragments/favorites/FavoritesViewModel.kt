package ge.bootcamp.travel19.ui.fragments.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.TravelPlanRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: TravelPlanRepository): ViewModel() {

     fun getPlans(token: String) = repository.getPlans(token)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

     fun postPlans(token: String) = repository.postPlans(token)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
}