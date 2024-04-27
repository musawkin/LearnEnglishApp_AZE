package com.example.englishwordsapp.ui.main.learn.sentenceBuild

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.FragmentSentenceBuildBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SentenceBuildFragment : Fragment() {
    private var binding: FragmentSentenceBuildBinding? = null
    private val viewModel by viewModels<SentenceBuildViewModel>()
    private val answerList = mutableListOf<String>()
    private val suggestedList = mutableListOf<String>()
    private var listOfSentences = mutableListOf<SentenceModel>()
    private var sentenceModelForSkipButton: SentenceModel? = null
    private var sentenceIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSentenceBuildBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val difficultyLevel = arguments?.getString("difficultyLevel")

        viewModel.responseModelData.observe(viewLifecycleOwner) { result ->
            result?.let {
                when (result) {
                    is SentenceBuildState.Success -> {
                        setQuestion(result.sentenceModel)
                    }
                    is SentenceBuildState.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.errorException}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is SentenceBuildState.Loading -> {
                        binding?.progressBarLoadingData?.isVisible = result.isLoading
                    }
                }
            }
        }
        difficultyLevel?.let { viewModel.getSentenceModel(it) }

        binding?.btConfirm?.setOnClickListener {
            checkAnswerList()
        }

        binding?.btSkip?.setOnClickListener {
            changeContinueButtonState(ContinueBtStates.NORMAL)

        }

        binding?.btContinue?.setOnClickListener {
            checkAnswerList()
            changeContinueButtonState(ContinueBtStates.NORMAL)
        }

        binding?.imageButtonClose?.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun  setQuestion(sentenceModel: SentenceModel){
        binding?.suggestedChipGroup?.removeAllViews()
        binding?.answerChipGroup?.removeAllViews()
        suggestedList.clear()
        answerList.clear()

        binding?.tvSentenceInAze?.text = sentenceModel.question
        suggestedList.addAll(sentenceModel.answerWordsList)
        suggestedList.shuffle()
        createSuggestedView()
        createAnsweredView()
    }

    private fun createSuggestedView() {
        suggestedList.forEach { item ->
            addSuggestedViewItem(item)
        }
    }

    private fun createAnsweredView() {
        answerList.forEach { item ->
            addEmptyViewItem(item)
        }
    }

    private fun onSuggestedItemClicked(item: String) {

        answerList.add(item)
        removeSuggestedViewItem(item)
        addEmptyViewItem(item)
    }

    private fun onAnsweredItemClicked(item: String) {

        answerList.remove(item)
        removeEmptyViewItem(item)
        addSuggestedViewItem(item)

    }

    private fun addSuggestedViewItem(item: String) {
        val chip = Chip(requireContext())
        chip.text = item
        chip.tag = item
        chip.setOnClickListener {
            onSuggestedItemClicked(item)
        }
        binding?.suggestedChipGroup?.addView(chip)
    }

    private fun removeSuggestedViewItem(tag: String) {
        val chip = binding?.suggestedChipGroup?.findViewWithTag<Chip>(tag)
        binding?.suggestedChipGroup?.removeView(chip)
    }

    private fun addEmptyViewItem(item: String) {
        val chip = Chip(requireContext())
        chip.text = item
        chip.tag = item
        chip.setOnClickListener {
            onAnsweredItemClicked(item)
        }
        binding?.answerChipGroup?.addView(chip)
    }

    private fun removeEmptyViewItem(tag: String) {
        val chip = binding?.answerChipGroup?.findViewWithTag<Chip>(tag)
        binding?.answerChipGroup?.removeView(chip)
    }

    private fun checkAnswerList() {


//            isRight = answerList.size != 0 && answerList == rightAnswerList
//            changeContinueButtonState(ContinueBtStates.CORRECT)
//
//            if (isRight) {
//
//                setQuestion()
//
//                binding?.tvProgressCount?.text = listOfSentences.size.toString()
//                binding?.progressIndicator?.progress = binding?.progressIndicator?.progress!! + 1
//
//            } else {
//                changeContinueButtonState(ContinueBtStates.WRONG)
//            }
//        }else {
//            val sentence = listOfSentences[sentenceIndex]
//            val rightAnswerList = sentence.answerWordsList
//            if(answerList.size != 0 && answerList == rightAnswerList){
//                Toast.makeText(requireContext(), "Done", Toast.LENGTH_LONG).show()
//
//            }else{
//                changeContinueButtonState(ContinueBtStates.WRONG)
//            }
//        }
    }

    private fun changeContinueButtonState(buttonState: ContinueBtStates){
        when(buttonState){
            ContinueBtStates.CORRECT -> handleCorrectAnswerButton()
            ContinueBtStates.WRONG -> handleWrongAnswerButton()
            ContinueBtStates.NORMAL -> normalStateContinueButton()
        }
    }

    private fun handleCorrectAnswerButton() {
        binding?.btSkip?.isVisible = false
        binding?.continueButtonLayout?.isVisible = true
        binding?.continueButtonLayout?.setBackgroundResource(R.color.green)
        binding?.imageView?.setImageResource(R.drawable.ic_correct)
        binding?.tvCorrectOrWrong?.text = "Correct!"
        binding?.btContinue?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))

    }

    private fun handleWrongAnswerButton() {
        binding?.continueButtonLayout?.isVisible = true
        binding?.continueButtonLayout?.setBackgroundResource(R.color.red)
        binding?.imageView?.setImageResource(R.drawable.ic_incorrect)
        binding?.tvCorrectOrWrong?.text = "Wrong!"
        binding?.btContinue?.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

    }

    private fun normalStateContinueButton() {
        binding?.continueButtonLayout?.isVisible = false
        binding?.btSkip?.isVisible = true

    }

    private enum class ContinueBtStates{
        NORMAL, CORRECT, WRONG
    }

}