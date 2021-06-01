package com.manut.gogreen

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.manut.gogreen.data.utils.SessionManager
import com.manut.gogreen.data.utils.UserRepository
import com.manut.gogreen.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var userRepository: UserRepository
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sesi = SessionManager(this)
        userRepository = UserRepository.getInstance(sesi)
        setupBottomNavigationView()
        Toast.makeText(this,  "Welcome ${userRepository.getUser()}",Toast.LENGTH_SHORT).show()

    }

    private fun setupBottomNavigationView(){
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()


        val appBarConfiguration = AppBarConfiguration.Builder(R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications).build()

        setSupportActionBar(binding.toolBar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}