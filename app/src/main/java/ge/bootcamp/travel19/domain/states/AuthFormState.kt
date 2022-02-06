package ge.bootcamp.travel19.domain.states


data class AuthFormState(
        val fullNameError: Int? = null,
        val emailError: Int? = null,
        val passwordError: Int? = null,
        val isDataValid: Boolean = false
)