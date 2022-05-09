package com.mobile.phonelogs

import android.view.MenuItem
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobile.phonelogs.base.activities.BaseActivity
import com.mobile.phonelogs.base.viewmodels.ContactsViewModel
import com.mobile.phonelogs.data.Constants.CALLLOGS
import com.mobile.phonelogs.data.Constants.CONTACTS
import com.mobile.phonelogs.data.Constants.MESSAGES
import com.mobile.phonelogs.data.ContactSharedPreference
import com.mobile.phonelogs.databinding.ActivityMainBinding

class MainPageLandingScreen : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ContactsViewModel

    override fun initialize() {
        updateToolbar()
        updateDrawerAndNavigation()
        handleClickListeners()
    }

    private fun handleClickListeners() {
        when (ContactSharedPreference.fetchLastScreenSeen(this)) {
            CONTACTS -> {
                binding.navView.setCheckedItem(R.id.nav_contacts)
                binding.navView.menu.performIdentifierAction(R.id.nav_contacts, 0)
            }
            CALLLOGS -> {
                binding.navView.setCheckedItem(R.id.nav_call_logs)
                binding.navView.menu.performIdentifierAction(R.id.nav_call_logs, 0)
            }
            MESSAGES -> {
                binding.navView.setCheckedItem(R.id.nav_inbox)
                binding.navView.menu.performIdentifierAction(R.id.nav_inbox, 0)
            }
        }
    }

    override fun initializeBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initializeViewModel() {
        viewModel = ViewModelProvider(this)[ContactsViewModel::class.java]
    }

    override fun fetchLayout(): View {
        return binding.root
    }

    private fun updateToolbar() {
        setSupportActionBar(binding.appBarMain.toolbar)
    }

    private fun updateDrawerAndNavigation() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_contacts, R.id.nav_call_logs, R.id.nav_inbox), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}