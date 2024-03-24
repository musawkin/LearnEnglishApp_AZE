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
import com.example.englishwordsapp.ui.main.tabs.Learn.Sentence_Build_Section.SentenceBuildFragment
import com.example.englishwordsapp.ui.main.tabs.Learn.Speech_Recognition_Section.SpeechRecognitionFragment


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
            showDifficultyLevelDialog(QuizFragment(), R.id.quizFragment)
        }

        binding?.bt2?.setOnClickListener {
            val direction =
                LearnFragmentDirections
                    .actionLearnFragmentToTranslationWordsFragment()
            findNavController().navigate(direction)
        }

        binding?.bt3?.setOnClickListener {
            showDifficultyLevelDialog(SpeechRecognitionFragment(),R.id.speechRecognitionFragment2 )
        }
        binding?.bt4?.setOnClickListener {
            showDifficultyLevelDialog(SentenceBuildFragment(), R.id.sentenceBuildFragment)
        }
    }

    private fun showDifficultyLevelDialog(fragment: Fragment, fragmentID: Int){
        val dialog = LevelSetDialogFragment()
        dialog.onLevelSelectedListener = object : LevelSetDialogFragment.OnLevelSelectedListener {
            override fun onLevelSelected(level: String) {
                startFragment(level, fragment, fragmentID)
            }
        }
        dialog.show(childFragmentManager, "DifficultyLevelDialog")
    }

    private fun startFragment(
        difficultyLevel: String,
        fragment: Fragment,
        fragmentID: Int
    ) {
        val bundle = Bundle()
        bundle.putString("difficultyLevel", difficultyLevel)
        fragment.arguments = bundle
        findTopNavController().navigate(fragmentID, bundle)
    }



}