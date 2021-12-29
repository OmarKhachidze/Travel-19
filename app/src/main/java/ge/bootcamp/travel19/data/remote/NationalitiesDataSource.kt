package ge.bootcamp.travel19.data.remote

import ge.bootcamp.travel19.data.remote.authentication.NationalityService
import javax.inject.Inject

class NationalitiesDataSource @Inject constructor(private val api: NationalityService){
    suspend fun getNationalities() = api.getNationalities()
}