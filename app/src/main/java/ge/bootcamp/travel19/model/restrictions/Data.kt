package ge.bootcamp.travel19.model.restrictions


import com.squareup.moshi.Json

data class Data(
    @Json(name = "area")
    val area: Area?,
    @Json(name = "areaAccessRestriction")
    val areaAccessRestriction: AreaAccessRestriction?,
    @Json(name = "areaPolicy")
    val areaPolicy: AreaPolicy?,
    @Json(name = "areaRestrictions")
    val areaRestrictions: List<AreaRestriction>?,
    @Json(name = "areaVaccinated")
    val areaVaccinated: List<AreaVaccinated>?,
    @Json(name = "dataSources")
    val dataSources: DataSources?,
    @Json(name = "diseaseCases")
    val diseaseCases: DiseaseCases?,
    @Json(name = "diseaseInfection")
    val diseaseInfection: DiseaseInfection?,
    @Json(name = "diseaseRiskLevel")
    val diseaseRiskLevel: String?,
    @Json(name = "hotspots")
    val hotspots: String?,
    @Json(name = "relatedArea")
    val relatedArea: List<RelatedArea>?,
    @Json(name = "summary")
    val summary: String?,
    @Json(name = "type")
    val type: String?
)