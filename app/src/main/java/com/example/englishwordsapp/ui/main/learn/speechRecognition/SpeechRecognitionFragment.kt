package com.example.englishwordsapp.ui.main.learn.speechRecognition

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
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
import com.example.englishwordsapp.databinding.FragmentSpeechRecognitionBinding
import com.example.englishwordsapp.ui.main.learn.dialogFragments.ResultDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SpeechRecognitionFragment : Fragment(), TextToSpeech.OnInitListener {
    private var binding: FragmentSpeechRecognitionBinding? = null
    private lateinit var textToSpeech: TextToSpeech
    private var isResultShown = false
    private val viewModel by viewModels<SpeechRecognitionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentSpeechRecognitionBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            textToSpeech.language = Locale.US

            val voices = textToSpeech.voices
            val selectedVoice = voices.firstOrNull { it.locale == Locale.US && it.quality == Voice.QUALITY_HIGH}
            selectedVoice?.let {
                textToSpeech.voice = it
            }
        }else{
            Toast.makeText(requireContext(), "A sound error occurred", Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textToSpeech = TextToSpeech(requireContext(), this)

        val difficultyLevel = arguments?.getString("difficultyLevel")
        difficultyLevel?.let { viewModel.loadWords(it) }

        viewModel.progress.observe(viewLifecycleOwner){count->
            count?.let {
                binding?.tvProgressCount?.text = count.inc().toString()
            }
        }

        viewModel.countOfQuestions.observe(viewLifecycleOwner){count->
            count?.let {
                binding?.progressIndicator?.max = count
            }
        }

        viewModel.wordsModelData.observe(viewLifecycleOwner){word->
            when(word){
                is WordRecognitionState.Success->{
                    binding?.progressBarLoadingData?.isVisible = false
                    word.listOfQuestions.word?.let { pronounceWord(it) }
                }

                is WordRecognitionState.Error -> {
                }

                is WordRecognitionState.Loading -> {
                    binding?.progressBarLoadingData?.isVisible = word.isLoading
                }
            }
        }

        binding?.btConfirm?.setOnClickListener {
            false.isClickable()
            hideKeyboard(binding?.btConfirm!!)
            viewModel.checkAnswer(binding?.etInputCorrectAnswer?.text.toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
            viewModel.state.observe(viewLifecycleOwner) { state ->
                state?.let {
                    when (state) {
                        is SpeechRecognitionAnswerWordState.CORRECT -> {
                            changeContinueButtonState(ContinueBtStates.CORRECT)
                        }

                        is SpeechRecognitionAnswerWordState.WRONG -> {
                            changeContinueButtonState(ContinueBtStates.WRONG)
                        }

                        is SpeechRecognitionAnswerWordState.CorrectLast -> {
                            changeContinueButtonState(ContinueBtStates.CORRECT)
                            if (!isResultShown) {
                                showResult(state.countOfAllQuestions, state.countOfCorrectAnswer)
                                isResultShown = true
                            }
                        }

                        is SpeechRecognitionAnswerWordState.WrongLast -> {
                            changeContinueButtonState(ContinueBtStates.WRONG)
                            if (!isResultShown) {
                                showResult(state.countOfAllQuestions, state.countOfCorrectAnswer)
                                isResultShown = true
                            }
                        }
                    }
                }
            }
        }

        binding?.imagePlaySpeaker?.setOnClickListener {
            viewModel.word.observe(viewLifecycleOwner){word->
                word?.let {
                    it.word?.let { it1 -> pronounceWord(it1) }
                }
            }
        }

        binding?.btContinue?.setOnClickListener {
            true.isClickable()
            increaseStep()
            changeContinueButtonState(ContinueBtStates.NORMAL)
            viewModel.startRecognition()
            viewModel.word.observe(viewLifecycleOwner){word->
                word?.let {
                    it.word?.let { it1 -> pronounceWord(it1) }
                }
            }
        }

        binding?.btSkip?.setOnClickListener {
            viewModel.skipWord()
            viewModel.word.observe(viewLifecycleOwner){word->
                word?.let {
                    it.word?.let { it1 -> pronounceWord(it1) }
                }
            }
        }

        binding?.imageButtonClose?.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun hideKeyboard(view: View){
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun pronounceWord(word: String){
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun showResult(countOfAllQuestions: Int, countOfCorrectAnswer: Int){
            val wrongAnswer = countOfAllQuestions - countOfCorrectAnswer
            val dialogFragment = ResultDialogFragment()
            dialogFragment.isCancelable = false
            dialogFragment.setScore(countOfCorrectAnswer.toString(), wrongAnswer.toString())
            dialogFragment.show(
                parentFragmentManager,
                ResultDialogFragment::class.java.canonicalName
            )

    }

    private fun increaseStep() {
        binding?.progressIndicator?.progress = binding?.progressIndicator?.progress?.plus(1) ?: 0
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
                binding?.btSkip?.isVisible = false
                binding?.continueButtonLayout?.isVisible = true
                binding?.continueButtonLayout?.setBackgroundResource(R.color.red)
                binding?.imageView?.setImageResource(R.drawable.ic_incorrect)
                binding?.tvCorrectOrWrong?.text = "Wrong!"
                binding?.btContinue?.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }

            ContinueBtStates.NORMAL ->{
                binding?.continueButtonLayout?.isVisible = false
                binding?.btSkip?.isVisible = true
                binding?.etInputCorrectAnswer?.text?.clear()
            }
        }
    }

    private fun Boolean.isClickable(){
        binding?.btConfirm?.isClickable = this
        binding?.etInputCorrectAnswer?.isEnabled = this

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