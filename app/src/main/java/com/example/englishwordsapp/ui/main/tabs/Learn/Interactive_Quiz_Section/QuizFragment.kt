package com.example.englishwordsapp.ui.main.tabs.Learn.Interactive_Quiz_Section

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
import com.example.englishwordsapp.ui.main.tabs.Learn.ResultDialogFragment


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

        val difficultyLevel = arguments?.getString("difficultyLevel")

        viewModel.questionModelData.observe(viewLifecycleOwner){result ->
            result?.let {
                when(result){
                    is QuizQuestionsResponseState.Success ->{
                        handleOnSuccessResult(result.listOfQuestions)
                    }
                    is QuizQuestionsResponseState.Error ->{
                        Toast.makeText(requireContext(), "Error: ${result.errorException}", Toast.LENGTH_LONG).show()
                    }
                    is QuizQuestionsResponseState.Loading ->{
                        binding?.progressBarLoadingData?.isVisible = result.isLoading
                    }
                    else -> {}
                }
            }
        }

        difficultyLevel?.let { viewModel.getQuestionList(it) }

        binding?.layoutVariant1?.setOnClickListener {
            if (correctAnswer == binding?.tvAnswer1?.text) {
                checkAnswer(
                    binding?.layoutVariant1,
                    binding?.tvAnswer1Nuber,
                    binding?.tvAnswer1,
                    VariantStates.CORRECT
                )
            } else {
                checkAnswer(
                    binding?.layoutVariant1,
                    binding?.tvAnswer1Nuber,
                    binding?.tvAnswer1,
                    VariantStates.WRONG
                )

            }
        }

        binding?.layoutVariant2?.setOnClickListener {
            if (correctAnswer == binding?.tvAnswer2?.text) {
                checkAnswer(
                    binding?.layoutVariant2,
                    binding?.tvAnswer2Nuber,
                    binding?.tvAnswer2,
                    VariantStates.CORRECT
                )
            } else {
                checkAnswer(
                    binding?.layoutVariant2,
                    binding?.tvAnswer2Nuber,
                    binding?.tvAnswer2,
                    VariantStates.WRONG
                )

            }
        }

        binding?.layoutVariant3?.setOnClickListener {
            if (correctAnswer == binding?.tvAnswer3?.text) {
                checkAnswer(
                    binding?.layoutVariant3,
                    binding?.tvAnswer3Nuber,
                    binding?.tvAnswer3,
                    VariantStates.CORRECT
                )
            } else {
                checkAnswer(
                    binding?.layoutVariant3,
                    binding?.tvAnswer3Nuber,
                    binding?.tvAnswer3,
                    VariantStates.WRONG
                )

            }
        }

        binding?.layoutVariant4?.setOnClickListener {
            if (correctAnswer == binding?.tvAnswer4?.text) {
                checkAnswer(
                    binding?.layoutVariant4,
                    binding?.tvAnswer4Nuber,
                    binding?.tvAnswer4,
                    VariantStates.CORRECT
                )
            } else {
                checkAnswer(
                    binding?.layoutVariant4,
                    binding?.tvAnswer4Nuber,
                    binding?.tvAnswer4,
                    VariantStates.WRONG
                )

            }
        }

        binding?.btContinue?.setOnClickListener {
            changeContinueButtonState(ContinueBtStates.NORMAL)

            checkAnswer(binding?.layoutVariant1, binding?.tvAnswer1Nuber, binding?.tvAnswer1, VariantStates.NORMAL
            )
            checkAnswer(binding?.layoutVariant2, binding?.tvAnswer2Nuber, binding?.tvAnswer2, VariantStates.NORMAL
            )
            checkAnswer(binding?.layoutVariant4, binding?.tvAnswer4Nuber, binding?.tvAnswer4,VariantStates.NORMAL
            )
            checkAnswer(binding?.layoutVariant3, binding?.tvAnswer3Nuber, binding?.tvAnswer3,VariantStates.NORMAL
            )

            increaseStep()

            if(listOfWords.size > 0) {
                showQuestion()
            }else {
                showResult()
            }
        }

        binding?.btSkip?.setOnClickListener {
            questionModel?.let { listOfWords.add(0, it) }

            if (listOfWords.size > 0) {
                showQuestion()
            } else {
                showResult()
            }
        }

        binding?.imageButtonClose?.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun handleOnSuccessResult(listOfQuestions: List<QuizQuestionsModel>){
        listOfWords.addAll(listOfQuestions)

        binding?.progressIndicator?.max = listOfWords.size
        binding?.tvProgressCount?.text = listOfWords.size.toString()
        countOfAllQuestions = listOfWords.size

        listOfWords.shuffle()
        showQuestion()
        binding?.progressBarLoadingData?.isVisible = false
    }

    private fun increaseStep() {
        binding?.tvProgressCount?.text = listOfWords.size.toString()
        binding?.progressIndicator?.progress = binding?.progressIndicator?.progress?.plus(1) ?: 0
    }

    private fun showQuestion(){
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

        false.changeVariantsClickState()
    }

    private fun handleWrongAnswerButton() {
        binding?.btSkip?.isVisible = false
        binding?.continueButtonLayout?.isVisible = true
        binding?.continueButtonLayout?.setBackgroundResource(R.color.red)
        binding?.imageView?.setImageResource(R.drawable.ic_incorrect)
        binding?.tvCorrectOrWrong?.text = "Wrong!"
        binding?.btContinue?.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        false.changeVariantsClickState()
    }

    private fun normalStateContinueButton() {
        binding?.continueButtonLayout?.isVisible = false
        binding?.btSkip?.isVisible = true

        true.changeVariantsClickState()
    }

    private  fun Boolean.changeVariantsClickState() {
        binding?.layoutVariant1?.isClickable = this
        binding?.layoutVariant2?.isClickable = this
        binding?.layoutVariant3?.isClickable = this
        binding?.layoutVariant4?.isClickable = this
    }
    private fun checkAnswer(
        layout: LinearLayout?,
        textViewVariantNumber: TextView?,
        textViewVariantValue: TextView?,
        variantStates: VariantStates

    ) {
        when (variantStates) {
            VariantStates.CORRECT -> handleCorrectAnswer(
                layout,
                textViewVariantNumber,
                textViewVariantValue
            )

            VariantStates.WRONG -> handleWrongAnswer(
                layout,
                textViewVariantNumber,
                textViewVariantValue
            )

            VariantStates.NORMAL -> variantsNormalState(
                layout,
                textViewVariantNumber,
                textViewVariantValue
            )
        }
    }

    private fun handleCorrectAnswer(
        layout: LinearLayout?,
        textViewVariantNumber: TextView?,
        textViewVariantValue: TextView?,
    ) {
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

        textViewVariantValue?.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.green
            )
        )

        changeContinueButtonState(ContinueBtStates.CORRECT)
    }

    private fun handleWrongAnswer(
        layout: LinearLayout?,
        textViewVariantNumber: TextView?,
        textViewVariantValue: TextView?,
    ){
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

            wrongAnswer++

            when(correctAnswer){
                binding?.tvAnswer1?.text -> {
                    handleCorrectAnswer(
                        binding?.layoutVariant1,
                        binding?.tvAnswer1Nuber,
                        binding?.tvAnswer1
                    )
                }

                binding?.tvAnswer2?.text ->{
                    handleCorrectAnswer(
                        binding?.layoutVariant2,
                        binding?.tvAnswer2Nuber,
                        binding?.tvAnswer2
                    )
                }

                binding?.tvAnswer3?.text ->{
                    handleCorrectAnswer(
                        binding?.layoutVariant3,
                        binding?.tvAnswer3Nuber,
                        binding?.tvAnswer3
                    )
                }

                binding?.tvAnswer4?.text ->{
                    handleCorrectAnswer(
                        binding?.layoutVariant4,
                        binding?.tvAnswer4Nuber,
                        binding?.tvAnswer4
                    )
                }
            }
        changeContinueButtonState(ContinueBtStates.WRONG)
    }

    private fun variantsNormalState(
        layout: LinearLayout?,
        textViewVariantNumber: TextView?,
        textViewVariantValue: TextView?,
    ) {

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

        textViewVariantValue?.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.textVariantsColor
            )
        )

        changeContinueButtonState(ContinueBtStates.NORMAL)
    }


    private enum class ContinueBtStates{
        NORMAL, CORRECT, WRONG
    }

    private enum class VariantStates {
        NORMAL, CORRECT, WRONG
    }

}

