package com.toduko.ltc.menus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.toduko.ltc.lessons.LessonAdapter
import com.toduko.ltc.databinding.FragmentLessonListBinding
import com.toduko.ltc.lessons.LessonData

class LessonList : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLessonListBinding.inflate(inflater, container, false)
        val lang = arguments?.getString("language").toString()
        val diff = arguments?.getString("difficulty").toString()

        val lessons = LessonData.getLessonTitles(binding.root.context, lang, diff)

        binding.lessonListTitle.text = lang.plus(" ").plus(diff)
        binding.lessonList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.lessonList.adapter = LessonAdapter(lessons, lang, diff)

        return binding.root
    }



}