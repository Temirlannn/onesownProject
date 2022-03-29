package com.example.ui.main

import com.example.utils.networkConnectivityChecker.NetInternetOFF
import com.example.core.base.BaseActivity
import com.example.youtube.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by inject()

    override fun setupUI() {
        btn_check_internet.setOnClickListener { NetInternetOFF.checkForConnection() }
    }
}