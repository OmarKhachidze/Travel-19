package ge.bootcamp.travel19.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.data.remote.authentication.AuthService
import ge.bootcamp.travel19.data.remote.user_info.UserInfoService
import ge.bootcamp.travel19.data.remote.countries.CountriesService
import ge.bootcamp.travel19.data.remote.favoritePlans.PlansService
import ge.bootcamp.travel19.data.remote.restrictions.OAuthService
import ge.bootcamp.travel19.data.remote.restrictions.RestrictionsService
import ge.bootcamp.travel19.utils.OAuthInterceptor
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
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
    ): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggingInterceptor)
            .dispatcher(Dispatcher().apply { maxRequests = 5 })
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        return builder
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
    @Named("main")
    fun provideRetrofit(
        okHttpClient: OkHttpClient.Builder,
        oAuthInterceptor: OAuthInterceptor,
        moshi: Moshi
    ): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BuildConfig.AMADEUS_ENDPOINT)
            .client(okHttpClient.addInterceptor(oAuthInterceptor).build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    @Named("helper")
    fun provideRetrofitHelper(
        okHttpClient: OkHttpClient.Builder,
        moshi: Moshi
    ): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BuildConfig.AMADEUS_ENDPOINT)
            .client(okHttpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    fun provideRestrictionsAccessTokenService(
        @Named("helper") retrofit: Retrofit.Builder,
    ): OAuthService {
        return retrofit.build()
            .create(OAuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideRestrictionsService(@Named("main") retrofit: Retrofit.Builder): RestrictionsService {
        return retrofit.build()
            .create(RestrictionsService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthService(@Named("helper")retrofit: Retrofit.Builder): AuthService {
        return retrofit.build()
            .create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideCountriesService(@Named("helper")retrofit: Retrofit.Builder): CountriesService {
        return retrofit.build()
            .create(CountriesService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserInfoService(@Named("helper")retrofit: Retrofit.Builder): UserInfoService {
        return retrofit.build()
            .create(UserInfoService::class.java)
    }

    @Singleton
    @Provides
    fun provideTravelPlaneService(@Named("helper")retrofit: Retrofit.Builder): PlansService {
        return retrofit.build()
            .create(PlansService::class.java)
    }

}