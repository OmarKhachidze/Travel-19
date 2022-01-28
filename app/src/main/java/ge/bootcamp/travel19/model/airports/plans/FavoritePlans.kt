package ge.bootcamp.travel19.model.airports.plans

data class PostTravelPlan(
    val source: String?,
    val destination: String?,
    val date: String?,
    val vaccine: String?,
    val nationality: String?,
    val transfer: String?
    ) {
}