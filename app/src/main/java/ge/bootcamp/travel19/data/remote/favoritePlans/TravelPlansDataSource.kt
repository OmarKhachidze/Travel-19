package ge.bootcamp.travel19.data.remote.favoritePlans

import ge.bootcamp.travel19.data.remote.countries.CountriesService
import javax.inject.Inject

class TravelPlansDataSource @Inject constructor(private val api: PlansService){
    suspend fun postTravelPlan(token: String) = api.postPlane(token)
    suspend fun getTravelPlan(token: String) = api.getTravelPlan(token)
}