package com.example.englishwordsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.englishwordsapp.databinding.ItemForSentenceRcviewBinding

class RcAdapterForMakingSentenceEMPTY(
    private var listener: RcOnClickListener? = null
): RecyclerView.Adapter<RcAdapterForMakingSentenceEMPTY.MyHolder>() {
    private val listOfWords = mutableListOf<String>()
    fun updateData(newList: List<String>){
        listOfWords.clear()
        listOfWords.addAll(newList)
        notifyDataSetChanged()
    }
    fun setListener(clickListener: RcOnClickListener){
        this.listener = clickListener
    }

    interface RcOnClickListener{
        fun onClick(position: Int)
    }

    class MyHolder(val binding: ItemForSentenceRcviewBinding): ViewHolder(binding.root) {
        fun bind(data: String, listener: RcOnClickListener?, position: Int){
            binding.tvMainText.text = data
            itemView.setOnClickListener {
                listener?.onClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = ItemForSentenceRcviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(view)
    }

    override fun getItemCount() = listOfWords.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(listOfWords[position], listener, position)
    }
}