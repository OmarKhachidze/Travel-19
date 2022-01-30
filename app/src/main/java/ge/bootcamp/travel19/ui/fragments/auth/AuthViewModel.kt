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
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserInfoRepository,
    private val localStore: DataStoreManager
) : ViewModel() {

    private val _authFormForm = MutableSharedFlow<AuthFormState>()
    val authFormState: SharedFlow<AuthFormState> = _authFormForm

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

    fun getUserInfo(token: String) = userRepository.getSelf(token)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading(null)
        )

    fun signInUser(login: UserInfo) = authRepository.logIn(login)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading(null)
        )

    fun signUpUser(user: UserInfo) = authRepository.signUp(user)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading(null)
        )

    suspend fun <T> saveTokenToDataStore(key: Preferences.Key<T>, value: T) {
        localStore.storeValue(key, value)
    }

    suspend fun getUserToken(key: Preferences.Key<String>) =
        localStore.readValue(key)


    fun signInDataChanged(email: String, password: String) {
        viewModelScope.launch {
            if (!email.isValidEmail()) {
                _authFormForm.emit(AuthFormState(emailError = R.string.invalid_email))
            } else if (!password.isValidPassword()) {
                _authFormForm.emit(AuthFormState(passwordError = R.string.invalid_password))
            } else
                _authFormForm.emit(AuthFormState(isDataValid = true))
        }
    }

    fun signUpDataChanged(
        fullName: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            if (fullName.isEmpty()) {
                _authFormForm.emit(AuthFormState(fullNameError = R.string.full_name_errror))
            } else if (!email.isValidEmail()) {
                _authFormForm.emit(AuthFormState(emailError = R.string.invalid_username))
            } else if (!password.isValidPassword()) {
                _authFormForm.emit(AuthFormState(passwordError = R.string.invalid_password))
            } else
                _authFormForm.emit(AuthFormState(isDataValid = true))
        }
    }
}