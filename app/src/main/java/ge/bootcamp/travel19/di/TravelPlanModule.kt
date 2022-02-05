package ge.bootcamp.travel19.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.bootcamp.travel19.data.remote.TravelPlansService
import ge.bootcamp.travel19.data.repository.travel_plans.TravelPlanRepository
import ge.bootcamp.travel19.data.repository.travel_plans.TravelPlanRepositoryImpl
import ge.bootcamp.travel19.domain.use_cases.travel_plan.DeleteTravelPlanUseCase
import ge.bootcamp.travel19.domain.use_cases.travel_plan.GetTravelPlanUseCase
import ge.bootcamp.travel19.domain.use_cases.travel_plan.SaveTravelPlanUseCase
import ge.bootcamp.travel19.domain.use_cases.travel_plan.TravelPlanUseCases
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TravelPlanModule {

    @Singleton
    @Provides
    fun provideTravelPlanService(@Named(Constants.HELPER_RETROFIT_CLIENT) retrofit: Retrofit.Builder): TravelPlansService {
        return retrofit.build()
            .create(TravelPlansService::class.java)
    }

    @Provides
    @Singleton
    fun provideTravelPlanRepository(
        travelPlansService: TravelPlansService,
        connectionListener: ConnectionListener
    ): TravelPlanRepository {
        return TravelPlanRepositoryImpl(travelPlansService, connectionListener)
    }

    @Provides
    @Singleton
    fun provideTravelPlanServiceUseCases(repository: TravelPlanRepository): TravelPlanUseCases {
        return TravelPlanUseCases(
            saveTravelPlanUseCase = SaveTravelPlanUseCase(repository),
            getTravelPlanUseCase = GetTravelPlanUseCase(repository),
            deleteTravelPlanUseCase = DeleteTravelPlanUseCase(repository),
        )
    }
}