package com.toduko.ltc.questions

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentQuestionMultipleChoiceBinding
import com.toduko.ltc.lessons.LessonData

class QuestionMultipleChoice : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQuestionMultipleChoiceBinding.inflate(inflater, container, false)

        val lang = arguments?.getString("language").toString()
        val diff = arguments?.getString("difficulty").toString()
        val lessonNumber = arguments?.getString("lessonNumber").toString()

        val question = LessonData.getLessonMultipleChoiceQuestion(
            binding.root.context,
            lang,
            diff,
            lessonNumber
        )
        binding.choice1.text = question.answer1
        binding.choice2.text = question.answer2
        binding.choice3.text = question.answer3
        binding.questionTitle.text = question.title

        binding.checkAnswerButton.setOnClickListener {
            binding.choice1.isEnabled = false
            binding.choice2.isEnabled = false
            binding.choice3.isEnabled = false

            val borderRadius = 12
            val borderWidth = 4

            val redBorder = GradientDrawable()
            redBorder.cornerRadius = borderRadius.toFloat()
            redBorder.setStroke(borderWidth, Color.RED)

            val greenBorder = GradientDrawable()
            greenBorder.cornerRadius = borderRadius.toFloat()
            greenBorder.setStroke(borderWidth, Color.GREEN)

            val checkedAnswer =
                binding.root.findViewById<RadioButton>(binding.questions.checkedRadioButtonId)
            if (binding.questions.checkedRadioButtonId != -1) {
                val answeredCorrectly = checkedAnswer.text.toString() == question.correctAnswer
                if (!answeredCorrectly)
                    checkedAnswer.background = redBorder
            }
            when (question.correctAnswer) {
                question.answer1 -> binding.choice1.background = greenBorder
                question.answer2 -> binding.choice2.background = greenBorder
                question.answer3 -> binding.choice3.background = greenBorder
            }

            binding.checkAnswerButton.visibility = View.GONE
            binding.nextButton.visibility = View.VISIBLE
        }

        binding.nextButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_questionMultipleChoice_to_questionFillTheBlank,
                bundleOf("language" to lang, "difficulty" to diff, "lessonNumber" to lessonNumber)
            )
        }

        return binding.root
    }
}