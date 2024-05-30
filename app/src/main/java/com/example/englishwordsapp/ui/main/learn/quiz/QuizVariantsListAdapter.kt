package com.example.englishwordsapp.ui.main.learn.quiz

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.ExampleForQuizVariantsBinding

class QuizVariantsListAdapter : ListAdapter<AnswerModel, QuizVariantsListAdapter.QuizWH>(
    DIF_UTIL
) {
    private var onItemClick: ((variant: AnswerModel) -> Unit)? = null


    fun onItemClickListener(onItemClick: (variant: AnswerModel) -> Unit) {
        this.onItemClick = onItemClick
    }

    class QuizWH(
        private val binding: ExampleForQuizVariantsBinding
    ) : ViewHolder(binding.root) {
        private var correctItem = 0L
        fun bind(data: AnswerModel, position: Int, onItemClick: ((AnswerModel) -> Unit)?) {
            binding.tvContainerNumber.text = position.inc().toString()
            binding.tvContainerAnswer.text = data.answer

            binding.linearLayout.setBackgroundResource(R.drawable.shape_rounded_containers)
            binding.tvContainerNumber.setBackgroundResource(R.drawable.shape_rounded_variants)
            binding.tvContainerNumber.setTextColor(Color.GRAY)
            binding.tvContainerAnswer.setTextColor(Color.GRAY)

            itemView.setOnClickListener {
                if(data.isCorrect) {
                    onItemClick?.invoke(data)
                    binding.linearLayout.setBackgroundResource(R.drawable.shape_rounded_containers_correct)
                    binding.tvContainerNumber.setBackgroundResource(R.drawable.shape_rounded_variants_correct)
                    binding.tvContainerNumber.setTextColor(Color.WHITE)
                    binding.tvContainerAnswer.setTextColor(Color.GREEN)
                }
                else{
                    onItemClick?.invoke(data)
                    binding.linearLayout.setBackgroundResource(R.drawable.shape_rounded_containers_wrong)
                    binding.tvContainerNumber.setBackgroundResource(R.drawable.shape_rounded_variants_wrong)
                    binding.tvContainerNumber.setTextColor(Color.WHITE)
                    binding.tvContainerAnswer.setTextColor(Color.RED)

                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizWH {
        val inflater = LayoutInflater.from(parent.context)
        val view = ExampleForQuizVariantsBinding.inflate(inflater, parent, false)
        return QuizWH(view)

    }

    override fun onBindViewHolder(holder: QuizWH, position: Int) {
        holder.bind(getItem(position), position, onItemClick)

    }

    companion object {
        val DIF_UTIL = object : DiffUtil.ItemCallback<AnswerModel>() {
            override fun areItemsTheSame(
                oldItem: AnswerModel,
                newItem: AnswerModel
            ): Boolean {
                return oldItem.answer == newItem.answer
            }

            override fun areContentsTheSame(
                oldItem: AnswerModel,
                newItem: AnswerModel
            ): Boolean {
                return oldItem.answer == newItem.answer &&
                        oldItem.isCorrect == newItem.isCorrect
            }


        }

    }
}

