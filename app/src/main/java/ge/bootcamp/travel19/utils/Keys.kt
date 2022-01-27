package ge.bootcamp.travel19.utils

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun clientId(): String
    external fun clientSecret(): String

}