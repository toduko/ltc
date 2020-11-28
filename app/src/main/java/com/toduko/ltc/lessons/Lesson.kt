package com.toduko.ltc.lessons

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentLessonBinding

class Lesson : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLessonBinding.inflate(inflater, container, false)
        val lang = arguments?.getString("language").toString()
        val diff = arguments?.getString("difficulty").toString()
        val lessonNumber = arguments?.getString("lessonNumber").toString()
        val lessonTitle = arguments?.getString("lessonTitle").toString()

        binding.lessonTitle.text = lessonTitle
        binding.lessonContent.text = Html.fromHtml(LessonData.getLesson(binding.root.context, lang, diff, lessonNumber))

        binding.startTestButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_lesson_to_questionMultipleChoice,
                bundleOf("language" to lang, "difficulty" to diff, "lessonNumber" to lessonNumber)
            )
        }

        return binding.root
    }
}