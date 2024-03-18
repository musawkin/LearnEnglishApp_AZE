package com.example.englishwordsapp.ui.main.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.FragmentTabsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TabsFragment : Fragment() {
    private var binding: FragmentTabsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentTabsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController
        binding?.bottomNav?.let { NavigationUI.setupWithNavController(it, navController) }



    }


}