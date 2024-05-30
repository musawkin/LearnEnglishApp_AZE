package com.example.englishwordsapp.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.FragmentSplashBinding
import com.example.englishwordsapp.extensions.findTopNavController
import com.example.englishwordsapp.ui.MainActivity
import com.example.englishwordsapp.ui.MainActivityArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SplashFragment : Fragment() {
    private var binding: FragmentSplashBinding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?){
        if (currentUser != null){
            launchMainScreen(true)
        }else{
            launchMainScreen(false)
        }
    }
    private fun launchMainScreen(isSignedIn: Boolean){
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val args = MainActivityArgs(isSignedIn)
        intent.putExtras(args.toBundle())
        startActivity(intent)
        SplashActivity().finish()
    }
}