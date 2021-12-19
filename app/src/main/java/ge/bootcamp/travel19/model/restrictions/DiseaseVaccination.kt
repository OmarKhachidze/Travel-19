package ge.bootcamp.travel19.model.restrictions


import com.squareup.moshi.Json

data class DiseaseVaccination(
    @Json(name = "date")
    val date: String?,
    @Json(name = "exemptions")
    val exemptions: String?,
    @Json(name = "isRequired")
    val isRequired: String?,
    @Json(name = "policy")
    val policy: String?,
    @Json(name = "qualifiedVaccines")
    val qualifiedVaccines: List<String>?,
    @Json(name = "text")
    val text: String?
)