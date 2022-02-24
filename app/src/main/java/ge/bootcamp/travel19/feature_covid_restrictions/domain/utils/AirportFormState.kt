package ge.bootcamp.travel19.feature_covid_restrictions.domain.utils


data class AirportFormState(
    val location: Int? = null,
    val destination: Int? = null,
    val isDataValid: Boolean = false
)