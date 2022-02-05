package ge.bootcamp.travel19.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.bootcamp.travel19.data.remote.CountriesService
import ge.bootcamp.travel19.data.repository.countries.CountriesRepository
import ge.bootcamp.travel19.data.repository.countries.CountriesRepositoryImpl
import ge.bootcamp.travel19.domain.use_cases.country.CountryUseCases
import ge.bootcamp.travel19.domain.use_cases.country.GetAllCountryUseCase
import ge.bootcamp.travel19.domain.use_cases.country.GetCountryByNameUseCase
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CountriesModule {

    @Singleton
    @Provides
    fun provideCountriesService(@Named(Constants.HELPER_RETROFIT_CLIENT) retrofit: Retrofit.Builder): CountriesService {
        return retrofit.build()
            .create(CountriesService::class.java)
    }

    @Provides
    @Singleton
    fun provideCountriesRepository(
        countriesService: CountriesService,
        connectionListener: ConnectionListener
    ): CountriesRepository {
        return CountriesRepositoryImpl(countriesService, connectionListener)
    }

    @Provides
    @Singleton
    fun provideCountryUseCases(repository: CountriesRepository): CountryUseCases {
        return CountryUseCases(
            getAllCountryUseCase = GetAllCountryUseCase(repository),
            getCountryByNameUseCase = GetCountryByNameUseCase(repository)
        )
    }

}