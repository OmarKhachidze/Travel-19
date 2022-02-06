package ge.bootcamp.travel19.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.bootcamp.travel19.data.remote.RestrictionsService
import ge.bootcamp.travel19.data.repository.restrictions.RestrictionsRepository
import ge.bootcamp.travel19.data.repository.restrictions.RestrictionsRepositoryImpl
import ge.bootcamp.travel19.domain.use_cases.restrictions.GetRestrictionsByAirportUseCase
import ge.bootcamp.travel19.domain.use_cases.restrictions.GetRestrictionsByCountryUseCase
import ge.bootcamp.travel19.domain.use_cases.restrictions.RestrictionsUseCases
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestrictionsModule {

    @Singleton
    @Provides
    fun provideRestrictionsService(@Named(Constants.MAIN_RETROFIT_CLIENT) retrofit: Retrofit.Builder): RestrictionsService {
        return retrofit.build()
            .create(RestrictionsService::class.java)
    }

    @Provides
    @Singleton
    fun provideRestrictionsRepository(
        restrictionsService: RestrictionsService,
        connectionListener: ConnectionListener
    ): RestrictionsRepository {
        return RestrictionsRepositoryImpl(restrictionsService, connectionListener)
    }

    @Provides
    @Singleton
    fun provideRestrictionsUseCases(repository: RestrictionsRepository): RestrictionsUseCases {
        return RestrictionsUseCases(
            getRestrictionsByCountryUseCase = GetRestrictionsByCountryUseCase(repository),
            getRestrictionsByAirportUseCase = GetRestrictionsByAirportUseCase(repository)
        )
    }
}