package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.country

data class CountryUseCases(
    val getCountryByNameUseCase: GetCountryByNameUseCase,
    val getAllCountryUseCase: GetAllCountryUseCase
)