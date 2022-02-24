package ge.bootcamp.travel19.ui.fragments.auth

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.datastore.DataStoreManager
import ge.bootcamp.travel19.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.domain.model.auth.UserInfo
import ge.bootcamp.travel19.domain.states.AuthFormState
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.domain.use_cases.auth.AuthUseCase
import ge.bootcamp.travel19.domain.use_cases.user.UserUseCases
import ge.bootcamp.travel19.extensions.isValidEmail
import ge.bootcamp.travel19.extensions.isValidPassword
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userUseCases: UserUseCases,
    private val dataStore: DataStoreManager
) : ViewModel() {

    private val _signInUserState = MutableStateFlow<Resource<AuthResponse>>(Resource.Empty())
    val signInUserState get() = _signInUserState.asStateFlow()

    private val _signUpUserState = MutableStateFlow<Resource<AuthResponse>>(Resource.Empty())
    val signUpUserState get() = _signUpUserState.asStateFlow()

    private val _authFormForm = MutableSharedFlow<AuthFormState>()
    val authFormState: SharedFlow<AuthFormState> = _authFormForm

    fun signInUser(userCredential: UserInfo) {
        viewModelScope.launch {
            authUseCase.signInUseCase(userCredential)
                .collectLatest {
                    _signInUserState.value = it
                }
        }
    }

    fun signUpUser(userCredential: UserInfo) {
        viewModelScope.launch {
            authUseCase.signUpUseCase(userCredential)
                .collectLatest {
                    _signUpUserState.value = it
                }
        }
    }

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

    suspend fun <T> saveTokenToDataStore(key: Preferences.Key<T>, value: T) {
        dataStore.storeValue(key, value)
    }

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