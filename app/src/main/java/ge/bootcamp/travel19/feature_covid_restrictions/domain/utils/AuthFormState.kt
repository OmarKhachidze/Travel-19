package ge.bootcamp.travel19.feature_covid_restrictions.domain.utils


data class AuthFormState(
        val fullNameError: Int? = null,
        val emailError: Int? = null,
        val passwordError: Int? = null,
        val isDataValid: Boolean = false
)