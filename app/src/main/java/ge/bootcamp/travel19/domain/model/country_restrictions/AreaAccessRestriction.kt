package ge.bootcamp.travel19.domain.model.country_restrictions


import com.squareup.moshi.Json

data class AreaAccessRestriction(
    @Json(name = "declarationDocuments")
    val declarationDocuments: DeclarationDocuments?,
    @Json(name = "diseaseTesting")
    val diseaseTesting: DiseaseTesting?,
    @Json(name = "diseaseVaccination")
    val diseaseVaccination: DiseaseVaccination?,
    @Json(name = "entry")
    val entry: Entry?,
    @Json(name = "exit")
    val exit: Exit?,
    @Json(name = "mask")
    val mask: Mask?,
    @Json(name = "otherRestriction")
    val otherRestriction: OtherRestriction?,
    @Json(name = "quarantineModality")
    val quarantineModality: QuarantineModality?,
    @Json(name = "tracingApplication")
    val tracingApplication: TracingApplication?,
    @Json(name = "transportation")
    val transportation: Transportation?
)