package ge.bootcamp.travel19.feature_covid_restrictions.data.remote_data_source

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.token.Token
import ge.bootcamp.travel19.utils.Keys
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OAuthService {
    @FormUrlEncoded
    @POST("${BuildConfig.AMADEUS_BASE_URL}security/oauth2/token")
    suspend fun getRestrictionsAccessToken(
        @Field("grant_type") type: String = "client_credentials",
        @Field("client_id") key: String = Keys.clientId(),
        @Field("client_secret") secret: String = Keys.clientSecret()
    ): Response<Token>
}
