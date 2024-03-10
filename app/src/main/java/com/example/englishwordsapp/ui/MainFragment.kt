package com.example.englishwordsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint


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


        binding?.bt1?.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_quizFragment)
        }

        binding?.bt2?.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_translationWordsFragment)
        }

        binding?.bt3?.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_speechRecognitionFragment)
        }
        binding?.bt4?.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_sentenceBuildFragment)
        }
    }


}