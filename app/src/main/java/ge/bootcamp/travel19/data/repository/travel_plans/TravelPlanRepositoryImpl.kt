package ge.bootcamp.travel19.data.repository.travel_plans

import ge.bootcamp.travel19.data.remote.TravelPlansService
import ge.bootcamp.travel19.domain.model.airports.plans.*
import ge.bootcamp.travel19.domain.model.token.Success
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants.ERROR_JSON_NAME
import ge.bootcamp.travel19.utils.Constants.NO_INTERNET_CONNECTION
import retrofit2.Response


class TravelPlanRepositoryImpl (
    private val travelPlanApi: TravelPlansService,
    private var connectionListener: ConnectionListener
) : TravelPlanRepository {

    override suspend fun saveTravelPlan(
        token: String,
        plan: SaveTravelPlan
    ): Resource<TravelPlanResponse> {
        return handleTravelPlanResponse { travelPlanApi.saveTravelPlane(token, plan) }
    }

    override suspend fun getTravelPlan(token: String): Resource<PlanList> {
        return handleTravelPlanResponse { travelPlanApi.getTravelPlan(token) }
    }

    override suspend fun deleteTravelPlan(planId: String, token: String): Resource<Success> {
        return handleTravelPlanResponse { travelPlanApi.deleteTravelPlan(planId, token) }
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