package ge.bootcamp.travel19.model.country_restrictions


import com.squareup.moshi.Json

data class CovidRestrictions(
    @Json(name = "data")
    val data: Data?,
    @Json(name = "meta")
    val meta: Meta?
)