package com.example.englishwordsapp.ui.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.FragmentProfileBinding
import com.example.englishwordsapp.extensions.findTopNavController
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUserData()

        binding?.btLogOut?.setOnClickListener {
            logOut()
        }
    }

    private fun setUserData(){
        auth = FirebaseAuth.getInstance()

        var currentUser = auth.currentUser
        if (currentUser != null){
            var name = currentUser.displayName
            var email = currentUser.email
            var photoUrl = currentUser.photoUrl
            if (name!=null){
                binding?.tvName?.text = "Name:  " + name
            }

            if (email!=null){
                binding?.tvEmail?.text = "Email:  " + email
            }

            photoUrl?.let {
                binding?.ivAvatar?.let { it1 ->
                    Glide.with(requireContext())
                        .load(it)
                        .apply(RequestOptions.circleCropTransform()) // Опционально, для округления изображения
                        .into(it1)
                }
            }

        }else{
            Toast.makeText(requireContext(), "Error receiving data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logOut(){
        auth = FirebaseAuth.getInstance()
        auth.signOut()

        clearBackStack(findTopNavController())

        clearBackStack(findNavController())

        findTopNavController().navigate(R.id.signInFragment)


    }

    private fun clearBackStack(navController: NavController) {
        while (navController.currentBackStackEntry != null) {
            navController.popBackStack()
        }
    }
}