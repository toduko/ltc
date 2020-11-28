package com.toduko.ltc.questions

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentQuestionFillTheBlankBinding
import com.toduko.ltc.lessons.LessonData

class QuestionFillTheBlank : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQuestionFillTheBlankBinding.inflate(inflater, container, false)

        val lang = arguments?.getString("language").toString()
        val diff = arguments?.getString("difficulty").toString()
        val lessonNumber = arguments?.getString("lessonNumber").toString()

        val question = LessonData.getLessonFillTheBlankQuestion(binding.root.context, lang, diff, lessonNumber)
        binding.fillTheBlankText.text = question.text

        binding.checkAnswer.setOnClickListener {
            binding.missingWord.isEnabled = false
            val answeredCorrectly = binding.missingWord.text.toString() == question.missingWord
            binding.checkAnswer.visibility = View.GONE
            binding.doneButton.visibility = View.VISIBLE
            if (answeredCorrectly) {
                binding.missingWord.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
                binding.status.text = "Correct"
            }
            else {
                binding.missingWord.backgroundTintList = ColorStateList.valueOf(Color.RED)
                binding.status.text = "Incorrect (Correct answer: ".plus(question.missingWord).plus(")")
            }
        }

        binding.doneButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return binding.root
    }
}