package ge.bootcamp.travel19.model.airports.plans.travlePlans

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTravelPlaneResponse(
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "travelPlans")
    val travelPlans: List<TravelPlan>
) {
}