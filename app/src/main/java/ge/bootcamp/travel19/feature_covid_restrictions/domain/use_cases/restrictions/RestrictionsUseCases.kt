package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.restrictions

data class RestrictionsUseCases(
    val getRestrictionsByCountryUseCase: GetRestrictionsByCountryUseCase,
    val getRestrictionsByAirportUseCase: GetRestrictionsByAirportUseCase
)