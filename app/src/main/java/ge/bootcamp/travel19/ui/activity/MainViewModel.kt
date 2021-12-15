package ge.bootcamp.travel19.ui.activity

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.RestrictionsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RestrictionsRepository) :
    ViewModel() {

    fun data(countryCode: String) = repository.getCovidRestrictions(countryCode)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    companion object {
        const val WORK_DURATION = 1000L
    }

    private val initTime = SystemClock.uptimeMillis()
    fun isDataReady() = SystemClock.uptimeMillis() - initTime > WORK_DURATION

}