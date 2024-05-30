package com.example.englishwordsapp.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.ActivityMainBinding
import com.example.englishwordsapp.extensions.findTopNavController
import com.example.englishwordsapp.ui.auth.SignInFragment
import com.example.englishwordsapp.ui.main.MainFragment
import com.example.englishwordsapp.ui.splash.SplashActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        isSignedIn()

    }

    private fun isSignedIn() {
        val bundle = intent.extras ?: throw IllegalStateException("No required arguments")
        val args = MainActivityArgs.fromBundle(bundle)

    }
    private fun getMainNavigationGraphId(): Int = R.navigation.main_graph

    private fun getTabsDestination(): Int = R.id.mainFragment

    private fun getSignInDestination(): Int = R.id.signInFragment
}