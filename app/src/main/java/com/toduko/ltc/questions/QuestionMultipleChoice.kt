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
import com.google.firebase.firestore.FirebaseFirestore
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentQuestionMultipleChoiceBinding

class QuestionMultipleChoice : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQuestionMultipleChoiceBinding.inflate(inflater, container, false)

        val lang = arguments?.getString("language").toString()
        val diff = arguments?.getString("difficulty").toString()
        val lessonNumber = arguments?.getString("lessonNumber")?.toInt()

        val db = FirebaseFirestore.getInstance()
        db.collection("lessons").document(lang).get().addOnSuccessListener { documentSnapshot ->
            val lessons = documentSnapshot.data?.get(diff) as List<HashMap<String, HashMap<String, String>>>
            val lesson = lessons[lessonNumber!! - 1]

            binding.questionTitle.text = lesson["questionMultipleChoice"]?.get("title").toString()
            binding.choice1.text = lesson["questionMultipleChoice"]?.get("answer1").toString()
            binding.choice2.text = lesson["questionMultipleChoice"]?.get("answer2").toString()
            binding.choice3.text = lesson["questionMultipleChoice"]?.get("answer3").toString()
            val questionCorrectAnswer = lesson["questionMultipleChoice"]?.get("correctAnswer").toString()

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
                val answeredCorrectly = checkedAnswer.text.toString() == questionCorrectAnswer
                if (!answeredCorrectly)
                    checkedAnswer.background = redBorder
                }
            when (questionCorrectAnswer) {
                binding.choice1.text -> binding.choice1.background = greenBorder
                binding.choice2.text -> binding.choice2.background = greenBorder
                binding.choice3.text -> binding.choice3.background = greenBorder
            }

                binding.checkAnswerButton.visibility = View.GONE
                binding.nextButton.visibility = View.VISIBLE
            }

            binding.nextButton.setOnClickListener {
                it.findNavController().navigate(
                    R.id.action_questionMultipleChoice_to_questionFillTheBlank,
                    bundleOf("language" to lang, "difficulty" to diff, "lessonNumber" to lessonNumber.toString())
                )
            }

            binding.questions.visibility = View.VISIBLE
            binding.questionTitle.visibility = View.VISIBLE
            binding.progressBar3.visibility = View.GONE
        }

        return binding.root
    }
}