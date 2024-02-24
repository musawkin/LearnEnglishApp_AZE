package com.example.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.englishwordsapp.databinding.FragmentSentenceBuildBinding
import com.example.main.Sentences
import com.google.android.material.chip.Chip

class SentenceBuildFragment : Fragment() {
    private var binding: FragmentSentenceBuildBinding? = null
    private val answerList = mutableListOf<String>()
    private val suggestedList = mutableListOf<String>()
    private var number = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentSentenceBuildBinding.inflate(layoutInflater)
        return binding?.root
    }

    private fun createSuggestedView(){
        binding?.suggestedChipGroup?.removeAllViews()
        suggestedList.forEach {item->

            addSuggestedViewItem(item)
        }
    }

    private fun createAnsweredView(){
        binding?.answerChipGroup?.removeAllViews()
        answerList.forEach {item->

            addEmptyViewItem(item)
        }
    }
    private fun onSuggestedItemClicked(item: String){

        answerList.add(item)
        removeSuggestedViewItem(item)
        addEmptyViewItem(item)
    }

    private fun onAnsweredItemClicked(item: String){

        answerList.remove(item)
        removeEmptyViewItem(item)
        addSuggestedViewItem(item)

    }

    private fun addSuggestedViewItem(item: String){
        val chip = Chip(requireContext())
        chip.text = item
        chip.tag = item
        chip.setOnClickListener {
            onSuggestedItemClicked(item)
        }
        binding?.suggestedChipGroup?.addView(chip)
    }

    private fun removeSuggestedViewItem(tag: String){
        val chip = binding?.suggestedChipGroup?.findViewWithTag<Chip>(tag)
        binding?.suggestedChipGroup?.removeView(chip)
    }

    private fun addEmptyViewItem(item: String){
        val chip = Chip(requireContext())
        chip.text = item
        chip.tag = item
        chip.setOnClickListener {
            onAnsweredItemClicked(item)

        }
        binding?.answerChipGroup?.addView(chip)
    }

    private fun removeEmptyViewItem(tag: String){
        val chip = binding?.answerChipGroup?.findViewWithTag<Chip>(tag)
        binding?.answerChipGroup?.removeView(chip)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        createSuggestedList()

        createSuggestedView()
        createAnsweredView()





        binding?.btConfirm?.setOnClickListener {
            createSuggestedList()
        }


    }
    private fun createSuggestedList() {

        val listOfSentences = Sentences.listOfStentences
        var sentence = listOfSentences[number]
        var sentenceInEng = sentence.inEng
        var sentenceInAze = sentence.inAze
        binding?.tvSentenceInAze?.text = sentenceInAze
        suggestedList.addAll(sentenceInEng)



        if (sentenceInEng == answerList){
            number++

            binding?.suggestedChipGroup?.removeAllViews()
            binding?.answerChipGroup?.removeAllViews()
            answerList.clear()
            suggestedList.forEach {item->

                addSuggestedViewItem(item)
            }
        }else {
            Toast.makeText(requireContext(), "Incorrect sentence", Toast.LENGTH_SHORT).show()
        }




    }


}