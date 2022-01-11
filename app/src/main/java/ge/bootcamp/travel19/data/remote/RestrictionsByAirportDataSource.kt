package ge.bootcamp.travel19.data.remote

import javax.inject.Inject

class RestrictionsByAirportDataSource @Inject constructor(private val api: RestrictionByAirportService){
    suspend fun getRestByAirport(loc: String, dest: String,
                                 nationality:String, vaccine:String) = api.getRestrictionByAirport(loc, dest, nationality, vaccine)
}