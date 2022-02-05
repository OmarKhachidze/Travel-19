package ge.bootcamp.travel19.domain.use_cases.auth

data class AuthUseCase(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase
)
