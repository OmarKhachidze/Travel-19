package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.authentication.VaccineService
import javax.inject.Inject

class VaccineDataSource @Inject constructor(private val api: VaccineService){
    suspend fun getVaccines() = api.getVaccine()
}
