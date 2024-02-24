package com.example.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.englishwordsapp.databinding.FragmentHardCoreMarathonBinding


class HardCoreMarathonFragment : Fragment() {
    private var binding: FragmentHardCoreMarathonBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentHardCoreMarathonBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


}