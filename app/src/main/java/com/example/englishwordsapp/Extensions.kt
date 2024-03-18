package com.example.englishwordsapp

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController


    fun Fragment.findTopNavController(): NavController {
        val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerInActivity) as NavHostFragment?
        return topLevelHost?.navController ?: findNavController()
    }
