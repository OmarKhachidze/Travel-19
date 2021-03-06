package ge.bootcamp.travel19.feature_covid_restrictions.data.repository

import ge.bootcamp.travel19.feature_covid_restrictions.data.remote_data_source.TravelPlansService
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans.*
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.token.Success
import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.TravelPlanRepository
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants.ERROR_JSON_NAME
import ge.bootcamp.travel19.utils.Constants.NO_INTERNET_CONNECTION
import retrofit2.Response


class TravelPlanRepositoryImpl(
    private val travelPlanApi: TravelPlansService,
    private var connectionListener: ConnectionListener
) : TravelPlanRepository {

    override suspend fun saveTravelPlan(
        token: String,
        planModel: TravelPlanModel
    ): Resource<TravelPlanResponse> {
        return handleTravelPlanResponse { travelPlanApi.saveTravelPlane(token, planModel) }
    }

    override suspend fun getTravelPlan(token: String): Resource<PlanList> {
        return handleTravelPlanResponse { travelPlanApi.getTravelPlan(token) }
    }

    override suspend fun updateTravelPlan(
        planId: String,
        newPlan: TravelPlanModel,
        userToken: String
    ): Resource<TravelPlanResponse> {
        return handleTravelPlanResponse {
            travelPlanApi.updateTravelPlan(
                planId,
                newPlan,
                userToken
            )
        }
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