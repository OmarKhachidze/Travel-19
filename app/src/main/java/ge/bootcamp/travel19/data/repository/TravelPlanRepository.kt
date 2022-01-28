package ge.bootcamp.travel19.data.repository


import android.util.Log
import ge.bootcamp.travel19.data.remote.travel_plans.TravelPlansDataSource
import ge.bootcamp.travel19.model.airports.plans.PostTravelPlan
import ge.bootcamp.travel19.model.airports.plans.travlePlans.GetTravelPlaneResponse
import ge.bootcamp.travel19.model.airports.plans.travlePlans.TravelPlanResponse
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class TravelPlanRepository @Inject constructor(private val api: TravelPlansDataSource) {

    fun getPlans(token: String): Flow<Resource<out GetTravelPlaneResponse>> {
        return flow {
            try {
                emit(Resource.Loading(null))
                val result = api.getTravelPlan(token)
                val body = result.body()
                if (result.isSuccessful && body != null) {
                    emit(Resource.Success(body))
                } else if (result.code() == 401) {
                    val jObjError = org.json.JSONObject(result.errorBody()!!.charStream().readText())
                    emit(Resource.Error(jObjError.getString("error")))
                } else {
                    emit(Resource.Error(result.message(), null))
                }
            } catch (e: Throwable) {
                Log.i("onOtherErr", e.message.toString())
                emit(Resource.Error("Something went Wrong !"))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun postPlans(token: String, plan: PostTravelPlan): Flow<Resource<out TravelPlanResponse>> {
        return flow {
            try {
                emit(Resource.Loading(null))
                val result = api.postTravelPlan(token, plan)
                val body = result.body()
                if (result.isSuccessful && body != null) {
                    emit(Resource.Success(body))
                } else if (result.code() == 401) {
                    val jObjError = org.json.JSONObject(result.errorBody()!!.charStream().readText())
                    emit(Resource.Error(jObjError.getString("error")))
                } else {
                    emit(Resource.Error(result.message(), null))
                }
            } catch (e: Throwable) {
                Log.i("onOtherErr", e.message.toString())
                emit(Resource.Error("Something went Wrong !"))
            }
        }.flowOn(Dispatchers.IO)
    }
}