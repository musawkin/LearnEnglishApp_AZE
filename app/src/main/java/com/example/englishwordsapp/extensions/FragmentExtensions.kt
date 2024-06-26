package com.example.englishwordsapp.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


fun Fragment.findTopNavController(): NavController {
        val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerMainGraph) as NavHostFragment?
        return topLevelHost?.navController ?: findNavController()
    }

fun <T> Fragment.collectFlow(flow: Flow<T>, collect: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            flow.collect {
                collect.invoke(it)
            }
        }
    }
}

