package com.yasinenessisik.adopt_a_pet.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yasinenessisik.adopt_a_pet.R

class NavigationBarActivity : AppCompatActivity() {
    private lateinit var navController: NavController




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.searchFragment,
            R.id.addPetFragment,
            R.id.myHomeFragment,
            R.id.messageFragment
        ))

        bottomNavigationView.setupWithNavController(navController)


    }

}