package ge.bootcamp.travel19.model.countries


import com.squareup.moshi.Json

data class Currency(
    @Json(name = "code")
    val code: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "symbol")
    val symbol: String?
)