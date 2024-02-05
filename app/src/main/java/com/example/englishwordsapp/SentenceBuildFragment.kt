package com.example.englishwordsapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishwordsapp.databinding.FragmentSentenceBuildBinding
import com.google.android.material.chip.Chip

class SentenceBuildFragment : Fragment() {
    private var binding: FragmentSentenceBuildBinding? = null
    private val adapterForFilled = RcAdapterForMakingSentenceEMPTY()
    private val adapterForEmpty = RcAdapterForMakingSentenceEMPTY()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSentenceBuildBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        binding?.rcViewFilled?.adapter = adapterForFilled
//        listOfWords.shuffle()
//        adapterForFilled.updateData(listOfWords)
//
//        binding?.rcViewEmpty?.adapter = adapterForEmpty
//        adapterForEmpty.updateData(emptyListOfWords)
//
//        adapterForFilled.setListener(object : RcAdapterForMakingSentenceEMPTY.RcOnClickListener{
//            override fun onClick(position: Int) {
//                val word = listOfWords[position]
//                emptyListOfWords.add(word)
//                listOfWords.removeAt(position)
//                adapterForFilled.updateData(listOfWords)
//                adapterForEmpty.updateData(emptyListOfWords)
//
//            }
//
//
//        })
//
//        adapterForEmpty.setListener(object : RcAdapterForMakingSentenceEMPTY.RcOnClickListener{
//            override fun onClick(position: Int) {
//                val word = emptyListOfWords[position]
//                listOfWords.add(word)
//                emptyListOfWords.removeAt(position)
//                adapterForEmpty.updateData(emptyListOfWords)
//                adapterForFilled.updateData(listOfWords)
//
//            }
//
//
//        })

        for (i in listOfWords){
            if (listOfWords.size > 0){
                val chip = Chip(requireContext())
                chip.text = i
                binding?.cGroupViewFilled?.addView(chip)
            }else break
        }


        binding?.cGroupViewFilled?.children?.forEach { chip->

            if (chip is Chip){
                chip.setOnClickListener {

                    val newChip = Chip(requireContext())
                    newChip.text = chip.text

                    binding?.cGroupViewFilled?.removeView(chip)
                    binding?.cGroupViewEmpty?.addView(newChip)
                }
            }
        }

        binding?.cGroupViewEmpty?.children?.forEach { chip->

            if (chip is Chip){
                chip.setOnClickListener {

                    val newChip = Chip(requireContext())
                    newChip.text = chip.text

                    binding?.cGroupViewEmpty?.removeView(chip)
                    binding?.cGroupViewFilled?.addView(newChip)
                }
            }
        }


//        binding?.cGroupViewFilled?.let { cGroupViewFilled ->
//            var index = 0
//            do {
//                val chip = cGroupViewFilled.getChildAt(index)
//                if (chip is Chip) {
//                    chip.setOnClickListener {
//                        val newChip = Chip(binding?.cGroupViewEmpty?.context)
//                        newChip.text = chip.text
//                        newChip.id = chip.id
//                        binding?.cGroupViewFilled?.removeView(chip)
//                        binding?.cGroupViewEmpty?.addView(newChip)
//                    }
//                }
//                index++
//            } while (index > cGroupViewFilled.childCount)
//        }
//
//
//        binding?.cGroupViewEmpty?.let { cGroupViewEmpty ->
//            var index = 0
//            do {
//                val chip = cGroupViewEmpty.getChildAt(index)
//                if (chip is Chip) {
//                    chip.setOnClickListener {
//                        val newChip = Chip(binding?.cGroupViewFilled?.context)
//                        newChip.text = chip.text
//                        newChip.id = chip.id
//                        binding?.cGroupViewEmpty?.removeView(chip)
//                        binding?.cGroupViewFilled?.addView(newChip)
//                    }
//                }
//                index++
//            } while (index < cGroupViewEmpty.childCount)
//        }



//        for (i in 0 until binding?.cGroupViewFilled?.childCount!!){
//            val chip = binding?.cGroupViewFilled?.getChildAt(i) as Chip
//            chip?.setOnClickListener {
//
//                binding?.cGroupViewFilled?.removeView(chip)
//
//                val newChip = Chip(binding?.cGroupViewEmpty?.context)
//                newChip.text = chip.text
//
//                binding?.cGroupViewEmpty?.addView(newChip)
//            }
//        }
//
//        for (i in 0 until binding?.cGroupViewEmpty?.childCount!!){
//            val chip = binding?.cGroupViewEmpty?.getChildAt(i) as Chip
//            chip?.setOnClickListener {
//                binding?.cGroupViewEmpty?.removeView(chip)
//
//                val newChip = Chip(binding?.cGroupViewFilled?.context)
//                newChip?.text = chip.text
//
//                binding?.cGroupViewFilled?.addView(newChip)
//            }
//        }









        binding?.btConfirm?.setOnClickListener {

            val chip = Chip(binding?.cGroupViewEmpty?.context)
            chip.text = "New Chip1"
            chip.isCheckable = true

            binding?.cGroupViewEmpty?.addView(chip)



        }


    }

    private var listOfWords = mutableListOf(
        "I", "go", "to", "school", "every", "day","schoolsfsdfsfsfsfsf", "schosfsffsffs"
    )

    private var emptyListOfWords = mutableListOf<String>()

}