package com.example.englishwordsapp.ui.main.tabs.Learn

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.LevelSetDialogFragmentBinding
import com.example.englishwordsapp.ui.main.tabs.Learn.Interactive_Quiz_Section.QuizFragment


class LevelSetDialogFragment : DialogFragment() {
    private var binding: LevelSetDialogFragmentBinding? = null
    var onLevelSelectedListener: OnLevelSelectedListener? = null
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LevelSetDialogFragmentBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.level_dialog_height)
        dialog?.window?.setLayout(width, height)

        binding?.btStart?.setOnClickListener {

            var selectedLevel: String? = null
                when(binding?.radioGroupLevels?.checkedRadioButtonId){
                    binding?.beginnerLevel?.id -> selectedLevel = "beginner_level"
                    binding?.elementaryLevel?.id -> selectedLevel = "elementary_level"
                    binding?.intermediateLevel?.id -> selectedLevel = "intermediate_level"
                    binding?.advanceLevel?.id -> selectedLevel = "advance_level"
                }

            if (selectedLevel != null) {
                onLevelSelectedListener?.onLevelSelected(selectedLevel)
            }
            dismiss()
        }

    }

    interface OnLevelSelectedListener {
        fun onLevelSelected(level: String)
    }


}