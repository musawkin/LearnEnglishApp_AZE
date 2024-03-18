package com.example.englishwordsapp.ui.main.tabs.Learn.Interactive_Quiz_Section

import android.graphics.Color
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.FragmentQuizBinding
import com.example.englishwordsapp.databinding.FragmentWithViewPagerQuizBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class WithViewPagerQuizFragment : Fragment() {
    private var binding: FragmentWithViewPagerQuizBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWithViewPagerQuizBinding.inflate(layoutInflater, container, false)
        provideWords()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeContinueButtonState(ContinueBtStates.NORMAL)

        val shuffAnswerledList = listOfWordsModel.last().answers.shuffled()



        binding?.tvAnswer1?.text = shuffAnswerledList[0]
        binding?.tvAnswer2?.text = shuffAnswerledList[1]
        binding?.tvAnswer3?.text = shuffAnswerledList[2]
        binding?.tvAnswer4?.text = shuffAnswerledList[3]


        fun checkAnswer(
            layout: LinearLayout?,
            textViewVariantNumber: TextView?,
            textViewVariantValue: TextView?,

        ){
            if (textViewVariantValue?.text == listOfWordsModel.last().correctAnswer){
                layout?.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                textViewVariantNumber?.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                textViewVariantNumber?.setTextColor(Color.WHITE)
                textViewVariantValue.setTextColor(Color.rgb(14,173,105))

                changeContinueButtonState(ContinueBtStates.CORRECT)

            }else {
                layout?.setBackgroundResource(R.drawable.shape_rounded_containers_wrong)
                textViewVariantNumber?.setBackgroundResource(R.drawable.shape_rounded_variants_wrong)
                textViewVariantNumber?.setTextColor(Color.WHITE)
                textViewVariantValue?.setTextColor(Color.rgb(250,65,105))

                changeContinueButtonState(ContinueBtStates.WRONG)

                var correctAnswerIndex: Int? = null
                for (i in shuffAnswerledList){
                    if (i == listOfWordsModel.last().correctAnswer){
                        correctAnswerIndex = shuffAnswerledList.indexOf(i)
                    }
                }

                when(correctAnswerIndex){
                    0 ->{
                        binding?.layoutVariant1?.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                        binding?.tvAnswer1Nuber?.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                        binding?.tvAnswer1Nuber?.setTextColor(Color.WHITE)
                        binding?.tvAnswer1?.setTextColor(Color.rgb(14,173,105))
                    }

                    1 ->{
                        binding?.layoutVariant2?.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                        binding?.tvAnswer2Nuber?.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                        binding?.tvAnswer2Nuber?.setTextColor(Color.WHITE)
                        binding?.tvAnswer2?.setTextColor(Color.rgb(14,173,105))
                    }

                    2 ->{
                        binding?.layoutVariant3?.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                        binding?.tvAnswer3Nuber?.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                        binding?.tvAnswer3Nuber?.setTextColor(Color.WHITE)
                        binding?.tvAnswer3?.setTextColor(Color.rgb(14,173,105))
                    }

                    3 ->{
                        binding?.layoutVariant4?.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                        binding?.tvAnswer4Nuber?.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                        binding?.tvAnswer4Nuber?.setTextColor(Color.WHITE)
                        binding?.tvAnswer4?.setTextColor(Color.rgb(14,173,105))
                    }
                }
            }
        }
        binding?.layoutVariant1?.setOnClickListener {
            checkAnswer(binding?.layoutVariant1, binding?.tvAnswer1Nuber, binding?.tvAnswer1)
        }
        binding?.layoutVariant2?.setOnClickListener {
            checkAnswer(binding?.layoutVariant2, binding?.tvAnswer2Nuber, binding?.tvAnswer2)
        }
        binding?.layoutVariant3?.setOnClickListener {
            checkAnswer( binding?.layoutVariant3, binding?.tvAnswer3Nuber, binding?.tvAnswer3)
        }
        binding?.layoutVariant4?.setOnClickListener {
            checkAnswer(binding?.layoutVariant4, binding?.tvAnswer4Nuber, binding?.tvAnswer4)
        }


    }

    private fun provideWords(){
        val db = Firebase.firestore
        val docRef =
            db.collection("wordsForQuiz")
                .document("beginner_level")
                .collection("questionsModel")
        docRef.get()
            .addOnSuccessListener { documents ->

                for(document in documents){

                    val dataList = document.data
                    val question = dataList["question"] as String
                    val correctAnswer = dataList["correctAnswer"] as String
                    val answersList = dataList["answers"] as List<String>

                    listOfWordsModel.add(QuizQuestionsModel(question,correctAnswer,answersList))
                }

            }.addOnFailureListener { exception ->
                Log.d("Musa", "$exception")
                Toast.makeText(requireContext(), "Error getting document: $exception", Toast.LENGTH_SHORT).show()
            }
    }
    private val listOfWordsModel = mutableListOf<QuizQuestionsModel>(
        QuizQuestionsModel("Apple", "Alma", listOf("Alma", "Kitab", "Şəhər","At")),
        QuizQuestionsModel("Book", "Kitab", listOf("Alma", "At", "Kitab","At")),
        QuizQuestionsModel("Moon", "Ay", listOf("Alma", "Ay", "Şəhər","At")),
    )

    private fun changeContinueButtonState(buttonState: ContinueBtStates){
        when(buttonState){
            ContinueBtStates.CORRECT -> {

                binding?.correctLayout?.isVisible = true
                binding?.correctLayout?.setBackgroundResource(R.color.green)
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

                binding?.correctLayout?.isVisible = true
                binding?.correctLayout?.setBackgroundResource(R.color.red)
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

                binding?.correctLayout?.isVisible = false
                binding?.btSkip?.isVisible = true

                binding?.layoutVariant1?.isClickable = true
                binding?.layoutVariant2?.isClickable = true
                binding?.layoutVariant3?.isClickable = true
                binding?.layoutVariant4?.isClickable = true
            }
        }
    }

    enum class ContinueBtStates{
        NORMAL, CORRECT, WRONG
    }

}

