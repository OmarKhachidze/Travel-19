package ge.bootcamp.travel19.model.singup

import ge.bootcamp.travel19.model.logIn.User

data class SignUpResponse(
    val success: Boolean?,
    val token: String?,
    val user: User?,
) {
}