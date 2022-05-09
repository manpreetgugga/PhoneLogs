package com.mobile.phonelogs.base.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        setContentView(fetchLayout())
        initializeViewModel()
        initialize()
    }

    abstract fun initialize()
    abstract fun initializeBinding()
    abstract fun initializeViewModel()
    abstract fun fetchLayout() : View
}