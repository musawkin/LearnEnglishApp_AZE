package com.example.englishwordsapp.ui.main.tabs.Learn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.FragmentLearnBinding
import com.example.englishwordsapp.findTopNavController
import com.example.englishwordsapp.ui.main.tabs.Learn.Interactive_Quiz_Section.QuizFragment


class LearnFragment : Fragment() {
    private var binding: FragmentLearnBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLearnBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.bt1?.setOnClickListener {
            showDifficultyLevelDialog()
        }

        binding?.bt2?.setOnClickListener {
            val direction =
                LearnFragmentDirections
                    .actionLearnFragmentToTranslationWordsFragment()
            findNavController().navigate(direction)
        }

        binding?.bt3?.setOnClickListener {
            findTopNavController().navigate(R.id.speechRecognitionFragment2)
        }
        binding?.bt4?.setOnClickListener {
            findTopNavController().navigate(R.id.sentenceBuildFragment)
        }

    }

    private fun showDifficultyLevelDialog(){
        val dialog = LevelSetDialogFragment()
        dialog.onLevelSelectedListener = object : LevelSetDialogFragment.OnLevelSelectedListener {
            override fun onLevelSelected(level: String) {
                startPracticeFragment(level)
            }
        }
        dialog.show(childFragmentManager, "DifficultyLevelDialog")
    }

    fun startPracticeFragment(difficultyLevel: String) {
        val fragment = QuizFragment()
        val bundle = Bundle()
        bundle.putString("difficultyLevel", difficultyLevel)
        fragment.arguments = bundle
        findTopNavController().navigate(R.id.quizFragment, bundle)
    }

}