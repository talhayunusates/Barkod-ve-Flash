
package com.example.barkod

import android.app.Service
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.os.IBinder
import android.content.Context


class FlashlightService : Service() {

    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String
    private var isFlashOn = false

    override fun onCreate() {
        super.onCreate()
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraId = cameraManager.cameraIdList[0] // Genellikle arka kameranın ID'sini al
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val action = intent.getStringExtra("ACTION")
        if (action == "TOGGLE") {
            toggleFlashlight()
        }
        return START_STICKY
    }

    private fun toggleFlashlight() {
        isFlashOn = !isFlashOn
        try {
            cameraManager.setTorchMode(cameraId, isFlashOn)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Servis kapatıldığında flaşı kapat
        try {
            cameraManager.setTorchMode(cameraId, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
