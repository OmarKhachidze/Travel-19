package ge.bootcamp.travel19.utils

import android.util.Log
import ge.bootcamp.travel19.utils.Constants.CONNECTIVITY_TAG
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

object NetworkInternet {

    fun execute(socketFactory: SocketFactory): Boolean {
        return try {
            Log.d(CONNECTIVITY_TAG, "PINGING google.")
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Log.d(CONNECTIVITY_TAG, "PING success.")
            true
        } catch (e: IOException) {
            Log.e(CONNECTIVITY_TAG, "No internet connection. $e")
            false
        }
    }
}