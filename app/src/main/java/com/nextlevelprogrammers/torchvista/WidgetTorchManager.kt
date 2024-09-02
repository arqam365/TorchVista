package com.nextlevelprogrammers.torchvista

import android.content.Context

class WidgetTorchManager(private val context: Context) {

    private val torchManager = TorchManager(context)

    fun turnTorchOn() {
        torchManager.setTorchState(true)
    }

    fun turnTorchOff() {
        torchManager.setTorchState(false)
    }

    val isTorchOn: Boolean
        get() = torchManager.isTorchOn
}
