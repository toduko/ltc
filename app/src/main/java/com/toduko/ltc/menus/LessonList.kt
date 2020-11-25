package com.toduko.ltc.menus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toduko.ltc.databinding.FragmentLessonListBinding


class LessonList : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentLessonListBinding.inflate(inflater, container, false)
        val lang = arguments?.getString("language")
        val diff = arguments?.getString("difficulty")
        binding.lessonListTitle.text = lang.plus(" ").plus(diff)
        return binding.root
    }


}