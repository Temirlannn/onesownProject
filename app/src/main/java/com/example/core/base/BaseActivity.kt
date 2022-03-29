package com.example.core.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.utils.networkConnectivityChecker.NetInternetOFF
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity(private val layout: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        setupUI()
        NetInternetOFF.observe(this, liveDataObserver)
    }

    override fun onResume() {
        super.onResume()
        NetInternetOFF.checkForConnection()
    }

    private val liveDataObserver: Observer<Boolean> = Observer { isConnected ->
        if (!isConnected) {
            showDisconnectState()
        } else {
            showConnectedState()
        }
    }

    private fun showDisconnectState() {
        connectionContainer.visibility = View.VISIBLE
        containerTwo.visibility = View.INVISIBLE
    }

    private fun showConnectedState() {
        if (connectionContainer != null) {
            connectionContainer.visibility = View.INVISIBLE
            containerTwo.visibility = View.VISIBLE
        }
    }

    abstract fun setupUI()
}