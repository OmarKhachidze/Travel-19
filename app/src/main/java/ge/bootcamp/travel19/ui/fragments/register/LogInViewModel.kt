package ge.bootcamp.travel19.ui.fragments.register

import android.util.Log
import android.util.Patterns
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.data.repository.AuthRepository
import ge.bootcamp.travel19.data.repository.RestrictionsRepository
import ge.bootcamp.travel19.data.repository.UserInfoRepository
import ge.bootcamp.travel19.datastore.DataStoreManager
import ge.bootcamp.travel19.model.logIn.LoginRequest
import ge.bootcamp.travel19.model.singup.UserInfo
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val authRepository: AuthRepository,
    private val localStore: DataStoreManager
) : ViewModel() {

    suspend fun saveTokenToDataStore(key: Preferences.Key<String>, value: String) {
        localStore.storeValue(key, value)
    }

    fun data() = userInfoRepository.getVaccines()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun nationalities() = userInfoRepository.getNationalities()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun postUser(userInfo: UserInfo) = authRepository.signUp(userInfo)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())


    fun logIn(login: LoginRequest) = authRepository.logIn(login)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _loginResult1 = MutableSharedFlow<LoginResult>()
    val loginResult1: SharedFlow<LoginResult> = _loginResult1

    private val _loginForm1 = MutableStateFlow<LoginFormState>(LoginFormState())
    val loginFormState1: StateFlow<LoginFormState> = _loginForm1

    /**      With livedata   **/
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)
        // can be launched in a separate asynchronous job
        val result = logIn(loginRequest)

        viewModelScope.launch {
            logIn(loginRequest).collect {
                Log.i("result", it.toString())
                when (it) {
                    is Resource.Success -> {
                        _loginResult1.emit(
                            LoginResult(
                                success = LoggedInUserView(displayName = it.data!!.user!!.email.toString()),
                                isLoading = false
                            )
                        )
                    }
                    is Resource.Error -> {
                        _loginResult1.emit(
                            LoginResult(
                                error = R.string.login_failed,
                                isLoading = false
                            )
                        )
                    }
                    else -> {

                    }
                }
            }
        }


        /*
        if (result is Resource.Success<*>) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.toString())) ///es shesacvlelia
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        } */
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm1.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm1.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm1.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}