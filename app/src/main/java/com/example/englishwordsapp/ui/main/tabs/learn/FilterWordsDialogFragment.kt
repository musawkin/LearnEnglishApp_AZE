package com.example.englishwordsapp.ui.main.tabs.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.englishwordsapp.databinding.DialogFilterWordsBinding

class FilterWordsDialogFragment: DialogFragment() {
    private var binding: DialogFilterWordsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFilterWordsBinding.inflate(layoutInflater)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.imageButtonClose?.setOnClickListener {
            dismiss()
        }

        binding?.btConfirm?.setOnClickListener {
            dismiss()
        }
    }
}