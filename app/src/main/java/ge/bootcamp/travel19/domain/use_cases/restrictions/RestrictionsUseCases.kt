package ge.bootcamp.travel19.domain.use_cases.restrictions

data class RestrictionsUseCases(
    val getRestrictionsByCountryUseCase: GetRestrictionsByCountryUseCase,
    val getRestrictionsByAirportUseCase: GetRestrictionsByAirportUseCase
)