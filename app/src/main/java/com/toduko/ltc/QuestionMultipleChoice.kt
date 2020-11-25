package com.toduko.ltc

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
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

            val checkButton =
                binding.root.findViewById<RadioButton>(binding.questions.checkedRadioButtonId)
            Log.i("test", (checkButton.text.toString() == question.correctAnswer).toString())
        }
        return binding.root
    }
}