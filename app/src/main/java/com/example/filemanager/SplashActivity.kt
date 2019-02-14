package com.example.filemanager

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.example.filemanager.activity.MainActivity

class SplashActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_READ_WRITE_STORAGE = 111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        requestPermissions()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_READ_WRITE_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

            PERMISSION_REQUEST_READ_WRITE_STORAGE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    val startMainIntent = Intent(this, MainActivity::class.java)
                    startActivity(startMainIntent)

                }
            }

        }
    }

}
