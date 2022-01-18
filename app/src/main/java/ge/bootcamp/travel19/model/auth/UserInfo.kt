package ge.bootcamp.travel19.model.auth

import ge.bootcamp.travel19.model.auth.Data

data class UserInfo(val email: String, val password: String, val data: Data? = null) {
}