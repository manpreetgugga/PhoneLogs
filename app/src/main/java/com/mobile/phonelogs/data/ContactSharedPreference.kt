package com.mobile.phonelogs.data

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


object ContactSharedPreference {

    fun updateLastSeenScreen(activity: Activity, screenName: String) {
        val editor: SharedPreferences.Editor = activity.getSharedPreferences("UserContact", MODE_PRIVATE).edit();
        editor.putString("lastScreen", screenName);
        editor.apply();
    }

    fun fetchLastScreenSeen(activity: Activity ): String {
        val prefs: SharedPreferences = activity.getSharedPreferences("UserContact", MODE_PRIVATE)
        return prefs.getString("lastScreen","Contacts") ?: "Contacts"
    }
}