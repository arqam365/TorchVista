package com.nextlevelprogrammers.torchvista

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.util.Log

class TorchManager(private val context: Context) {
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    var isTorchOn: Boolean = false

    fun toggleTorch() {
        isTorchOn = !isTorchOn
        setTorchState(isTorchOn)
    }

    fun setTorchState(on: Boolean) {
        try {
            val cameraId = cameraManager.cameraIdList.firstOrNull() ?: return
            cameraManager.setTorchMode(cameraId, on)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
