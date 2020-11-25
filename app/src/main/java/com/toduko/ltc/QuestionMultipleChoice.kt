package com.toduko.ltc

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.toduko.ltc.databinding.FragmentQuestionMultipleChoiceBinding
import kotlinx.coroutines.selects.select


class QuestionMultipleChoice : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQuestionMultipleChoiceBinding.inflate(inflater, container, false)
        val question = QuestionMultipleChoiceData(
            "2+2", "1", "2", "4", "4"
        )
        binding.choice1.text = question.answer1
        binding.choice2.text = question.answer2
        binding.choice3.text = question.answer3
        binding.questionTitle.text = question.title
        binding.button3.setOnClickListener {
            var resultText: String
            val checkButton =
                binding.root.findViewById<RadioButton>(binding.questions.checkedRadioButtonId)
            if (binding.questions.checkedRadioButtonId != -1) {
                val answeredCorrectly = checkButton.text.toString() == question.correctAnswer
                if (answeredCorrectly) resultText = "Correct Answer"
                else resultText = "Wrong Answer"
            } else resultText = "You must first select an answer"
            Toast.makeText(binding.root.context, resultText, Toast.LENGTH_LONG).show()
        }
        return binding.root
    }
}