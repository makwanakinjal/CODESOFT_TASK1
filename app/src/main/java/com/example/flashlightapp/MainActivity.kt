package com.example.flashlightapp

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton

class MainActivity : AppCompatActivity() {

    private lateinit var cameraManager : CameraManager
    private var cameraId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toggleButoon = findViewById<ToggleButton>(R.id.toggleButton)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try {
            cameraId = cameraManager.cameraIdList[0]
        } catch (e: CameraAccessException){
            e.printStackTrace()
        }

        toggleButoon.setOnCheckedChangeListener{_, isChecked ->

            if(isChecked){
                turnOnFlashlight()
            } else{
                turnOffFlashlight()
            }

        }


    }

    private fun turnOnFlashlight(){
        try{
            cameraId?.let { id->
                cameraManager.setTorchMode(id,true)
            }

        } catch (e: CameraAccessException){
            e.printStackTrace()
            Toast.makeText(this,"Unable to turn on flashlight",Toast.LENGTH_LONG).show()
        }
    }

    private fun turnOffFlashlight() {
        try {
            cameraId?.let { id ->
                cameraManager.setTorchMode(id, false)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
            Toast.makeText(this, "Unable to turn off flashlight", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        turnOffFlashlight()
    }

    override fun onDestroy() {
        super.onDestroy()
        turnOffFlashlight()
    }
}