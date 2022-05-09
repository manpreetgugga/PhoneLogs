package com.mobile.phonelogs.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.phonelogs.R
import com.mobile.phonelogs.data.Constants.PERMISSION_CALL_LOG
import com.mobile.phonelogs.data.Constants.PERMISSION_CALL_PHONE
import com.mobile.phonelogs.data.Constants.PERMISSION_CALL_SMS
import com.mobile.phonelogs.data.Constants.PERMISSION_CONTACT
import com.mobile.phonelogs.data.Constants.PERMISSION_SEND_MESSAGES
import com.mobile.phonelogs.ui.utils.redirectToMainScreen
import com.mobile.phonelogs.ui.utils.redirectToSettingsScreen
import pub.devrel.easypermissions.EasyPermissions

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private var permsContacts = arrayOf(Manifest.permission.READ_CONTACTS)
    private var permsCallLogs = arrayOf(Manifest.permission.READ_CALL_LOG)
    private var permsSms = arrayOf(Manifest.permission.READ_SMS)
    private var permsSendSms = arrayOf(Manifest.permission.SEND_SMS)
    private var permsCallPhone = arrayOf(Manifest.permission.CALL_PHONE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestPermission()
    }

    private fun requestPermission() {
        if (EasyPermissions.hasPermissions(this, *permsContacts)) {
            if (EasyPermissions.hasPermissions(this, *permsCallLogs)) {
                if (EasyPermissions.hasPermissions(this, *permsSms)) {
                    if (EasyPermissions.hasPermissions(this, *permsSendSms)) {
                        if (EasyPermissions.hasPermissions(this, *permsCallPhone)) {
                            redirectToMainScreen()
                        } else {
                            EasyPermissions.requestPermissions(this, getString(R.string.perm_call), PERMISSION_CALL_PHONE, *permsCallPhone)
                        }
                    } else {
                        EasyPermissions.requestPermissions(this, getString(R.string.perm_send_sms), PERMISSION_SEND_MESSAGES, *permsSendSms)
                    }
                } else {
                    EasyPermissions.requestPermissions(this, getString(R.string.perm_display_sms), PERMISSION_CALL_SMS, *permsSms)
                }
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.perm_display_logs), PERMISSION_CALL_LOG, *permsCallLogs)
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.perm_contacts), PERMISSION_CONTACT, *permsContacts)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        requestPermission()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) || shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG) || shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
                requestPermission()
            } else {
                redirectToSettingsScreen()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        requestPermission()
    }
}