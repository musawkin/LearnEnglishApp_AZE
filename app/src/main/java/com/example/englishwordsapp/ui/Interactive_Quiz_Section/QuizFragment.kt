package com.example.englishwordsapp.ui.Interactive_Quiz_Section

import android.graphics.Color
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
import com.example.englishwordsapp.databinding.FragmentQuizBinding
import com.example.englishwordsapp.ui.ResultDialogFragment


class QuizFragment : Fragment() {

    private var binding: FragmentQuizBinding? = null
    private val viewModel by viewModels<QuizViewModel>()
    private val listOfWords = mutableListOf<QuizQuestionsModel>()
    private var correctAnswer: String? = null
    private var wrongAnswer = 0
    private var countOfAllQuestions: Int? = null
    private var questionModel: QuizQuestionsModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.questionModelData.observe(viewLifecycleOwner){result ->
            result?.let {
                when(result){
                    is QuizQuestionsResponseState.Success ->{
                        listOfWords.addAll(result.listOfQuestions)

                        binding?.progressIndicator?.max = listOfWords.size
                        binding?.tvProgressCount?.text = listOfWords.size.toString()
                        countOfAllQuestions = listOfWords.size

                        listOfWords.shuffle()
                        questionModel = listOfWords.last()
                        listOfWords.removeLast()
                        val questionWord = questionModel?.question
                        val answerWords = questionModel?.answers?.shuffled()
                        correctAnswer = questionModel?.correctAnswer
                        binding?.tvQuestionWord?.text = questionWord

                        binding?.tvAnswer1?.text = answerWords?.get(0)
                        binding?.tvAnswer2?.text = answerWords?.get(1)
                        binding?.tvAnswer3?.text = answerWords?.get(2)
                        binding?.tvAnswer4?.text = answerWords?.get(3)

                        binding?.progressBarLoadingData?.isVisible = false

                    }
                    is QuizQuestionsResponseState.Error ->{
                        val errorText = result.errorException
                        Toast.makeText(requireContext(), "Error: $errorText", Toast.LENGTH_LONG).show()
                    }
                    is QuizQuestionsResponseState.Loading ->{
                        val loading = result.isLoading
                        binding?.progressBarLoadingData?.isVisible = loading
                    }
                }
            }
        }
        viewModel.getQuestionList("beginner_level")

        binding?.layoutVariant1?.setOnClickListener {
            if (correctAnswer == binding?.tvAnswer1?.text){
                checkAnswer(binding?.layoutVariant1, binding?.tvAnswer1Nuber, binding?.tvAnswer1, VariantStates.CORRECT)
            }else{
                checkAnswer(binding?.layoutVariant1, binding?.tvAnswer1Nuber, binding?.tvAnswer1, VariantStates.WRONG)

            }        }
        binding?.layoutVariant2?.setOnClickListener {
            if (correctAnswer == binding?.tvAnswer2?.text){
                checkAnswer(binding?.layoutVariant2, binding?.tvAnswer2Nuber, binding?.tvAnswer2, VariantStates.CORRECT)
            }else{
                checkAnswer(binding?.layoutVariant2, binding?.tvAnswer2Nuber, binding?.tvAnswer2, VariantStates.WRONG)

            }        }
        binding?.layoutVariant3?.setOnClickListener {
            if (correctAnswer == binding?.tvAnswer3?.text){
                checkAnswer(binding?.layoutVariant3, binding?.tvAnswer3Nuber, binding?.tvAnswer3, VariantStates.CORRECT)
            }else{
                checkAnswer(binding?.layoutVariant3, binding?.tvAnswer3Nuber, binding?.tvAnswer3, VariantStates.WRONG)

            }        }
        binding?.layoutVariant4?.setOnClickListener {
            if (correctAnswer == binding?.tvAnswer4?.text){
                checkAnswer(binding?.layoutVariant4, binding?.tvAnswer4Nuber, binding?.tvAnswer4, VariantStates.CORRECT)
            }else{
                checkAnswer(binding?.layoutVariant4, binding?.tvAnswer4Nuber, binding?.tvAnswer4, VariantStates.WRONG)

            }
        }

        binding?.btContinue?.setOnClickListener {
            changeContinueButtonState(ContinueBtStates.NORMAL)
            checkAnswer(binding?.layoutVariant1, binding?.tvAnswer1Nuber, binding?.tvAnswer1, VariantStates.NORMAL)
            checkAnswer(binding?.layoutVariant2, binding?.tvAnswer2Nuber, binding?.tvAnswer2, VariantStates.NORMAL)
            checkAnswer(binding?.layoutVariant4, binding?.tvAnswer4Nuber, binding?.tvAnswer4, VariantStates.NORMAL)
            checkAnswer(binding?.layoutVariant3, binding?.tvAnswer3Nuber, binding?.tvAnswer3, VariantStates.NORMAL)

            binding?.tvProgressCount?.text = listOfWords.size.toString()
            binding?.progressIndicator?.progress = binding?.progressIndicator?.progress!! + 1

            if(listOfWords.size > 0) {

                questionModel = listOfWords.last()
                listOfWords.removeLast()
                val questionWord = questionModel?.question
                val answerWords = questionModel?.answers?.shuffled()
                correctAnswer = questionModel?.correctAnswer
                binding?.tvQuestionWord?.text = questionWord

                binding?.tvAnswer1?.text = answerWords?.get(0)
                binding?.tvAnswer2?.text = answerWords?.get(1)
                binding?.tvAnswer3?.text = answerWords?.get(2)
                binding?.tvAnswer4?.text = answerWords?.get(3)
            }else {
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
        }

        binding?.btSkip?.setOnClickListener {

            questionModel?.let { listOfWords.add(0, it) }

            if (listOfWords.size > 0) {

                questionModel = listOfWords.last()
                listOfWords.removeLast()
                val questionWord = questionModel?.question
                val answerWords = questionModel?.answers?.shuffled()
                correctAnswer = questionModel?.correctAnswer
                binding?.tvQuestionWord?.text = questionWord

                binding?.tvAnswer1?.text = answerWords?.get(0)
                binding?.tvAnswer2?.text = answerWords?.get(1)
                binding?.tvAnswer3?.text = answerWords?.get(2)
                binding?.tvAnswer4?.text = answerWords?.get(3)
            } else {
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


        }

        binding?.imageButtonClose?.setOnClickListener {
            findNavController().popBackStack()
        }

    }



    private fun changeContinueButtonState(buttonState: ContinueBtStates){
        when(buttonState){
            ContinueBtStates.CORRECT -> {

                binding?.continueButtonLayout?.isVisible = true
                binding?.continueButtonLayout?.setBackgroundResource(R.color.green)
                binding?.imageView?.setImageResource(R.drawable.ic_correct)
                binding?.tvCorrectOrWrong?.text = "Correct!"
                binding?.btContinue?.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding?.btSkip?.isVisible = false

                binding?.layoutVariant1?.isClickable = false
                binding?.layoutVariant2?.isClickable = false
                binding?.layoutVariant3?.isClickable = false
                binding?.layoutVariant4?.isClickable = false

            }
            ContinueBtStates.WRONG ->{

                binding?.continueButtonLayout?.isVisible = true
                binding?.continueButtonLayout?.setBackgroundResource(R.color.red)
                binding?.imageView?.setImageResource(R.drawable.ic_incorrect)
                binding?.tvCorrectOrWrong?.text = "Wrong!"
                binding?.btContinue?.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding?.btSkip?.isVisible = false

                binding?.layoutVariant1?.isClickable = false
                binding?.layoutVariant2?.isClickable = false
                binding?.layoutVariant3?.isClickable = false
                binding?.layoutVariant4?.isClickable = false

            }

            ContinueBtStates.NORMAL ->{

                binding?.continueButtonLayout?.isVisible = false
                binding?.btSkip?.isVisible = true

                binding?.layoutVariant1?.isClickable = true
                binding?.layoutVariant2?.isClickable = true
                binding?.layoutVariant3?.isClickable = true
                binding?.layoutVariant4?.isClickable = true
            }
        }
    }

    private fun checkAnswer(
        layout: LinearLayout?,
        textViewVariantNumber: TextView?,
        textViewVariantValue: TextView?,
        variantStates: VariantStates

        ){
        when(variantStates){
            VariantStates.CORRECT ->{
                layout?.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_containers_correct
                )

                textViewVariantNumber?.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_variants_correct
                )

                textViewVariantNumber?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )

                textViewVariantValue?.setTextColor(ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                ))

                changeContinueButtonState(ContinueBtStates.CORRECT)
            }
            VariantStates.WRONG ->{
                layout?.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_containers_wrong
                )

                textViewVariantNumber?.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_variants_wrong
                )

                textViewVariantNumber?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )

                textViewVariantValue?.setTextColor(ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                ))

                changeContinueButtonState(ContinueBtStates.WRONG)
                wrongAnswer++

                when(correctAnswer){
                    binding?.tvAnswer1?.text ->{
                        binding?.layoutVariant1?.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                        binding?.tvAnswer1Nuber?.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                        binding?.tvAnswer1Nuber?.setTextColor(Color.WHITE)
                        binding?.tvAnswer1?.setTextColor(Color.rgb(14,173,105))
                    }

                    binding?.tvAnswer2?.text ->{
                        binding?.layoutVariant2?.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                        binding?.tvAnswer2Nuber?.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                        binding?.tvAnswer2Nuber?.setTextColor(Color.WHITE)
                        binding?.tvAnswer2?.setTextColor(Color.rgb(14,173,105))
                    }

                    binding?.tvAnswer3?.text ->{
                        binding?.layoutVariant3?.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                        binding?.tvAnswer3Nuber?.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                        binding?.tvAnswer3Nuber?.setTextColor(Color.WHITE)
                        binding?.tvAnswer3?.setTextColor(Color.rgb(14,173,105))
                    }

                    binding?.tvAnswer4?.text ->{
                        binding?.layoutVariant4?.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                        binding?.tvAnswer4Nuber?.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                        binding?.tvAnswer4Nuber?.setTextColor(Color.WHITE)
                        binding?.tvAnswer4?.setTextColor(Color.rgb(14,173,105))
                    }
                }
            }
            VariantStates.NORMAL ->{
                layout?.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_containers
                )

                textViewVariantNumber?.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_variants
                )

                textViewVariantNumber?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.textVariantsColor
                    )
                )

                textViewVariantValue?.setTextColor(ContextCompat.getColor(
                    requireContext(),
                    R.color.textVariantsColor
                ))

                changeContinueButtonState(ContinueBtStates.NORMAL)

            }
        }
        }


    private enum class ContinueBtStates{
        NORMAL, CORRECT, WRONG
    }

    private enum class VariantStates {
        NORMAL, CORRECT, WRONG
    }

}

