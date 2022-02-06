package ge.bootcamp.travel19.ui.activity

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

    @Inject
    lateinit var connectivityListener: ConnectionListener

    suspend fun getUserToken(key: Preferences.Key<String>): String? {
        return localStore.readValue(key)
    }

    suspend fun <T> removeUserToken(key: Preferences.Key<T>) {
        localStore.removeValue(key)
    }
}