package com.example.englishwordsapp

import android.content.Context
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.englishwordsapp.databinding.ExampleWordTranslationBinding
import java.util.Locale

class RcAdapterForWordsTranslation(private var listener: RvOnClickListener? = null, ): RecyclerView.Adapter<RcAdapterForWordsTranslation.MyHolder>() {

    private val listOfWords = mutableListOf<SimpleWordsModel>()

    fun setOnClickListener(clickListener: RvOnClickListener){
        this.listener = clickListener
    }

    interface RvOnClickListener{
        fun onClick(data: SimpleWordsModel)

        fun playSpeaker(text: String, position: Int)
    }


    fun updateData(newList: List<SimpleWordsModel>){
        listOfWords.clear()
        listOfWords.addAll(newList)
        notifyDataSetChanged()
    }

    class MyHolder(var binding: ExampleWordTranslationBinding): ViewHolder(binding.root) {

        fun bind(data: SimpleWordsModel, listener: RvOnClickListener?, position: Int){
            binding.tvWordInEng.text = data.wordInEnglish
            binding.tvWordInAze.text = data.wordInAze
            binding.tvWordTranscript.text = data.transcription


            binding.imagePlaySpeaker.setOnClickListener {
                listener?.playSpeaker(data.wordInEnglish!!, position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = ExampleWordTranslationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(view)
    }

    override fun getItemCount() = listOfWords.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(listOfWords[position], listener, position)
    }
}