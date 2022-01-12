package ge.bootcamp.travel19.ui.fragments.auth

import android.text.TextUtils
import android.util.Patterns
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.data.repository.AuthRepository
import ge.bootcamp.travel19.data.repository.UserInfoRepository
import ge.bootcamp.travel19.datastore.DataStoreManager
import ge.bootcamp.travel19.model.logIn.LoginRequest
import ge.bootcamp.travel19.model.singup.UserInfo
import ge.bootcamp.travel19.ui.fragments.auth.home.LoginFormState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserInfoRepository,
        private val localStore: DataStoreManager
) : ViewModel() {
    private val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 8 && Pattern.compile(PASSWORD_PATTERN)
                .matcher(password)
                .matches()
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email)
    }

    suspend fun saveTokenToDataStore(key: Preferences.Key<String>, value: String) {
        localStore.storeValue(key, value)
    }

    fun signInUser(login: LoginRequest) = authRepository.logIn(login)
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun signUpUser(user: UserInfo) = authRepository.signUp(user)
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    var vaccines = userRepository.getVaccines().shareIn(viewModelScope, SharingStarted.WhileSubscribed())
    var nationalities = userRepository.getNationalities().shareIn(viewModelScope, SharingStarted.WhileSubscribed())
    var airports = userRepository.getAllAirport().shareIn(viewModelScope, SharingStarted.WhileSubscribed())


}