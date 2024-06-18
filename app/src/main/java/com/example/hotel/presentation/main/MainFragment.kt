package com.example.hotel.presentation.main

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import com.example.hotel.R
import com.example.hotel.databinding.FragmentMainBinding


class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val topIdSet = setOf(
        R.id.homeFragment,
        R.id.roomsFragment,
        R.id.historyFragment,
        R.id.profileFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = resources.getColor(R.color.white, null)
        }
        initBottomBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initBottomBar(){
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.container_main) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.bottomNavigationView.isVisible = topIdSet.contains(destination.id)
        }
        navController.navigate(R.id.homeFragment, null, navOptions {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        })

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

           when(navController.currentDestination?.id){
               R.id.roomInfoFragment->{
                   navController.popBackStack()
               }
               else->{
                   requireActivity().finish()
               }
           }

        }
    }
}