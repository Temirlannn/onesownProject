package com.example.utils.networkConnectivityChecker

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
//internet выкл/вкл

object NetInternetOFF : LiveData<Boolean>() {
    private lateinit var networkConnectivityUtil: NetInternetON
    private lateinit var connectivityManager: ConnectivityManager

    override fun onActive() {
        registerCallback()
        super.onActive()
    }

    override fun onInactive() {
        removeCallback()
        super.onInactive()
    }

    fun checkForConnection() {
        value = networkConnectivityUtil.isConnected()
    }

    fun init(context: Context) {
        networkConnectivityUtil = NetInternetON(context)
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }


    private fun notifyObservers(connectionStatus: Boolean) {
        postValue(connectionStatus)
    }

    private fun registerCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun removeCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            notifyObservers(true)
            super.onAvailable(network)
        }

        override fun onLost(network: Network) {
            notifyObservers(false)
            super.onLost(network)
        }
    }
}