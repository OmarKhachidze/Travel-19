package ge.bootcamp.travel19.ui.fragments.register

import com.example.homework17.ui.ui.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null,
    val isLoading: Boolean? = true
)