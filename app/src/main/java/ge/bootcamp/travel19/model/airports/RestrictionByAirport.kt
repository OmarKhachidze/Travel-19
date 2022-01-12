package ge.bootcamp.travel19.model.airports

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestrictionByAirport(
        val location: String,
        val destination: String,
        val vaccine: String,
        val nationality: String
): Parcelable