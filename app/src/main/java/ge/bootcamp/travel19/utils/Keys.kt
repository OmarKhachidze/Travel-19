package ge.bootcamp.travel19.utils

import ge.bootcamp.travel19.utils.Constants.LIBRARY_NAME

object Keys {

    init {
        System.loadLibrary(LIBRARY_NAME)
    }

    external fun clientId(): String
    external fun clientSecret(): String

}