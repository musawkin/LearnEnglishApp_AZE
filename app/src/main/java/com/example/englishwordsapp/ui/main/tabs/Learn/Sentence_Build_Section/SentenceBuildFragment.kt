package com.example.englishwordsapp.ui.main.tabs.Learn.Sentence_Build_Section

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.databinding.FragmentSentenceBuildBinding
import com.google.android.material.chip.Chip

class SentenceBuildFragment : Fragment() {
    private var binding: FragmentSentenceBuildBinding? = null
    private val viewModel by viewModels<SentenceBuildViewModel>()
    private val answerList = mutableListOf<String>()
    private val suggestedList = mutableListOf<String>()
    private var listOfSentences = mutableListOf<SentenceModel>()
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

        viewModel.questionModelData.observe(viewLifecycleOwner) { result ->
            result?.let {
                when (result) {
                    is SentenceModelResponseState.Success -> {
                        handleOnSuccessResult(result.listOfQuestions)
                        createSuggestedView()
                        createAnsweredView()
                    }
                    is SentenceModelResponseState.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.errorException}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is SentenceModelResponseState.Loading -> {
                        binding?.progressBarLoadingData?.isVisible = result.isLoading
                    }
                }
            }
        }
        difficultyLevel?.let { viewModel.getSentenceModel(it) }

        binding?.btConfirm?.setOnClickListener {
            checkAnswerList()
        }

        binding?.imageButtonClose?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun handleOnSuccessResult(listOfQuestions: List<SentenceModel>){
        listOfSentences.addAll(listOfQuestions)

        listOfSentences.shuffle()
        sentenceIndex = listOfSentences.size - 1
        val sentence = listOfSentences[sentenceIndex]
        val sentenceInEng = sentence.answerWordsList
        val sentenceInAze = sentence.question
        binding?.tvSentenceInAze?.text = sentenceInAze
        suggestedList.addAll(sentenceInEng)
        suggestedList.shuffle()

        binding?.progressIndicator?.max = listOfSentences.size
        binding?.tvProgressCount?.text = listOfSentences.size.toString()

        binding?.progressBarLoadingData?.isVisible = false
    }

    private fun createSuggestedView() {
        binding?.suggestedChipGroup?.removeAllViews()
        suggestedList.forEach { item ->

            addSuggestedViewItem(item)
        }
    }

    private fun createAnsweredView() {
        binding?.answerChipGroup?.removeAllViews()
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

        if (sentenceIndex > 0) {
            val sentence = listOfSentences[sentenceIndex]
            val rightAnswerList = sentence.answerWordsList

            var isRight = false

            isRight = answerList.size != 0 && answerList == rightAnswerList

            if (isRight) {
                listOfSentences.removeLast()
                binding?.suggestedChipGroup?.removeAllViews()
                binding?.answerChipGroup?.removeAllViews()
                suggestedList.clear()
                answerList.clear()

                sentenceIndex--

                val sentence = listOfSentences[sentenceIndex]
                val sentenceInAze = sentence.question
                binding?.tvSentenceInAze?.text = sentenceInAze

                suggestedList.addAll(sentence.answerWordsList)
                suggestedList.shuffle()
                suggestedList.forEach { item ->
                    addSuggestedViewItem(item)
                }

                binding?.tvProgressCount?.text = listOfSentences.size.toString()
                binding?.progressIndicator?.progress = binding?.progressIndicator?.progress!! + 1

            } else {
                Toast.makeText(requireContext(), "Incorrect sentence", Toast.LENGTH_LONG).show()
            }
        }else {
            val sentence = listOfSentences[sentenceIndex]
            val rightAnswerList = sentence.answerWordsList
            if(answerList.size != 0 && answerList == rightAnswerList){
                Toast.makeText(requireContext(), "Done", Toast.LENGTH_LONG).show()

            }else{
                Toast.makeText(requireContext(), "Incorrect sentence", Toast.LENGTH_LONG).show()

            }
        }
    }
}