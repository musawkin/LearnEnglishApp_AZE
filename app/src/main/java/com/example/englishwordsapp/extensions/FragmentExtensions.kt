package com.example.englishwordsapp.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import com.example.englishwordsapp.ui.main.learn.SimpleWordsModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


fun Fragment.findTopNavController(): NavController {
        val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerInActivity) as NavHostFragment?
        return topLevelHost?.navController ?: findNavController()
    }

