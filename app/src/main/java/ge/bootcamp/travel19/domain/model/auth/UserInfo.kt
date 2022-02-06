package ge.bootcamp.travel19.domain.model.auth


data class UserInfo(val email: String, val password: String, val data: Data? = null)