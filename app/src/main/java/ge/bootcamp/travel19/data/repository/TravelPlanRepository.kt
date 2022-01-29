package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.travel_plans.TravelPlansDataSource
import ge.bootcamp.travel19.model.airports.plans.PostTravelPlan
import ge.bootcamp.travel19.model.airports.plans.travlePlans.GetTravelPlaneResponse
import ge.bootcamp.travel19.model.airports.plans.travlePlans.TravelPlanResponse
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject


class TravelPlanRepository @Inject constructor(
    private val travelPlanApi: TravelPlansDataSource,
    private var connectionListener: ConnectionListener
) {

    fun getPlans(token: String): Flow<Resource<out GetTravelPlaneResponse>> {
        return flow {
            emit(Resource.Loading(null))
            emit(handleTravelPlanResponse { travelPlanApi.getTravelPlan(token) })
        }.flowOn(Dispatchers.IO)
    }

    fun postPlans(token: String, plan: PostTravelPlan): Flow<Resource<out TravelPlanResponse>> {
        return flow {
            emit(Resource.Loading(null))
            emit(handleTravelPlanResponse { travelPlanApi.postTravelPlan(token, plan) })
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun <M> handleTravelPlanResponse(
        request: suspend () -> Response<M>
    ): Resource<M> {
        return try {
            if (connectionListener.value == true) {
                val result = request.invoke()
                val body = result.body()
                if (result.isSuccessful && body != null) {
                    return Resource.Success(body)
                } else if (result.code() == 401) {
                    val jObjError =
                        org.json.JSONObject(result.errorBody()!!.charStream().readText())
                    Resource.Error(jObjError.getString("error"))
                } else {
                    Resource.Error(result.message())
                }
            } else
                Resource.Error("No internet connection!")
        } catch (e: Throwable) {
            Resource.Error(e.message.toString(), null)
        }
    }
}