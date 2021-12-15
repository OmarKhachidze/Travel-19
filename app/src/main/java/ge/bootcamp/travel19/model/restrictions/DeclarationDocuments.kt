package ge.bootcamp.travel19.model.restrictions


import com.squareup.moshi.Json

data class DeclarationDocuments(
    @Json(name = "date")
    val date: String?,
    @Json(name = "documentRequired")
    val documentRequired: String?,
    @Json(name = "text")
    val text: String?
)