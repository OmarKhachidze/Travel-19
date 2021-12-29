package ge.bootcamp.travel19.data.remote

import javax.inject.Inject

class AirportsDataSource @Inject constructor(private val api: AirportsService){
    suspend fun fetchAirports() = api.getAirports()
}