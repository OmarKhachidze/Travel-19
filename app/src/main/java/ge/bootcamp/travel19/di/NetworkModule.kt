package ge.bootcamp.travel19.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.data.remote.*
import ge.bootcamp.travel19.utils.OAuthInterceptor
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        OAuthInterceptor: OAuthInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(OAuthInterceptor).addInterceptor(loggingInterceptor)
            .dispatcher(Dispatcher().apply { maxRequests = 5 })
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideMoshiO(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit.Builder {
        return Retrofit.Builder().baseUrl("https://test.api.amadeus.com/v1/").client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    fun provideRestrictionsService(retrofit: Retrofit.Builder): RestrictionsService {
        return retrofit.build()
            .create(RestrictionsService::class.java)
    }

    @Singleton
    @Provides
    fun provideNationalitiesService(retrofit: Retrofit.Builder): NationalityService {
        return retrofit.build()
            .create(NationalityService::class.java)
    }

    @Singleton
    @Provides
    fun provideSignUpService(retrofit: Retrofit.Builder): SignUpService {
        return retrofit.build()
            .create(SignUpService::class.java)
    }

    @Singleton
    @Provides
    fun provideRestrictionsAccessTokenService(moshi: Moshi): OAuthService {
        return Retrofit
            .Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl("https://test.api.amadeus.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(OAuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideVaccinesService(moshi: Moshi): VaccineService {
        return Retrofit
            .Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl("http://covid-restrictions-api.noxtton.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(VaccineService::class.java)
    }

}