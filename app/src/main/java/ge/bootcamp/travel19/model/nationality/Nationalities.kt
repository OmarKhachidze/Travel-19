package ge.bootcamp.travel19.model.nationality

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Nationalities (
    var success: Boolean?,
    var nacionalities: List<String>
)