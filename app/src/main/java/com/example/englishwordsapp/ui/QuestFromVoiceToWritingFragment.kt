package com.example.englishwordsapp.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.databinding.FragmentQuestFromVoiceToWritingBinding
import com.example.englishwordsapp.Words
import java.util.Locale


class QuestFromVoiceToWritingFragment : Fragment() {
    private var binding: FragmentQuestFromVoiceToWritingBinding? = null
    private var textToSpeech: TextToSpeech? = null
    private var wordInSpeaker: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentQuestFromVoiceToWritingBinding.inflate(layoutInflater)
        textToSpeech = TextToSpeech(requireContext(), null)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var wrongAnswer = 0
        var initialSizeListOfWords = listOfWords.size
        binding?.progressIndicator?.max = listOfWords.size
        binding?.tvProgressCount?.text = listOfWords.size.toString()



        binding?.correctLayout?.isVisible = false


        listOfWords.shuffle()
        wordInSpeaker = listOfWords.last().wordInEnglish
        listOfWords.removeLast()


        binding?.imagePlaySpeaker?.setOnClickListener {
            textToSpeech?.setLanguage(Locale.US)
            textToSpeech?.speak(wordInSpeaker, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        binding?.btConfirm?.setOnClickListener {

            if (wordInSpeaker == binding?.etInputCorrectAnswer?.text.toString().trim().toLowerCase().capitalize()){
                binding?.correctLayout?.isVisible = true
                hideKeyboard(binding?.btConfirm!!)
                binding?.etInputCorrectAnswer?.setTextColor(Color.BLACK)

            }else{
                binding?.etInputCorrectAnswer?.setTextColor(Color.RED)
            }
        }

        binding?.btCorrect?.setOnClickListener {

            if (listOfWords.size > 0) {

                binding?.correctLayout?.isVisible = false
                binding?.tvProgressCount?.text = listOfWords.size.toString()
                binding?.progressIndicator?.progress = binding?.progressIndicator?.progress!! + 1

                binding?.etInputCorrectAnswer?.text?.clear()

                wordInSpeaker = listOfWords.last().wordInEnglish
                listOfWords.removeLast()

                textToSpeech?.setLanguage(Locale.US)
                textToSpeech?.speak(wordInSpeaker, TextToSpeech.QUEUE_FLUSH, null, null)

            }else{

                val countOfCorrectAnswer = initialSizeListOfWords
                val dialogFragment = CustomDialogForEndOfQuest()
                dialogFragment.isCancelable = false
                dialogFragment.setScore(countOfCorrectAnswer.toString(), wrongAnswer.toString())
                dialogFragment.show(
                    parentFragmentManager,
                    CustomDialogForEndOfQuest::class.java.canonicalName
                )
            }
        }

        binding?.imageButtonClose?.setOnClickListener {
            findNavController().popBackStack()
        }



    }

    private val listOfWords = Words.listOfWords

    private fun hideKeyboard(view: View){
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}