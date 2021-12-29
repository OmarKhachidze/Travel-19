package ge.bootcamp.travel19.model.countries


import com.squareup.moshi.Json

data class Language(
    @Json(name = "iso639_1")
    val iso6391: String?,
    @Json(name = "iso639_2")
    val iso6392: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "nativeName")
    val nativeName: String?
)