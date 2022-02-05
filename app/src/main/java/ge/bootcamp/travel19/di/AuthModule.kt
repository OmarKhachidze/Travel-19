package ge.bootcamp.travel19.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.bootcamp.travel19.data.remote.AuthService
import ge.bootcamp.travel19.data.repository.authentication.AuthRepository
import ge.bootcamp.travel19.data.repository.authentication.AuthRepositoryImpl
import ge.bootcamp.travel19.domain.use_cases.auth.AuthUseCase
import ge.bootcamp.travel19.domain.use_cases.auth.SignInUseCase
import ge.bootcamp.travel19.domain.use_cases.auth.SignUpUseCase
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideAuthService(@Named(Constants.HELPER_RETROFIT_CLIENT) retrofit: Retrofit.Builder): AuthService {
        return retrofit.build()
            .create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
        connectionListener: ConnectionListener
    ): AuthRepository {
        return AuthRepositoryImpl(authService, connectionListener)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCase {
        return AuthUseCase(
            signInUseCase = SignInUseCase(repository),
            signUpUseCase = SignUpUseCase(repository)
        )
    }

}