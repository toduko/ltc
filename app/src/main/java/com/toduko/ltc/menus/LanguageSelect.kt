package com.toduko.ltc.menus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentLanguageSelectBinding

class LanguageSelect : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentLanguageSelectBinding.inflate(inflater, container, false)
        binding.javascriptButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_languageSelect_to_difficultySelect, bundleOf("language" to "JavaScript"))
        }
        binding.pythonButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_languageSelect_to_difficultySelect, bundleOf("language" to "Python"))
        }

        // TODO: the code below should be navigated from a lesson
        binding.multipleChoiceButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_languageSelect_to_questionMultipleChoice)
        }
        binding.fillTheBlankButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_languageSelect_to_questionFillTheBlank)
        }

        binding.loginWithGoogle.setOnClickListener {
            // TODO: sign in with Google using Firebase
        }

        return binding.root
    }

}