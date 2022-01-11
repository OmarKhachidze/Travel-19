package ge.bootcamp.travel19.ui.fragments.auth.register


data class RegisterFormState(
        val fullNameError: Int? = null,
        val emailError: Int? = null,
        val nationalityError: Int? = null,
        val vaccineError: Int? = null,
        val passwordError: Int? = null,
        val isRegisterDataValid: Boolean = false
)