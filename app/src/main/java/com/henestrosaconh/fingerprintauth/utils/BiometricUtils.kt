package com.henestrosaconh.fingerprintauth.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.henestrosaconh.fingerprintauth.R

object BiometricUtils {

    fun isDeviceReady(context: Context): Boolean =
        getCapability(context) == BIOMETRIC_SUCCESS

    fun showPrompt(
        activity: AppCompatActivity,
        callback: BiometricAuthCallback,
        title: String = activity.getString(R.string.biometric_title),
        subtitle: String = "",
        description: String = activity.getString(R.string.biometric_description),
        negativeButton: String = activity.getString(R.string.action_cancel),
    ) {
        val prompInfo =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setDescription(description)
                .setAllowedAuthenticators(BIOMETRIC_WEAK)
                .setNegativeButtonText(negativeButton)
                .build()

        val prompt = initPrompt(activity, callback)
        prompt.authenticate(prompInfo)
    }

    private fun initPrompt(activity: AppCompatActivity, callback: BiometricAuthCallback): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)

        val authenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                callback.onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                callback.onError()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                callback.onNotRecognized()
            }
        }

        return BiometricPrompt(activity, executor, authenticationCallback)
    }

    private fun getCapability(context: Context): Int =
        BiometricManager.from(context).canAuthenticate(BIOMETRIC_WEAK)

}
