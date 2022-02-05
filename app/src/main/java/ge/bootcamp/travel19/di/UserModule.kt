package ge.bootcamp.travel19.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.bootcamp.travel19.data.remote.UserService
import ge.bootcamp.travel19.data.repository.user_info.UserRepository
import ge.bootcamp.travel19.data.repository.user_info.UserRepositoryImpl
import ge.bootcamp.travel19.domain.use_cases.user.*
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Singleton
    @Provides
    fun provideUserInfoService(@Named(Constants.HELPER_RETROFIT_CLIENT) retrofit: Retrofit.Builder): UserService {
        return retrofit.build()
            .create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userService: UserService,
        connectionListener: ConnectionListener
    ): UserRepository {
        return UserRepositoryImpl(userService, connectionListener)
    }

    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository): UserUseCases {
        return UserUseCases(
            getNationalitiesUseCase = GetNationalitiesUseCase(repository),
            getVaccinesUseCase = GetVaccinesUseCase(repository),
            getAirportsUseCase = GetAirportsUseCase(repository),
            getUserUseCase = GetUserUseCase(repository)
        )
    }
}