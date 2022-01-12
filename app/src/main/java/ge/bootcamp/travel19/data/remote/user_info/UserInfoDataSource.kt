package ge.bootcamp.travel19.data.remote.user_info

import javax.inject.Inject

class UserInfoDataSource @Inject constructor(private val api: UserInfoService){
    suspend fun getNationalities() = api.getNationalities()
    suspend fun getVaccines() = api.getVaccine()
    suspend fun fetchAirports() = api.getAirports()
}