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
import com.example.englishwordsapp.ui.main.learn.ResultDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SentenceBuildFragment : Fragment() {
    private var binding: FragmentSentenceBuildBinding? = null
    private val viewModel by viewModels<SentenceBuildViewModel>()
    private var countOfAllQuestions: Int? = null
    private var wrongAnswer = 0
    private val answerList = mutableListOf<String>()
    private val suggestedList = mutableListOf<String>()

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

        viewModel.countOfQuestions.observe(viewLifecycleOwner){count->
            count?.let {
                countOfAllQuestions = count
                binding?.progressIndicator?.max = count
            }
        }

        viewModel.progress.observe(viewLifecycleOwner){progress->
            progress?.let {
                binding?.tvProgressCount?.text = (progress + 1).toString()
            }
        }

        binding?.btConfirm?.setOnClickListener {
            if (viewModel.chekAnswer(answerList)){
                changeContinueButtonState(ContinueBtStates.CORRECT)
            }else{
                changeContinueButtonState(ContinueBtStates.WRONG)
                wrongAnswer++
            }
        }

        binding?.btSkip?.setOnClickListener {
            viewModel.skipQuestion()
        }

        viewModel.questionModel.observe(viewLifecycleOwner){question->
            question?.let {
                setQuestion(question)
            }
        }

        binding?.btContinue?.setOnClickListener {
            changeContinueButtonState(ContinueBtStates.NORMAL)
            binding?.progressIndicator?.progress = binding?.progressIndicator?.progress?.plus(1) ?: 0

            if (viewModel.sentencesList.isNotEmpty()){
                viewModel.setQuestion()
            }else{
                showResult()
            }
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

    private fun showResult(){
        countOfAllQuestions?.let {
            val countOfCorrectAnswer = it - wrongAnswer
            val dialogFragment = ResultDialogFragment()
            dialogFragment.isCancelable = false
            dialogFragment.setScore(countOfCorrectAnswer.toString(), wrongAnswer.toString())
            dialogFragment.show(
                parentFragmentManager,
                ResultDialogFragment::class.java.canonicalName
            )
        }
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

        binding?.btConfirm?.isClickable = false
    }

    private fun handleWrongAnswerButton() {
        binding?.btSkip?.isVisible = false
        binding?.continueButtonLayout?.isVisible = true
        binding?.continueButtonLayout?.setBackgroundResource(R.color.red)
        binding?.imageView?.setImageResource(R.drawable.ic_incorrect)
        binding?.tvCorrectOrWrong?.text = "Wrong!"
        binding?.btContinue?.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        binding?.btConfirm?.isClickable = false
    }

    private fun normalStateContinueButton() {
        binding?.continueButtonLayout?.isVisible = false
        binding?.btSkip?.isVisible = true

        binding?.btConfirm?.isClickable = true
    }

    private enum class ContinueBtStates{
        NORMAL, CORRECT, WRONG
    }

}