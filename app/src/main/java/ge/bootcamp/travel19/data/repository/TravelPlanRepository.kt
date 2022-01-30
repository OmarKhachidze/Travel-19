package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.travel_plans.TravelPlansDataSource
import ge.bootcamp.travel19.model.airports.plans.PostTravelPlan
import ge.bootcamp.travel19.model.airports.plans.travlePlans.GetTravelPlaneResponse
import ge.bootcamp.travel19.model.airports.plans.travlePlans.TravelPlanResponse
import ge.bootcamp.travel19.model.token.Success
import ge.bootcamp.travel19.model.vaccines.Vaccines
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants.ERROR_JSON_NAME
import ge.bootcamp.travel19.utils.Constants.NO_INTERNET_CONNECTION
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


class TravelPlanRepository @Inject constructor(
    private val travelPlanApi: TravelPlansDataSource,
    private var connectionListener: ConnectionListener
) {

    fun getPlans(token: String): Flow<Resource<GetTravelPlaneResponse>> {
        return flow {
            emit(handleTravelPlanResponse { travelPlanApi.getTravelPlan(token) })
        }
    }

    fun postPlans(token: String, plan: PostTravelPlan): Flow<Resource<TravelPlanResponse>> {
        return flow {
            emit(handleTravelPlanResponse { travelPlanApi.postTravelPlan(token, plan) })
        }
    }

    fun deletePlans(planId: String, token: String): Flow<Resource<Success>> {
        return flow {
            emit(handleTravelPlanResponse { travelPlanApi.deleteTravelPlan(planId, token) })
        }
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
                    Resource.Error(jObjError.getString(ERROR_JSON_NAME))
                } else {
                    Resource.Error(result.message())
                }
            } else
                Resource.Error(NO_INTERNET_CONNECTION)
        } catch (e: Throwable) {
            Resource.Error(e.message.toString(), null)
        }
    }
}