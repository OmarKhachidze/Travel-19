package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans


import com.squareup.moshi.Json

data class PlanList(
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "travelPlans")
    val travelPlans: List<TravelPlan>
)