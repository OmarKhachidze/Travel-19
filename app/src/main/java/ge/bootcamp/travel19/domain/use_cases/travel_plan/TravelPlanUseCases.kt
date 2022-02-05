package ge.bootcamp.travel19.domain.use_cases.travel_plan

data class TravelPlanUseCases(
    val saveTravelPlanUseCase: SaveTravelPlanUseCase,
    val getTravelPlanUseCase: GetTravelPlanUseCase,
    val deleteTravelPlanUseCase: DeleteTravelPlanUseCase
)
