package ge.bootcamp.travel19.model.country_restrictions


import com.squareup.moshi.Json

data class AreaVaccinated(
    @Json(name = "date")
    val date: String?,
    @Json(name = "percentage")
    val percentage: Double?,
    @Json(name = "vaccinationDoseStatus")
    val vaccinationDoseStatus: String?
)