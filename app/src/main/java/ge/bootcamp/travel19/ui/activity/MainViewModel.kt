package ge.bootcamp.travel19.ui.activity

import android.os.SystemClock
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.RestrictionsRepository
import ge.bootcamp.travel19.datastore.DataStoreManager
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localStore: DataStoreManager
) : ViewModel() {
    companion object {
        const val WORK_DURATION = 1000L
    }

    suspend fun getUserToken(key: Preferences.Key<String>): String? {
        return localStore.readValue(key)
    }

    suspend fun removeUserToken(key: Preferences.Key<String>) {
        localStore.removeValue(key)
    }

    private val initTime = SystemClock.uptimeMillis()
    fun isDataReady() = SystemClock.uptimeMillis() - initTime > WORK_DURATION

}