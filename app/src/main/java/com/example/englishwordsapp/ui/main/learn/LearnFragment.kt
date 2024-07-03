package com.example.englishwordsapp.ui.main.learn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import com.example.englishwordsapp.adminPanel.AddingSimpleWordsModel
import com.example.englishwordsapp.databinding.FragmentLearnBinding
import com.example.englishwordsapp.extensions.findTopNavController
import com.example.englishwordsapp.ui.main.learn.dialogFragments.LevelSetDialogFragment
import com.example.englishwordsapp.ui.main.learn.quiz.QuizFragment
import com.example.englishwordsapp.ui.main.learn.sentenceBuild.SentenceBuildFragment
import com.example.englishwordsapp.ui.main.learn.speechRecognition.SpeechRecognitionFragment
import com.google.firebase.auth.FirebaseAuth


class LearnFragment : Fragment() {
    private var binding: FragmentLearnBinding? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLearnBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUserData()

        binding?.btQuiz?.setOnClickListener {
            showDifficultyLevelDialog(QuizFragment(), R.id.quizFragment)
        }

        binding?.btVocabularyTranslation?.setOnClickListener {
            findNavController().navigate(R.id.action_learnFragment_to_translationWordsFragment)
        }

        binding?.btSpeechRecognition?.setOnClickListener {
            showDifficultyLevelDialog(SpeechRecognitionFragment(),R.id.speechRecognitionFragment2 )
        }
        binding?.btSentenceBuild?.setOnClickListener {
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
        findTopNavController().navigate(fragmentID, bundle)
    }

    private fun setUserData(){
        auth = FirebaseAuth.getInstance()

        var currentUser = auth.currentUser
        if (currentUser != null){
            var name = currentUser.displayName
            var photoUrl = currentUser.photoUrl
            if (name!=null){
                binding?.tvGreetings?.text = "Hi, " + name
            }

        }else{
            Toast.makeText(requireContext(), "Error receiving data", Toast.LENGTH_SHORT).show()
        }
    }



}