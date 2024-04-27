package com.example.englishwordsapp.ui.main.learn.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.databinding.FragmentQuizBinding
import com.example.englishwordsapp.ui.main.learn.ResultDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private var binding: FragmentQuizBinding? = null
    private val viewModel by viewModels<QuizViewModel>()
    private var countOfAllQuestions: Int? = null
    private var wrongAnswer = 0
    private val variantsAdapter by lazy { QuizVariantsListAdapter() }
    private val listOfWords = mutableListOf<QuizQuestionsModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rcViewVariants?.adapter = variantsAdapter
        val difficultyLevel = arguments?.getString("difficultyLevel")

        viewModel.questionModelData.observe(viewLifecycleOwner){result ->
            result?.let {
                when(result){
                    is QuizState.Success ->{
                        val list = mutableListOf<Pair<String, String>>()
                        binding?.progressBarLoadingData?.isVisible = false
                        binding?.tvQuestionWord?.text = result.question.question
                        val map = result.question.variants
                        map.forEach { s, s2 ->
                            list.add(Pair(s,s2))
                        }
                        variantsAdapter.submitList(list)
                    }
                    is QuizState.Error ->{

                    }
                    is QuizState.Loading ->{
                        binding?.progressBarLoadingData?.isVisible = result.isLoading
                    }
                }
            }
        }

        difficultyLevel?.let { viewModel.getQuestionList(it) }

        variantsAdapter.onItemClickListener { variant ->
            if (variant.first == "true"){
                changeContinueButtonState(ContinueBtStates.CORRECT)
            }
            else {
                changeContinueButtonState(ContinueBtStates.WRONG)
                wrongAnswer++
            }
        }

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


        binding?.btContinue?.setOnClickListener {
            changeContinueButtonState(ContinueBtStates.NORMAL)
            binding?.progressIndicator?.progress = binding?.progressIndicator?.progress?.plus(1) ?: 0
            binding?.rcViewVariants?.isClickable = true

            if (viewModel.questionsList.isNotEmpty()){
                viewModel.setNewQuestion()
            }
            else{
                showResult()
            }
        }

        viewModel.question.observe(viewLifecycleOwner){question->
            question?.let {
                val list = mutableListOf<Pair<String, String>>()
                binding?.tvQuestionWord?.text = question.question
                val map = question.variants
                map.forEach { s, s2 ->
                    list.add(Pair(s,s2))
                }
                variantsAdapter.submitList(list)
            }
        }

        binding?.btSkip?.setOnClickListener {
                viewModel.skipQuestion()
        }

        binding?.imageButtonClose?.setOnClickListener {
            findNavController().popBackStack()
        }

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

//        false.changeVariantsClickState()
    }

    private fun handleWrongAnswerButton() {
        binding?.btSkip?.isVisible = false
        binding?.continueButtonLayout?.isVisible = true
        binding?.continueButtonLayout?.setBackgroundResource(R.color.red)
        binding?.imageView?.setImageResource(R.drawable.ic_incorrect)
        binding?.tvCorrectOrWrong?.text = "Wrong!"
        binding?.btContinue?.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

//        false.changeVariantsClickState()
    }

    private fun normalStateContinueButton() {
        binding?.continueButtonLayout?.isVisible = false
        binding?.btSkip?.isVisible = true

//        true.changeVariantsClickState()
    }


    private fun checkAnswer() {

    }



    private enum class ContinueBtStates{
        NORMAL, CORRECT, WRONG
    }

    private enum class VariantStates {
        NORMAL, CORRECT, WRONG
    }

}

