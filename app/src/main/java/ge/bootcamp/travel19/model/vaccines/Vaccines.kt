package ge.bootcamp.travel19.model.vaccines

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Vaccines (
    var success: Boolean?,
    var vaccines: List<String>
)