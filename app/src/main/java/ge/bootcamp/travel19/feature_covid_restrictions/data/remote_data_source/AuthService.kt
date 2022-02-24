package ge.bootcamp.travel19.feature_covid_restrictions.data.remote_data_source

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {
    @POST("${BuildConfig.NOXTON_BASE_URL}/login")
    suspend fun logIn(@Body request: UserInfo): Response<AuthResponse>

    @POST("${BuildConfig.NOXTON_BASE_URL}/signup")
    suspend fun singUp(
        @Body request: UserInfo
    ): Response<AuthResponse>
}
