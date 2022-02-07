package ge.bootcamp.travel19.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.data.remote.OAuthService
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants.HELPER_RETROFIT_CLIENT
import ge.bootcamp.travel19.utils.Constants.MAIN_RETROFIT_CLIENT
import ge.bootcamp.travel19.utils.OAuthInterceptor
import okhttp3.Cache
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
        connectionListener: ConnectionListener,
        @ApplicationContext context: Context
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (connectionListener.value == true)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            .dispatcher(Dispatcher().apply { maxRequests = 5 })
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .cache(Cache(context.cacheDir, (5 * 1024 * 1024).toLong()))
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
    @Named(MAIN_RETROFIT_CLIENT)
    fun provideRetrofit(
        okHttpClient: OkHttpClient.Builder,
        oAuthInterceptor: OAuthInterceptor,
        moshi: Moshi
    ): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BuildConfig.AMADEUS_BASE_URL)
            .client(okHttpClient.addInterceptor(oAuthInterceptor).build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    @Named(HELPER_RETROFIT_CLIENT)
    fun provideRetrofitHelper(
        okHttpClient: OkHttpClient.Builder,
        moshi: Moshi
    ): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BuildConfig.AMADEUS_BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    fun provideRestrictionsAccessTokenService(
        @Named(HELPER_RETROFIT_CLIENT) retrofit: Retrofit.Builder,
    ): OAuthService {
        return retrofit.build()
            .create(OAuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideConnectivityManager(
        @ApplicationContext context: Context,
    ): ConnectionListener {
        return ConnectionListener(context)
    }

}