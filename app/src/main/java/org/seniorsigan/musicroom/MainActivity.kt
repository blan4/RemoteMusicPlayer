package org.seniorsigan.musicroom

import android.content.Context
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var server: Server? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun restartServer() {
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipAddress = wifiManager.connectionInfo.ipAddress
        val formattedIpAddress = String.format(
                Locale.ENGLISH, "%d.%d.%d.%d",
                ipAddress and 0xff, ipAddress shr 8 and 0xff,
                ipAddress shr 16 and 0xff, ipAddress shr 24 and 0xff)

        if (server != null) {
            server!!.stop()
            server = null
        }
        server = Server()
        try {
            server!!.start()
        } catch (e: IOException) {
            Log.e("MusicRoom", e.message, e)
        }

        Log.i("MusicRoom", "Listen http://" + formattedIpAddress + ":" + Server.PORT)
    }

    private fun stopServer() {
        if (server != null) {
            server!!.stop()
            Log.i("MusicRoom", "Server stopped")
        }
    }

    override fun onResume() {
        super.onResume()
        restartServer()
    }

    override fun onPause() {
        super.onPause()
        stopServer()
    }
}