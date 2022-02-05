package ge.bootcamp.travel19.domain.model.airports.plans


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TravelPlanResponse(
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "travelPlan")
    val travelPlan: TravelPlan?
)
