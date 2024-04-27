package com.example.englishwordsapp.ui.main.learn.quiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.ExampleForQuizVariantsBinding

class QuizVariantsListAdapter : ListAdapter<Pair<String, String>, QuizVariantsListAdapter.QuizWH>(
    DIF_UTIL
) {
    private var onItemClick: ((variant: Pair<String, String>) -> Unit)? = null


    fun onItemClickListener(onItemClick: (variant: Pair<String, String>) -> Unit) {
        this.onItemClick = onItemClick
    }

    class QuizWH(
        private val binding: ExampleForQuizVariantsBinding
    ) : ViewHolder(binding.root) {
        private var correctItem = 0L
        fun bind(data: Pair<String, String>, position: Int, onItemClick: ((variant: Pair<String, String>) -> Unit)?) {
            binding.tvContainerNumber.text = (position + 1).toString()
            binding.tvContainerAnswer.text = data.second

            binding.linearLayout.setBackgroundResource(R.drawable.shape_rounded_containers)
            binding.tvContainerNumber.setBackgroundResource(R.drawable.shape_rounded_variants)
            binding.tvContainerNumber.setTextColor(Color.GRAY)
            binding.tvContainerAnswer.setTextColor(Color.GRAY)

            itemView.setOnClickListener {
                if(data.first == "true") {
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
        holder.bind(currentList[position], position, onItemClick)

    }

    companion object {
        val DIF_UTIL = object : DiffUtil.ItemCallback<Pair<String, String>>() {
            override fun areItemsTheSame(
                oldItem: Pair<String, String>,
                newItem: Pair<String, String>
            ): Boolean {
                return oldItem.first == newItem.first
            }

            override fun areContentsTheSame(
                oldItem: Pair<String, String>,
                newItem: Pair<String, String>
            ): Boolean {
                return oldItem.first == newItem.first &&
                        oldItem.second == newItem.second
            }


        }

    }
}

