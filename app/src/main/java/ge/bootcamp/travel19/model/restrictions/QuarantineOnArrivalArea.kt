package ge.bootcamp.travel19.model.restrictions


import com.squareup.moshi.Json

data class QuarantineOnArrivalArea(
    @Json(name = "areaType")
    val areaType: String?,
    @Json(name = "iataCode")
    val iataCode: String?
)