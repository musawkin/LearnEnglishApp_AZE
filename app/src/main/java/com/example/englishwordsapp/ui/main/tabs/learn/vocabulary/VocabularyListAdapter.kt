package com.example.englishwordsapp.ui.main.tabs.learn.vocabulary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.englishwordsapp.databinding.ExampleWordTranslationBinding
import com.example.englishwordsapp.ui.main.tabs.learn.SimpleWordsModel

class VocabularyListAdapter: ListAdapter<SimpleWordsModel, VocabularyListAdapter.VocabularyWH>(DIFF_UTIL) {

    private var onItemClik: ((word: SimpleWordsModel) -> Unit)? = null

    fun onItemClickListener(onItemClick: (word: SimpleWordsModel) -> Unit){
        this.onItemClik = onItemClick
    }
    class VocabularyWH(
        private val binding: ExampleWordTranslationBinding
    ): ViewHolder(binding.root) {
        fun bind(data: SimpleWordsModel, onItemClick: ((word: SimpleWordsModel) -> Unit)?){
            binding.tvWordInEng.text = data.word
            binding.tvWordInAze.text = data.translationToAze
            binding.tvWordTranscript.text = data.transcription
            binding.imagePlaySpeaker.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyWH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExampleWordTranslationBinding.inflate(inflater, parent, false)
        return VocabularyWH(binding)
    }

    override fun onBindViewHolder(holder: VocabularyWH, position: Int) {
        holder.bind(currentList[position], onItemClik)
    }

    companion object{
        val DIFF_UTIL = object : DiffUtil.ItemCallback<SimpleWordsModel>(){
            override fun areItemsTheSame(
                oldItem: SimpleWordsModel,
                newItem: SimpleWordsModel
            ): Boolean {
                return oldItem.word == newItem.word
            }

            override fun areContentsTheSame(
                oldItem: SimpleWordsModel,
                newItem: SimpleWordsModel
            ): Boolean {
                return oldItem.word == newItem.word &&
                        oldItem.transcription == newItem.transcription &&
                        oldItem.translationToAze == newItem.translationToAze  &&
                        oldItem.level == newItem.level  &&
                        oldItem.partOfSpeech == newItem.partOfSpeech
            }

        }
    }
}