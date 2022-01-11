package ge.bootcamp.travel19.ui.fragments.auth.home


data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)