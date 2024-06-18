package com.example.hotel.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.hotel.R
import com.example.hotel.presentation.utils.IS_REGISTERED
import com.example.hotel.presentation.utils.MY_STORAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferences = getSharedPreferences(MY_STORAGE, Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavController()
        if (sharedPreferences.getBoolean(IS_REGISTERED,false)){
            navController.navigate(R.id.mainFragment, null, navOptions {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            })
        }
    }


    private fun initNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

    }
}