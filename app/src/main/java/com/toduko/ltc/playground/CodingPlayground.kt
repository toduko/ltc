package com.toduko.ltc.playground

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentCodingPlaygroundBinding

class CodingPlayground : Fragment() {

    private lateinit var binding: FragmentCodingPlaygroundBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodingPlaygroundBinding.inflate(inflater, container, false)
        return binding.root
        binding.test
    }

}