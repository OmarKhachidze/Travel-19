package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.travel_plan

data class TravelPlanUseCases(
    val saveTravelPlanUseCase: SaveTravelPlanUseCase,
    val getTravelPlanUseCase: GetTravelPlanUseCase,
    val updateTravelPlanUseCase: UpdateTravelPlanUseCase,
    val deleteTravelPlanUseCase: DeleteTravelPlanUseCase
)
