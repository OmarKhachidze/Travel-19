package ge.bootcamp.travel19.ui.fragments.auth

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.data.repository.AuthRepository
import ge.bootcamp.travel19.data.repository.UserInfoRepository
import ge.bootcamp.travel19.datastore.DataStoreManager
import ge.bootcamp.travel19.extensions.isValidEmail
import ge.bootcamp.travel19.extensions.isValidPassword
import ge.bootcamp.travel19.model.auth.UserInfo
import ge.bootcamp.travel19.utils.AuthFormState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserInfoRepository,
        private val localStore: DataStoreManager
) : ViewModel() {

    private val _authFormForm = MutableSharedFlow<AuthFormState>()
    val authFormState: SharedFlow<AuthFormState> = _authFormForm

    var vaccines = userRepository.getVaccines().shareIn(viewModelScope, SharingStarted.WhileSubscribed())
    var nationalities = userRepository.getNationalities().shareIn(viewModelScope, SharingStarted.WhileSubscribed())
    var airports = userRepository.getAllAirport().shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun signInUser(login: UserInfo) = authRepository.logIn(login)
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun signUpUser(user: UserInfo) = authRepository.signUp(user)
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    suspend fun saveTokenToDataStore(key: Preferences.Key<String>, value: String) {
        localStore.storeValue(key, value)
    }

    suspend fun signInDataChanged(email: String, password: String) {
        if (!email.isValidEmail()) {
            _authFormForm.emit(AuthFormState(emailError = R.string.invalid_email))
        } else if (!password.isValidPassword()) {
            _authFormForm.emit(AuthFormState(passwordError = R.string.invalid_password))
        } else
            _authFormForm.emit(AuthFormState(isDataValid = true))
    }

    fun getUserInfo(token: String) = authRepository.getSelf(token)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    suspend fun signUpDataChanged(
            fullName: String,
            email: String,
            password: String
    ) {
        if (fullName.isEmpty()) {
            _authFormForm.emit(AuthFormState(fullNameError = R.string.full_name_errror))
        } else if (!email.isValidEmail()) {
            _authFormForm.emit(AuthFormState(emailError = R.string.invalid_username))
        } else if (!password.isValidPassword()) {
            _authFormForm.emit(AuthFormState(passwordError = R.string.invalid_password))
        } else
            _authFormForm.emit(AuthFormState(isDataValid = true))
    }


    suspend fun checkTokenInDataStore(key: Preferences.Key<String>): String? {
        return localStore.readValue(key)
    }
}