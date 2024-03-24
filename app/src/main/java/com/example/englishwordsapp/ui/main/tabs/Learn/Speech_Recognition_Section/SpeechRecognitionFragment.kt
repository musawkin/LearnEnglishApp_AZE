package com.example.englishwordsapp.ui.main.tabs.Learn.Speech_Recognition_Section

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import com.example.englishwordsapp.ui.main.tabs.Learn.SimpleWordsModel
import com.example.englishwordsapp.databinding.FragmentSpeechRecognitionBinding
import com.example.englishwordsapp.ui.main.tabs.Learn.ResultDialogFragment
import com.example.englishwordsapp.ui.main.tabs.Learn.Vocabulary_Section.VocabularyWordsResponseState
import java.util.Locale


class SpeechRecognitionFragment : Fragment() {
    private var binding: FragmentSpeechRecognitionBinding? = null
    private lateinit var textToSpeech: TextToSpeech
    private var wordInSpeaker: String? = null
    private var countOfAllQuestions: Int? = null
    private var correctAnswer = 0
    private var answerState = true
    private var questionModel: SimpleWordsModel? = null
    private val viewModel by viewModels<SpeechRecognitionViewModel>()
    private val listOfWords = mutableListOf<SimpleWordsModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentSpeechRecognitionBinding.inflate(layoutInflater)
        textToSpeech = TextToSpeech(requireContext(), null)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val difficultyLevel = arguments?.getString("difficultyLevel")

        viewModel.wordsModelData.observe(viewLifecycleOwner){ result->
            result?.let {
                when(result){
                    is VocabularyWordsResponseState.Success ->{
                        handleOnSuccessResult(result.listOfQuestions)
                    }
                    is VocabularyWordsResponseState.Error ->{
                        Toast.makeText(requireContext(), "Error: ${result.errorException}", Toast.LENGTH_SHORT).show()
                    }
                    is VocabularyWordsResponseState.Loading ->{
                        binding?.progressBarLoadingData?.isVisible = result.isLoading
                    }
                }
            }
        }
        difficultyLevel?.let { viewModel.getWordsList(it) }

        binding?.imagePlaySpeaker?.setOnClickListener {
            pronounceWord()
        }

        binding?.btConfirm?.setOnClickListener {
            hideKeyboard(binding?.btConfirm!!)

            if (wordInSpeaker == binding?.etInputCorrectAnswer?.text.toString().trim().toLowerCase()
                    .capitalize()
            ) {
                changeContinueButtonState(ContinueBtStates.CORRECT)
                answerState = true
            } else {
                changeContinueButtonState(ContinueBtStates.WRONG)
                answerState = false
            }
        }

        binding?.btContinue?.setOnClickListener {
            changeContinueButtonState(ContinueBtStates.NORMAL)
            if (answerState) correctAnswer++

            if (listOfWords.size > 0) {
                increaseStep()
                setQuestion()
            }else{
                showResult()
            }
        }

        binding?.btSkip?.setOnClickListener {
            questionModel?.let { listOfWords.add(0, it) }
            changeContinueButtonState(ContinueBtStates.NORMAL)

            if (listOfWords.size > 0) {
                setQuestion()
            } else {
                showResult()
            }
        }

        binding?.imageButtonClose?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun handleOnSuccessResult(listOfQuestions: List<SimpleWordsModel>){
        changeContinueButtonState(ContinueBtStates.NORMAL)

        listOfWords.addAll(listOfQuestions)
        countOfAllQuestions = listOfWords.size

        listOfWords.shuffle()
        setQuestion()

        binding?.progressIndicator?.max = listOfWords.size
        binding?.tvProgressCount?.text = listOfWords.size.toString()
        binding?.progressBarLoadingData?.isVisible = false
    }
    private fun hideKeyboard(view: View){
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun pronounceWord(){
        textToSpeech?.setLanguage(Locale.US)
        textToSpeech?.speak(wordInSpeaker, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun showResult(){
        countOfAllQuestions?.let {
            val wrongAnswer = it - correctAnswer
            val dialogFragment = ResultDialogFragment()
            dialogFragment.isCancelable = false
            dialogFragment.setScore(correctAnswer.toString(), wrongAnswer.toString())
            dialogFragment.show(
                parentFragmentManager,
                ResultDialogFragment::class.java.canonicalName
            )
        }
    }

    private fun increaseStep() {
        binding?.tvProgressCount?.text = listOfWords.size.toString()
        binding?.progressIndicator?.progress = binding?.progressIndicator?.progress?.plus(1) ?: 0
    }

    private fun setQuestion(){
        binding?.etInputCorrectAnswer?.text?.clear()
        wordInSpeaker = listOfWords.last().word
        pronounceWord()
        questionModel = listOfWords.last()
        listOfWords.removeLast()
    }

    private fun changeContinueButtonState(buttonState: ContinueBtStates){
        when(buttonState){
            ContinueBtStates.CORRECT -> {
                binding?.btSkip?.isVisible = false
                binding?.continueButtonLayout?.isVisible = true
                binding?.continueButtonLayout?.setBackgroundResource(R.color.green)
                binding?.imageView?.setImageResource(R.drawable.ic_correct)
                binding?.tvCorrectOrWrong?.text = "Correct!"
                binding?.btContinue?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            ContinueBtStates.WRONG ->{
                binding?.btSkip?.isVisible = true
                binding?.continueButtonLayout?.isVisible = true
                binding?.continueButtonLayout?.setBackgroundResource(R.color.red)
                binding?.imageView?.setImageResource(R.drawable.ic_incorrect)
                binding?.tvCorrectOrWrong?.text = "Wrong!"
                binding?.btContinue?.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }

            ContinueBtStates.NORMAL ->{
                binding?.continueButtonLayout?.isVisible = false
                binding?.btSkip?.isVisible = true
            }
        }
    }

    private enum class ContinueBtStates{
        NORMAL, CORRECT, WRONG
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }

}