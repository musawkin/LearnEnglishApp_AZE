package com.example.englishwordsapp.ui.Interactive_Quiz_Section

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.ItemForQuizViewpagerBinding

class ViewPagerAdapter(private var listener: RvOnClikListener? = null): RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {
    private val listOfQuestions = mutableListOf<QuizQuestionsModel>()

    fun setOnClickListener(listener: RvOnClikListener){
        this.listener = listener
    }
    interface RvOnClikListener{
        fun onClick(model: QuizQuestionsModel)
    }
    fun updateData(newList: List<QuizQuestionsModel>){
        listOfQuestions.clear()
        listOfQuestions.addAll(newList)
        notifyDataSetChanged()
    }
    class MyViewHolder(val binding: ItemForQuizViewpagerBinding): ViewHolder(binding.root) {
        fun bind(data: QuizQuestionsModel, click: RvOnClikListener?, position: Int){
            binding.tvQuestionWord.text = data.question

            val shuffledList = data.answers.shuffled()

            binding.tvAnswer1.text = shuffledList[0]
            binding.tvAnswer2.text = shuffledList[1]
            binding.tvAnswer3.text = shuffledList[2]
            binding.tvAnswer4.text = shuffledList[3]


            fun checkAnswer(
                layout: LinearLayout,
                textViewVariantNumber: TextView,
                textViewVariantValue: TextView,
//                continueStates: QuizFragment.ContinueStates,
//                variantStates: QuizFragment.VariantStates
            ){
                if (textViewVariantValue.text == data.correctAnswer){
                    layout.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                    textViewVariantNumber.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                    textViewVariantNumber.setTextColor(Color.WHITE)
                    textViewVariantValue.setTextColor(Color.rgb(14,173,105))

                }else {
                    layout.setBackgroundResource(R.drawable.shape_rounded_containers_wrong)
                    textViewVariantNumber.setBackgroundResource(R.drawable.shape_rounded_variants_wrong)
                    textViewVariantNumber.setTextColor(Color.WHITE)
                    textViewVariantValue.setTextColor(Color.rgb(250,65,105))

                    var correctAnswerIndex: Int? = null
                    for (i in shuffledList){
                        if (i == data.correctAnswer){
                            correctAnswerIndex = shuffledList.indexOf(i)
                        }
                    }

                    when(correctAnswerIndex){
                        0 ->{
                            binding.layoutVariant1.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                            binding.tvAnswer1Nuber.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                            binding.tvAnswer1Nuber.setTextColor(Color.WHITE)
                            binding.tvAnswer1.setTextColor(Color.rgb(14,173,105))
                        }

                        1 ->{
                            binding.layoutVariant2.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                            binding.tvAnswer2Nuber.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                            binding.tvAnswer2Nuber.setTextColor(Color.WHITE)
                            binding.tvAnswer2.setTextColor(Color.rgb(14,173,105))
                        }

                        2 ->{
                            binding.layoutVariant3.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                            binding.tvAnswer3Nuber.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                            binding.tvAnswer3Nuber.setTextColor(Color.WHITE)
                            binding.tvAnswer3.setTextColor(Color.rgb(14,173,105))
                        }

                        3 ->{
                            binding.layoutVariant4.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                            binding.tvAnswer4Nuber.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                            binding.tvAnswer4Nuber.setTextColor(Color.WHITE)
                            binding.tvAnswer4.setTextColor(Color.rgb(14,173,105))
                        }
                    }
                }
            }
            binding.layoutVariant1.setOnClickListener {
                checkAnswer(binding.layoutVariant1, binding.tvAnswer1Nuber, binding.tvAnswer1)
            }
            binding.layoutVariant2.setOnClickListener {
                checkAnswer(binding.layoutVariant2, binding.tvAnswer2Nuber, binding.tvAnswer2)
            }
            binding.layoutVariant3.setOnClickListener {
                checkAnswer( binding.layoutVariant3, binding.tvAnswer3Nuber, binding.tvAnswer3)
            }
            binding.layoutVariant4.setOnClickListener {
                checkAnswer(binding.layoutVariant4, binding.tvAnswer4Nuber, binding.tvAnswer4)
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemForQuizViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = listOfQuestions.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listOfQuestions[position], listener, position)
    }
}