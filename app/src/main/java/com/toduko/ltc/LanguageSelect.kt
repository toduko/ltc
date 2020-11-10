package com.toduko.ltc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.toduko.ltc.databinding.FragmentLanguageSelectBinding

class LanguageSelect : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentLanguageSelectBinding.inflate(inflater, container, false)
        binding.javascriptButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_languageSelect_to_javaScriptDifficultySelect)
        }
        binding.pythonButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_languageSelect_to_pythonDifficultySelect)
        }
        return binding.root
    }

}