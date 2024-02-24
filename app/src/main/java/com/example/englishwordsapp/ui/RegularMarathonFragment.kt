package com.example.englishwordsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.englishwordsapp.databinding.FragmentRegularMarathonBinding


class RegularMarathonFragment : Fragment() {
    private var binding: FragmentRegularMarathonBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentRegularMarathonBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


}