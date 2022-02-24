package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth


data class UserInfo(val email: String, val password: String, val data: Data? = null)