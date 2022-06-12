package com.henestrosaconh.fingerprintauth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.henestrosaconh.fingerprintauth.utils.BiometricAuthCallback
import com.henestrosaconh.fingerprintauth.utils.BiometricUtils
import com.henestrosaconh.fingerprintauth.utils.PreferencesUtils

class MainActivity : AppCompatActivity(), BiometricAuthCallback {

    private val values by lazy { arrayOf("name", "email", "phone") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBiometricCapabilities()
        showBiometricPrompt()
        fillUserData()

        findViewById<Button>(R.id.bt_save_data).setOnClickListener {
            saveUserData()
        }
    }

    override fun onSuccess() {
        findViewById<LinearLayout>(R.id.ll_parent).visibility = View.VISIBLE
    }

    override fun onError() {
        finish()
    }

    override fun onNotRecognized() {
        Log.d("MainActivity", "Huella no reconocida")
    }

    private fun checkBiometricCapabilities() {
        if (!BiometricUtils.isDeviceReady(this)) {
            finish()
        } else {
            Toast.makeText(this, getString(R.string.biometry_available), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showBiometricPrompt() {
        BiometricUtils.showPrompt(
            activity = this,
            callback = this
        )
    }

    private fun fillUserData() {
        values.forEach { value ->
            val resId = resources.getIdentifier(
                "et_$value",
                "id",
                packageName
            )

            findViewById<EditText>(resId).setText(
                PreferencesUtils.get(this, value, "")
                    .toString()
            )
        }
    }

    private fun saveUserData() {
        values.forEach { value ->
            val resId = resources.getIdentifier(
                "et_$value",
                "id",
                packageName
            )

            PreferencesUtils.put(
                this,
                value,
                findViewById<EditText>(resId).text.toString()
            )
        }
    }

}