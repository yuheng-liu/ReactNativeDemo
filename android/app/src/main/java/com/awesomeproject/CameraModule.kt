package com.awesomeproject
import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.BaseActivityEventListener
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class CameraModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String = "CameraModule"

    private var cameraPromise: Promise? = null

    private val activityEventListener = object : BaseActivityEventListener() {
        override fun onActivityResult(
            activity: Activity?,
            requestCode: Int,
            resultCode: Int,
            data: Intent?
        ) {
            if (requestCode == CAMERA_IMAGE_REQUEST) {
                cameraPromise?.let { promise: Promise ->
                    when (resultCode) {
                        Activity.RESULT_CANCELED -> promise.reject(ERROR_CAMERA_CANCELLED, "Camera was cancelled")
                        Activity.RESULT_OK -> {
                            val uriString = data?.getStringExtra("imageUri")
                            uriString?.let { promise.resolve(uriString) }
                                ?: promise.reject(ERROR_NO_IMAGE_SAVED, "NO IMAGE SAVED")
                        }
                    }
                    cameraPromise = null
                }
            }
        }
    }

    init {
        reactContext.addActivityEventListener(activityEventListener)
    }

    @ReactMethod()
    fun startCameraActivity(promise: Promise) {
        val activity = currentActivity

        if (activity == null) {
            promise.reject(ERROR_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist")
            return
        }

        cameraPromise = promise

        try {
            val intent = Intent(currentActivity, CameraActivity::class.java)
            currentActivity?.startActivityForResult(intent, CAMERA_IMAGE_REQUEST)
        } catch (t: Throwable) {
            cameraPromise?.reject(ERROR_CANNOT_SHOW_ACTIVITY, t)
            cameraPromise = null
        }
    }

    companion object {
        const val CAMERA_IMAGE_REQUEST = 1
        const val ERROR_ACTIVITY_DOES_NOT_EXIST = "ERROR_ACTIVITY_DOES_NOT_EXIST"
        const val ERROR_CANNOT_SHOW_ACTIVITY = "ERROR_CANNOT_SHOW_ACTIVITY"
        const val ERROR_CAMERA_CANCELLED = "ERROR_CAMERA_CANCELLED"
        const val ERROR_NO_IMAGE_SAVED = "ERROR_NO_IMAGE_SAVED"
    }
}