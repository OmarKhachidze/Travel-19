package ge.bootcamp.travel19.domain.model.airports.restrictionsbyairport


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Restricions(
    @Json(name = "GVA")
    val gVA: GVA?
)