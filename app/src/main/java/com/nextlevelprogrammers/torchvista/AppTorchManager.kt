package com.nextlevelprogrammers.torchvista

import android.content.Context

class AppTorchManager(private val context: Context) {

    private val torchManager = TorchManager(context)

    fun toggleTorch() {
        torchManager.toggleTorch()
    }
}
