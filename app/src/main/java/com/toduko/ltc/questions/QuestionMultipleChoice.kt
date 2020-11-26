package com.toduko.ltc.questions

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.toduko.ltc.databinding.FragmentQuestionMultipleChoiceBinding

class QuestionMultipleChoice : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQuestionMultipleChoiceBinding.inflate(inflater, container, false)

        // TODO: get questions depending on the lessons
        val question = QuestionMultipleChoiceData(
            "2+2", "1", "2", "3", "4", "4"
        )
        binding.choice1.text = question.answer1
        binding.choice2.text = question.answer2
        binding.choice3.text = question.answer3
        binding.choice4.text = question.answer4
        binding.questionTitle.text = question.title

        binding.checkAnswerButton.setOnClickListener {
            val checkButton =
                binding.root.findViewById<RadioButton>(binding.questions.checkedRadioButtonId)
            if (binding.questions.checkedRadioButtonId != -1) {
                val answeredCorrectly = checkButton.text.toString() == question.correctAnswer
                if (!answeredCorrectly) checkButton.setBackgroundColor(Color.RED)
            }
            when (question.correctAnswer) {
                question.answer1 -> binding.choice1.setBackgroundColor(Color.GREEN)
                question.answer2 -> binding.choice2.setBackgroundColor(Color.GREEN)
                question.answer3 -> binding.choice3.setBackgroundColor(Color.GREEN)
                question.answer4 -> binding.choice4.setBackgroundColor(Color.GREEN)
            }

            binding.checkAnswerButton.visibility = View.GONE
            binding.nextButton.visibility = View.VISIBLE
        }

        binding.nextButton.setOnClickListener {
            // TODO: navigate to next question or lesson
        }

        return binding.root
    }
}