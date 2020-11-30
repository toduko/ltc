package com.toduko.ltc.questions

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.toduko.ltc.databinding.FragmentQuestionFillTheBlankBinding

class QuestionFillTheBlank : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQuestionFillTheBlankBinding.inflate(inflater, container, false)

        val lang = arguments?.getString("language").toString()
        val diff = arguments?.getString("difficulty").toString()
        val lessonNumber = arguments?.getString("lessonNumber")?.toInt()

        val db = FirebaseFirestore.getInstance()
        db.collection("lessons").document(lang).get().addOnSuccessListener { documentSnapshot ->
            val lessons =
                documentSnapshot.data?.get(diff) as List<HashMap<String, HashMap<String, String>>>
            val lesson = lessons[lessonNumber!! - 1]

            binding.fillTheBlankText.text = lesson["questionFillTheBlank"]?.get("text").toString()
            val missingWord = lesson["questionFillTheBlank"]?.get("missingWord").toString()

            binding.checkAnswer.setOnClickListener {
                binding.missingWord.isEnabled = false
                val answeredCorrectly = binding.missingWord.text.toString() == missingWord
                binding.checkAnswer.visibility = View.GONE
                binding.doneButton.visibility = View.VISIBLE
                binding.status.visibility = View.VISIBLE
                if (answeredCorrectly) {
                    binding.missingWord.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
                    binding.status.text = "Correct"
                } else {
                    binding.missingWord.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    binding.status.text =
                        "Incorrect (Correct answer: ".plus(missingWord).plus(")")
                }
            }

            binding.doneButton.setOnClickListener {
                it.findNavController().popBackStack()
            }

            binding.fillTheBlank.visibility = View.VISIBLE
            binding.fillTheBlankText.visibility = View.VISIBLE
            binding.missingWord.visibility = View.VISIBLE
            binding.checkAnswer.visibility = View.VISIBLE
            binding.progressBar4.visibility = View.GONE
        }

        return binding.root
    }
}