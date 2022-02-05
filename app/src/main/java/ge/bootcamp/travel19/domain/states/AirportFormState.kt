package ge.bootcamp.travel19.domain.states


data class AirportFormState(
    val location: Int? = null,
    val destination: Int? = null,
    val isDataValid: Boolean = false
)