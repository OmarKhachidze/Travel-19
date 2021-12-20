package ge.bootcamp.travel19.model.countries


import com.squareup.moshi.Json

data class RegionalBloc(
    @Json(name = "acronym")
    val acronym: String?,
    @Json(name = "name")
    val name: String?
)