package com.toduko.ltc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.toduko.ltc.databinding.FragmentJavascriptDifficultySelectBinding

class JavaScriptDifficultySelect : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentJavascriptDifficultySelectBinding.inflate(inflater, container, false)
        binding.beginner.setOnClickListener {
            it.findNavController().navigate(R.id.action_javascriptDifficultySelect_to_javascriptBeginnerList)
        }
        return binding.root
    }
}