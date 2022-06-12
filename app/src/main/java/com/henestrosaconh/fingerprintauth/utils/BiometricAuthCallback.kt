package com.henestrosaconh.fingerprintauth.utils

interface BiometricAuthCallback {
    fun onSuccess()
    fun onError()
    fun onNotRecognized()
}