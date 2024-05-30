package com.example.englishwordsapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.FragmentMainBinding
import com.example.englishwordsapp.ui.MainActivityArgs
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import com.example.englishwordsapp.ui.MainActivityArgs.Companion as MainActivityArgs1


@AndroidEntryPoint
class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isSignedIn()
        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController
        binding?.bottomNav?.let { NavigationUI.setupWithNavController(it, navController) }
    }

    private fun isSignedIn() {
        val bundle = requireActivity().intent.extras ?: throw IllegalStateException("No required arguments")
        val args = MainActivityArgs.fromBundle(bundle)

            if (!args.isSignedIn) {
                findNavController().navigate(R.id.signInFragment)
            }

    }

}