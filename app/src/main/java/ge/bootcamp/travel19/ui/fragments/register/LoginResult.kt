package ge.bootcamp.travel19.ui.fragments.register

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null,
    val isLoading: Boolean? = true
)