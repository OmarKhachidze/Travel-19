package ge.bootcamp.travel19.model.airports.plans

data class PostTravelPlan(
    val source: String?,
    val destination: String?,
    val vaccine: String? = null,
    val nationality: String? = null,
    val transfer: String? = null
    ) {
}