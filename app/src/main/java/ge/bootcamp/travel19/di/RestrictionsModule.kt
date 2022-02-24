package ge.bootcamp.travel19.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.bootcamp.travel19.feature_covid_restrictions.data.remote_data_source.RestrictionsService
import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.RestrictionsRepository
import ge.bootcamp.travel19.feature_covid_restrictions.data.repository.RestrictionsRepositoryImpl
import ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.restrictions.GetRestrictionsByAirportUseCase
import ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.restrictions.GetRestrictionsByCountryUseCase
import ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.restrictions.RestrictionsUseCases
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