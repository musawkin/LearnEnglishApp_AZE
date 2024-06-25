package com.example.englishwordsapp.ui.main.learn

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.englishwordsapp.R
import com.example.englishwordsapp.databinding.LevelSetDialogFragmentBinding


class LevelSetDialogFragment : DialogFragment() {
    private var binding: LevelSetDialogFragmentBinding? = null
    var onLevelSelectedListener: OnLevelSelectedListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDialogTheme)
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

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.level_dialog_height)
        dialog?.window?.setLayout(width, height)

        binding?.btStart?.setOnClickListener {

            var selectedLevel: String? = null
            when (binding?.radioGroupLevels?.checkedRadioButtonId) {
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