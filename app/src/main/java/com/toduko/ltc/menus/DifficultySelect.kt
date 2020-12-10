package com.toduko.ltc.menus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentDifficultySelectBinding

class DifficultySelect : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDifficultySelectBinding.inflate(inflater, container, false)
        val lang = arguments?.getString("language")
        binding.beginnerButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_difficultySelect_to_lessonList,
                bundleOf("language" to lang, "difficulty" to "Beginner")
            )
        }
        binding.advancedButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_difficultySelect_to_lessonList,
                bundleOf("language" to lang, "difficulty" to "Advanced")
            )
        }
        binding.playgroundButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_difficultySelect_to_codingPlayground,
                bundleOf("language" to lang)
            )
        }
        return binding.root
    }
}
