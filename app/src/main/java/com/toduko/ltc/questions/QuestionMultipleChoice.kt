package com.toduko.ltc.questions

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentQuestionMultipleChoiceBinding

class QuestionMultipleChoice : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQuestionMultipleChoiceBinding.inflate(inflater, container, false)

        val lesson =
            arguments?.getSerializable("lesson") as HashMap<String, HashMap<String, String>>
        val lang = arguments?.getString("language").toString()
        val diff = arguments?.getString("difficulty").toString()

        binding.lessonname.text = lesson["title"].toString()
        binding.questionTitle.text = lesson["questionMultipleChoice"]?.get("title").toString()
        binding.choice1.text = lesson["questionMultipleChoice"]?.get("answer1").toString()
        binding.choice2.text = lesson["questionMultipleChoice"]?.get("answer2").toString()
        binding.choice3.text = lesson["questionMultipleChoice"]?.get("answer3").toString()
        val questionCorrectAnswer =
            lesson["questionMultipleChoice"]?.get("correctAnswer").toString()

        binding.checkAnswerButton.setOnClickListener {
            binding.choice1.isEnabled = false
            binding.choice2.isEnabled = false
            binding.choice3.isEnabled = false

            val checkedAnswer =
                binding.root.findViewById<RadioButton>(binding.questions.checkedRadioButtonId)
            if (binding.questions.checkedRadioButtonId != -1) {
                val answeredCorrectly = checkedAnswer.text.toString() == questionCorrectAnswer
                if (!answeredCorrectly) {
                    checkedAnswer.setTextColor(Color.RED)
                    checkedAnswer.buttonTintList = ContextCompat.getColorStateList(binding.root.context, R.color.red)
                    checkedAnswer.background =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.border_red_slim)
                }
            }

            when (questionCorrectAnswer) {
                binding.choice1.text -> {
                    binding.choice1.setTextColor(Color.GREEN)
                    binding.choice1.buttonTintList = ContextCompat.getColorStateList(binding.root.context, R.color.green)
                    binding.choice1.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.border_green_slim
                    )
                }
                binding.choice2.text -> {
                    binding.choice2.setTextColor(Color.GREEN)
                    binding.choice2.buttonTintList = ContextCompat.getColorStateList(binding.root.context, R.color.green)
                    binding.choice2.background =
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.border_green_slim
                        )
                }
                binding.choice3.text -> {
                    binding.choice3.setTextColor(Color.GREEN)
                    binding.choice3.buttonTintList = ContextCompat.getColorStateList(binding.root.context, R.color.green)
                    binding.choice3.background =
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.border_green_slim
                        )
                }
            }

            binding.checkAnswerButton.visibility = View.GONE
            binding.nextButton.visibility = View.VISIBLE
        }

        binding.back.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.nextButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_questionMultipleChoice_to_questionFillTheBlank,
                bundleOf("lesson" to lesson, "language" to lang, "difficulty" to diff)
            )
        }

        binding.backButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_questionMultipleChoice_to_lesson,
                bundleOf("lesson" to lesson, "language" to lang, "difficulty" to diff)
            )
        }

        return binding.root
    }
}