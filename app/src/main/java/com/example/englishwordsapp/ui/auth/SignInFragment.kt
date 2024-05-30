package com.example.englishwordsapp.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.FragmentSignInBinding
import com.example.englishwordsapp.extensions.findTopNavController
import com.example.englishwordsapp.ui.MainActivityArgs
import com.example.englishwordsapp.ui.main.MainFragment
import com.example.englishwordsapp.ui.splash.SplashActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignInFragment : Fragment() {
    private var binding: FragmentSignInBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        binding?.signInButton?.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account: GoogleSignInAccount? = task.result
            if (account!=null){
                updateUI(account)
            }
        }
        else{
            Toast.makeText(requireContext(), "Sign failed. try again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                findTopNavController().navigate(R.id.action_signInFragment_to_mainFragment)
                val args = MainActivityArgs(true)
                requireActivity().intent.putExtras(args.toBundle())
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainerMainGraph, MainFragment())
//                    .commit()
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
            }
        }
    }
}