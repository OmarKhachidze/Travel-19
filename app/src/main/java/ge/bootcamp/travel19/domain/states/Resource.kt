package ge.bootcamp.travel19.domain.states

sealed class Resource<T>(val data: T? = null, val message: String? = null, val empty: Int? = null) {
    class Empty<T>(val state: Int? = null) : Resource<T>(empty = state)
    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}