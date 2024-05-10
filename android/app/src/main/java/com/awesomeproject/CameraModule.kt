package com.awesomeproject
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class CameraModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String = "CameraModule"

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun startCameraActivity(name: String, location: String) {
        Log.d("CameraModule", "Create event called with name: $name and location: $location")
        val intent = Intent(currentActivity, CameraActivity::class.java)
        currentActivity?.startActivity(intent)
    }
}