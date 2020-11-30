package com.toduko.ltc.lessons

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentLessonBinding

class Lesson : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLessonBinding.inflate(inflater, container, false)
        val lang = arguments?.getString("language").toString()
        val diff = arguments?.getString("difficulty").toString()
        val lessonNumber = arguments?.getString("lessonNumber")?.toInt()
        var lessonTitle: String
        var lessonContent: String

        val db = FirebaseFirestore.getInstance()
        db.collection("lessons").document(lang).get().addOnSuccessListener { documentSnapshot ->
            val lessons = documentSnapshot.data?.get(diff) as List<HashMap<String, HashMap<String, String>>>
            lessonTitle = lessons[lessonNumber!! - 1]["title"].toString()
            lessonContent = lessons[lessonNumber!! - 1]["content"].toString()

            binding.progressBar2.visibility = View.GONE
            binding.lessonTitle.visibility = View.VISIBLE
            binding.lessonContent.visibility = View.VISIBLE
            binding.startTestButton.visibility = View.VISIBLE
            binding.lessonTitle.text = lessonTitle
            binding.lessonContent.text = lessonContent
        }

        binding.startTestButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_lesson_to_questionMultipleChoice,
                bundleOf("language" to lang, "difficulty" to diff, "lessonNumber" to lessonNumber.toString())
            )
        }

        return binding.root
    }
}