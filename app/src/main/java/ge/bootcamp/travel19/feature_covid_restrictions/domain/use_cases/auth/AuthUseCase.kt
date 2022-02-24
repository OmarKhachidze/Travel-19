package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.auth

data class AuthUseCase(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase
)
