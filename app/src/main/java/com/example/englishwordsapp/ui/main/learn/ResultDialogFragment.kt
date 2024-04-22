package com.example.englishwordsapp.ui.main.learn

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.DialogEndQuestBinding

class ResultDialogFragment: DialogFragment() {
    private var binding: DialogEndQuestBinding? = null
    private var countOfCorrectAnswer: String? = null
    private var countOfWrongAnswer: String? = null

    fun setScore(correct: String, wrong: String){
        countOfCorrectAnswer = correct
        countOfWrongAnswer = wrong
    }
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DialogEndQuestBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.dialog_height)
        dialog?.window?.setLayout(width, height)

        binding?.tvCorrectScore?.text = countOfCorrectAnswer
        binding?.tvWrongScore?.text = countOfWrongAnswer

        binding?.btClose?.setOnClickListener {
            findNavController().popBackStack()
            dismiss()
        }

    }
}