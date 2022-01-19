package ge.bootcamp.travel19.model.airports.plans.travlePlans


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
    val user: String?
)