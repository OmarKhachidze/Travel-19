package ge.bootcamp.travel19.model.restrictions_by_counntries


import com.squareup.moshi.Json

data class AreaVaccinated(
    @Json(name = "date")
    val date: String?,
    @Json(name = "percentage")
    val percentage: Double?,
    @Json(name = "vaccinationDoseStatus")
    val vaccinationDoseStatus: String?
)