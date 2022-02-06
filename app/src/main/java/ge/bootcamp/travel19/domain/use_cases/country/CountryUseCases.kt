package ge.bootcamp.travel19.domain.use_cases.country

data class CountryUseCases(
    val getCountryByNameUseCase: GetCountryByNameUseCase,
    val getAllCountryUseCase: GetAllCountryUseCase
)