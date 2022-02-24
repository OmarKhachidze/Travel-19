package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.user

data class UserUseCases(
    val getNationalitiesUseCase: GetNationalitiesUseCase,
    val getVaccinesUseCase: GetVaccinesUseCase,
    val getAirportsUseCase: GetAirportsUseCase,
    val getUserUseCase: GetUserUseCase
)
