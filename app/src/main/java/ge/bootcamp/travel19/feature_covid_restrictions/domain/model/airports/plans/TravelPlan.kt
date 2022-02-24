package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TravelPlan(
    @Json(name = "date")
    val date: String?,
    @Json(name = "destination")
    val destination: String?,
    @Json(name = "_id")
    val id: String?,
    @Json(name = "source")
    val source: String?,
    @Json(name = "user")
    val user: String?,
    @Json(name = "vaccine")
    val vaccine: String?,
    @Json(name = "nationality")
    val nationality: String?,
)

data class TravelPlanModel(
    val source: String?,
    val destination: String?,
    val vaccine: String?,
    val nationality: String?,
    val transfer: String? = null
)