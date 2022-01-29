package ge.bootcamp.travel19.ui.activity

import android.os.SystemClock
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.datastore.DataStoreManager
import ge.bootcamp.travel19.utils.ConnectionListener
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localStore: DataStoreManager,
) : ViewModel() {
    companion object {
        const val WORK_DURATION = 1000L
    }
    @Inject
    lateinit var connectivityListener: ConnectionListener

    suspend fun getUserToken(key: Preferences.Key<String>): String? {
        return localStore.readValue(key)
    }

    suspend fun removeUserToken(key: Preferences.Key<String>) {
        localStore.removeValue(key)
    }

    private val initTime = SystemClock.uptimeMillis()
    fun isDataReady() = SystemClock.uptimeMillis() - initTime > WORK_DURATION

}