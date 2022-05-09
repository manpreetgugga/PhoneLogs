package com.mobile.phonelogs.ui.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import com.mobile.phonelogs.MainPageLandingScreen
import com.mobile.phonelogs.data.Constants

fun Activity.redirectToSettingsScreen() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivityForResult(intent, Constants.PERMISSION_CALL_BACK)
}

fun Activity.redirectToMainScreen() {
    Handler(Looper.getMainLooper()).postDelayed({
        startActivity(Intent(this, MainPageLandingScreen::class.java))
        finish()
    }, 1000)
}
