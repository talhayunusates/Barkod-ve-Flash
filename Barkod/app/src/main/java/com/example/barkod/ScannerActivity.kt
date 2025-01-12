package com.example.barkod

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator


@Suppress("DEPRECATION")
class ScannerActivity : AppCompatActivity() {

    private val scanResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val resultData = result.data?.getStringExtra("SCAN_RESULT")
                resultData?.let {
                    val resultIntent = Intent().apply {
                        putExtra("SCAN_RESULT", it)
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBarcodeScanner()
    }

    private fun startBarcodeScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Scan a barcode")
        integrator.setCameraId(0) // Use a specific camera of the device
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        val scanIntent = integrator.createScanIntent()
        scanResultLauncher.launch(scanIntent)
    }
}
