package com.toduko.ltc.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toduko.ltc.databinding.FragmentQuestionFillTheBlankBinding

class QuestionFillTheBlank : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQuestionFillTheBlankBinding.inflate(inflater, container, false)

        // TODO: get questions depending on the lessons
        val question = QuestionFillTheBlankData("What do you use to output to the console in JavaScript?", "console.log()")
        binding.fillTheBlankText.text = question.text

        binding.checkAnswer.setOnClickListener {
            val answeredCorrectly = binding.missingWord.text.toString() == question.missingWord
            binding.checkAnswer.visibility = View.GONE
            binding.nextButton.visibility = View.VISIBLE
            if (answeredCorrectly) {
                // TODO: set the background of the input to green
            }
            else {
                // TODO: set the background of the input to red and add the correct answer to the input text for example: wrong answer(correct answer)
            }
        }

        binding.nextButton.setOnClickListener {
            // TODO: navigate to next question or lesson
        }

        return binding.root
    }
}