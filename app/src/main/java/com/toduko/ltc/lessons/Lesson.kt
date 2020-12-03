package com.toduko.ltc.lessons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentLessonBinding
import io.noties.markwon.Markwon

class Lesson : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLessonBinding.inflate(inflater, container, false)
        val lesson = arguments?.getSerializable("lesson") as HashMap<String, String>
        val lang = arguments?.getString("language").toString()
        val markwon = Markwon.create(binding.root.context)

        binding.lessonTitle.text = lesson["title"]
        markwon.setMarkdown(binding.lessonContent, lesson["content"].toString())

        binding.startTestButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_lesson_to_questionMultipleChoice,
                bundleOf("lesson" to lesson, "language" to lang)
            )
        }

        return binding.root
    }
}