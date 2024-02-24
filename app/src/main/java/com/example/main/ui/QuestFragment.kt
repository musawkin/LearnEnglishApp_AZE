package com.example.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.FragmentQuestBinding
import com.example.main.SimpleWordsModel
import com.example.main.Words


class QuestFragment : Fragment() {

    private var binding: FragmentQuestBinding? = null
    private var questionWord: SimpleWordsModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentQuestBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var answerWords = mutableListOf<String>()
        var wrongAnswer = 0
        var initialSizeListOfWords = listOfWords.size
        binding?.progressIndicator?.max = listOfWords.size

        markAnswer(binding?.layoutVariant1!!, binding?.tvAnswer1Nuber!!, binding?.tvAnswer1!!,
            ContinueStates.NORMAL,
            VariantStates.NORMAL
        )


        binding?.tvProgressCount?.text = listOfWords.size.toString()



        listOfWords.shuffle()

        questionWord = listOfWords.last()
        listOfWords.removeLast()

        listOfWrongAzeWords.shuffle()
        if(questionWord?.wordInAze != listOfWrongAzeWords[0] && questionWord?.wordInAze != listOfWrongAzeWords[1] && questionWord?.wordInAze != listOfWrongAzeWords[2]) {
            answerWords = mutableListOf(
                questionWord?.wordInAze.toString(),
                listOfWrongAzeWords[0],
                listOfWrongAzeWords[1],
                listOfWrongAzeWords[2])
        }else{
            answerWords = mutableListOf(
                questionWord?.wordInAze.toString(),
                listOfWrongAzeWords[3],
                listOfWrongAzeWords[4],
                listOfWrongAzeWords[5])
        }
        answerWords.shuffle()
        binding?.tvAnswer1?.text = answerWords[0]
        binding?.tvAnswer2?.text = answerWords[1]
        binding?.tvAnswer3?.text = answerWords[2]
        binding?.tvAnswer4?.text = answerWords[3]

        binding?.tvQuestionWord?.text = questionWord?.wordInEnglish



        binding?.layoutVariant1?.setOnClickListener {

            if(binding?.tvAnswer1?.text == questionWord?.wordInAze){
                markAnswer(binding?.layoutVariant1!!, binding?.tvAnswer1Nuber!!, binding?.tvAnswer1!!,
                    ContinueStates.CORRECT,
                    VariantStates.CORRECT
                )

            }else{
                markAnswer(binding?.layoutVariant1!!, binding?.tvAnswer1Nuber!!, binding?.tvAnswer1!!,
                    ContinueStates.WRONG,
                    VariantStates.WRONG
                )
                when {
                    binding?.tvAnswer2?.text == questionWord?.wordInAze -> {
                        markAnswer(binding?.layoutVariant2!!, binding?.tvAnswer2Nuber!!, binding?.tvAnswer2!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                    binding?.tvAnswer3?.text == questionWord?.wordInAze -> {
                        markAnswer(binding?.layoutVariant3!!, binding?.tvAnswer3Nuber!!, binding?.tvAnswer3!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                    binding?.tvAnswer4?.text == questionWord?.wordInAze -> {
                        markAnswer(binding?.layoutVariant4!!, binding?.tvAnswer4Nuber!!, binding?.tvAnswer4!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                }

                wrongAnswer++
            }
        }

        binding?.layoutVariant2?.setOnClickListener {

            if(binding?.tvAnswer2?.text == questionWord?.wordInAze){
                markAnswer(binding?.layoutVariant2!!, binding?.tvAnswer2Nuber!!, binding?.tvAnswer2!!,
                    ContinueStates.CORRECT,
                    VariantStates.CORRECT
                )
            }else{
                markAnswer(binding?.layoutVariant2!!, binding?.tvAnswer2Nuber!!, binding?.tvAnswer2!!,
                    ContinueStates.WRONG,
                    VariantStates.WRONG
                )
                when {
                    binding?.tvAnswer1?.text == questionWord?.wordInAze -> {
                        markAnswer(binding?.layoutVariant1!!, binding?.tvAnswer1Nuber!!, binding?.tvAnswer1!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                    binding?.tvAnswer3?.text == questionWord?.wordInAze -> {
                        markAnswer(binding?.layoutVariant3!!, binding?.tvAnswer3Nuber!!, binding?.tvAnswer3!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                    binding?.tvAnswer4?.text == questionWord?.wordInAze -> {
                        markAnswer(binding?.layoutVariant4!!, binding?.tvAnswer4Nuber!!, binding?.tvAnswer4!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                }
                wrongAnswer++
            }
        }

        binding?.layoutVariant3?.setOnClickListener {

            val questionWordInAze = questionWord?.wordInAze

            if(binding?.tvAnswer3?.text == questionWordInAze){
                markAnswer(binding?.layoutVariant3!!, binding?.tvAnswer3Nuber!!, binding?.tvAnswer3!!,
                    ContinueStates.CORRECT,
                    VariantStates.CORRECT
                )
            }else{
                markAnswer(binding?.layoutVariant3!!, binding?.tvAnswer3Nuber!!, binding?.tvAnswer3!!,
                    ContinueStates.WRONG,
                    VariantStates.WRONG
                )
                when {
                    binding?.tvAnswer2?.text == questionWordInAze -> {
                        markAnswer(binding?.layoutVariant2!!, binding?.tvAnswer2Nuber!!, binding?.tvAnswer2!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                    binding?.tvAnswer1?.text == questionWordInAze -> {
                        markAnswer(binding?.layoutVariant1!!, binding?.tvAnswer1Nuber!!, binding?.tvAnswer1!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                    binding?.tvAnswer4?.text == questionWordInAze -> {
                        markAnswer(binding?.layoutVariant4!!, binding?.tvAnswer4Nuber!!, binding?.tvAnswer4!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                }
                wrongAnswer++
            }
        }

        binding?.layoutVariant4?.setOnClickListener {

            if(binding?.tvAnswer4?.text == questionWord?.wordInAze){
                markAnswer(binding?.layoutVariant4!!, binding?.tvAnswer4Nuber!!, binding?.tvAnswer4!!,
                    ContinueStates.CORRECT,
                    VariantStates.CORRECT
                )
            }else{
                markAnswer(binding?.layoutVariant4!!, binding?.tvAnswer4Nuber!!, binding?.tvAnswer4!!,
                    ContinueStates.WRONG,
                    VariantStates.WRONG
                )
                when {
                    binding?.tvAnswer2?.text == questionWord?.wordInAze -> {
                        markAnswer(binding?.layoutVariant2!!, binding?.tvAnswer2Nuber!!, binding?.tvAnswer2!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                    binding?.tvAnswer3?.text == questionWord?.wordInAze -> {
                        markAnswer(binding?.layoutVariant3!!, binding?.tvAnswer3Nuber!!, binding?.tvAnswer3!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                    binding?.tvAnswer1?.text == questionWord?.wordInAze -> {
                        markAnswer(binding?.layoutVariant1!!, binding?.tvAnswer1Nuber!!, binding?.tvAnswer1!!,
                            ContinueStates.WRONG,
                            VariantStates.CORRECT
                        )
                    }
                }
                wrongAnswer++
            }
        }


        binding?.btCorrect?.setOnClickListener {

            markAnswer(binding?.layoutVariant1!!, binding?.tvAnswer1Nuber!!, binding?.tvAnswer1!!,
                ContinueStates.NORMAL,
                VariantStates.NORMAL
            )
            markAnswer(binding?.layoutVariant2!!, binding?.tvAnswer2Nuber!!, binding?.tvAnswer2!!,
                ContinueStates.NORMAL,
                VariantStates.NORMAL
            )
            markAnswer(binding?.layoutVariant3!!, binding?.tvAnswer3Nuber!!, binding?.tvAnswer3!!,
                ContinueStates.NORMAL,
                VariantStates.NORMAL
            )
            markAnswer(binding?.layoutVariant4!!, binding?.tvAnswer4Nuber!!, binding?.tvAnswer4!!,
                ContinueStates.NORMAL,
                VariantStates.NORMAL
            )

            binding?.tvProgressCount?.text = listOfWords.size.toString()
            binding?.progressIndicator?.progress = binding?.progressIndicator?.progress!! + 1

            if (listOfWords.size > 0) {


                questionWord = listOfWords.last()
                listOfWords.removeLast()
                listOfWrongAzeWords.shuffle()


                if(questionWord?.wordInAze != listOfWrongAzeWords[0] && questionWord?.wordInAze != listOfWrongAzeWords[1] && questionWord?.wordInAze != listOfWrongAzeWords[2]) {
                    answerWords = mutableListOf(
                        questionWord?.wordInAze.toString(),
                        listOfWrongAzeWords[0],
                        listOfWrongAzeWords[1],
                        listOfWrongAzeWords[2])
                }else{
                    answerWords = mutableListOf(
                        questionWord?.wordInAze.toString(),
                        listOfWrongAzeWords[3],
                        listOfWrongAzeWords[4],
                        listOfWrongAzeWords[5])
                }

                answerWords.shuffle()
                binding?.tvAnswer1?.text = answerWords[0]
                binding?.tvAnswer2?.text = answerWords[1]
                binding?.tvAnswer3?.text = answerWords[2]
                binding?.tvAnswer4?.text = answerWords[3]

                binding?.tvQuestionWord?.text = questionWord?.wordInEnglish
            }else{

                val countOfCorrectAnswer = initialSizeListOfWords - wrongAnswer
                val dialogFragment = CustomDialogForEndOfQuest()
                dialogFragment.isCancelable = false
                dialogFragment.setScore(countOfCorrectAnswer.toString(), wrongAnswer.toString())
                dialogFragment.show(
                    parentFragmentManager,
                    CustomDialogForEndOfQuest::class.java.canonicalName
                )
            }
        }

        binding?.btSkip?.setOnClickListener {
            listOfWords.add(0, questionWord!!)

            binding?.tvProgressCount?.text = listOfWords.size.toString()

            if (listOfWords.size > 0) {


                questionWord = listOfWords.last()
                listOfWrongAzeWords.shuffle()
                listOfWords.removeLast()

                if(questionWord?.wordInAze != listOfWrongAzeWords[0] && questionWord?.wordInAze != listOfWrongAzeWords[1] && questionWord?.wordInAze != listOfWrongAzeWords[2]) {
                    answerWords = mutableListOf(
                        questionWord?.wordInAze.toString(),
                        listOfWrongAzeWords[0],
                        listOfWrongAzeWords[1],
                        listOfWrongAzeWords[2])
                }else{
                    answerWords = mutableListOf(
                        questionWord?.wordInAze.toString(),
                        listOfWrongAzeWords[3],
                        listOfWrongAzeWords[4],
                        listOfWrongAzeWords[5])
                }
                answerWords.shuffle()
                binding?.tvAnswer1?.text = answerWords[0]
                binding?.tvAnswer2?.text = answerWords[1]
                binding?.tvAnswer3?.text = answerWords[2]
                binding?.tvAnswer4?.text = answerWords[3]

                binding?.tvQuestionWord?.text = questionWord?.wordInEnglish
            }else{

                val countOfCorrectAnswer = initialSizeListOfWords - wrongAnswer
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

    private val listOfWrongAzeWords = mutableListOf("Alma", "Avtomobil", "Pişik", "It", "Masa", "Otaq", "Qapı", "Ulduz",
        "Günəş", "Qələm", "Kitab", "Buz", "Ana", "Ata", "Qardaş", "Bacı", "Telefon","Avtobus", "Məktəb", "Şəhər", "Pendir"
    )

    private fun markAnswer(
        layout: LinearLayout,
        textViewVariantNumber: TextView,
        textViewVariantValue: TextView,
        continueStates: ContinueStates,
        variantStates: VariantStates
    ) {

        when(continueStates){
            ContinueStates.CORRECT -> {

                binding?.correctLayout?.isVisible = true
                binding?.correctLayout?.setBackgroundResource(R.color.green)
                binding?.imageView?.setImageResource(R.drawable.ic_correct)
                binding?.tvCorrectOrWrong?.text = "Correct!"
                binding?.btCorrect?.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding?.btSkip?.isVisible = false

                binding?.layoutVariant1?.isClickable = false
                binding?.layoutVariant2?.isClickable = false
                binding?.layoutVariant3?.isClickable = false
                binding?.layoutVariant4?.isClickable = false

            }
            ContinueStates.WRONG ->{

                binding?.correctLayout?.isVisible = true
                binding?.correctLayout?.setBackgroundResource(R.color.red)
                binding?.imageView?.setImageResource(R.drawable.ic_incorrect)
                binding?.tvCorrectOrWrong?.text = "Wrong!"
                binding?.btCorrect?.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding?.btSkip?.isVisible = false

                binding?.layoutVariant1?.isClickable = false
                binding?.layoutVariant2?.isClickable = false
                binding?.layoutVariant3?.isClickable = false
                binding?.layoutVariant4?.isClickable = false
            }

            ContinueStates.NORMAL ->{

                binding?.correctLayout?.isVisible = false
                binding?.btSkip?.isVisible = true

                binding?.layoutVariant1?.isClickable = true
                binding?.layoutVariant2?.isClickable = true
                binding?.layoutVariant3?.isClickable = true
                binding?.layoutVariant4?.isClickable = true
            }
        }

        when(variantStates){
            VariantStates.CORRECT ->{
                layout.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_containers_correct
                )

                textViewVariantNumber.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_variants_correct
                )

                textViewVariantNumber.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )

                textViewVariantValue.setTextColor(ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                ))
            }
            VariantStates.WRONG ->{
                layout.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_containers_wrong
                )

                textViewVariantNumber.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_variants_wrong
                )

                textViewVariantNumber.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )

                textViewVariantValue.setTextColor(ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                ))
            }
            VariantStates.NORMAL ->{
                layout.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_containers
                )

                textViewVariantNumber.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rounded_variants
                )

                textViewVariantNumber.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.textVariantsColor
                    )
                )

                textViewVariantValue.setTextColor(ContextCompat.getColor(
                    requireContext(),
                    R.color.textVariantsColor
                ))
            }

        }

    }


enum class ContinueStates{
    NORMAL, CORRECT, WRONG
}

enum class VariantStates{
    NORMAL, CORRECT, WRONG
}

}