package com.toduko.ltc.questions

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentQuestionFillTheBlankBinding

class QuestionFillTheBlank : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentQuestionFillTheBlankBinding.inflate(inflater, container, false)

        val auth = Firebase.auth
        val user = auth.currentUser
        if(user != null) {
            Glide.with(requireActivity())
                .load(user.photoUrl)
                .circleCrop()
                .into(binding.profilePicture)
        }

        val lesson =
            arguments?.getSerializable("lesson") as HashMap<String, HashMap<String, String>>
        val lang = arguments?.getString("language").toString()
        val diff = arguments?.getString("difficulty").toString()

        binding.question.text = lesson["questionFillTheBlank"]?.get("text").toString()
        val missingWord = lesson["questionFillTheBlank"]?.get("missingWord").toString()

        binding.lessonName.text = lesson["title"].toString()

        binding.checkAnswer.setOnClickListener {
            binding.doneButton.isEnabled = true
            binding.missingWord.isEnabled = false
            val answeredCorrectly = binding.missingWord.text.toString() == missingWord
            binding.checkAnswer.visibility = View.GONE
            binding.doneButton.visibility = View.VISIBLE
            binding.status.visibility = View.VISIBLE
            if (answeredCorrectly) {
                binding.missingWord.background = ContextCompat.getDrawable(binding.root.context, R.drawable.border_green)
                binding.status.setTextColor(Color.GREEN)
                binding.status.text = "Correct"
            } else {
                binding.missingWord.background = ContextCompat.getDrawable(binding.root.context, R.drawable.border_red)
                binding.status.setTextColor(Color.RED)
                binding.status.text = "Incorrect".plus(" (Correct answer: ").plus(missingWord).plus(")")
            }
        }

        binding.doneButton.setOnClickListener {
            //get db
            val db = FirebaseFirestore.getInstance()
            val lessonDataForDb = HashMap<String, HashMap<String, Boolean>>()

            val hashMap: HashMap<String, Boolean> = HashMap()
            val title = lesson["title"].toString()
            hashMap[title] = true
            lessonDataForDb[lang] = hashMap

            if (user != null) {
                db.collection("users")
                    .document(user.uid)
                    .set(lessonDataForDb, SetOptions.merge())
            }

            it.findNavController().navigate(
                R.id.action_questionFillTheBlank_to_lessonList,
                bundleOf("language" to lang, "difficulty" to diff)
            )

        }

        binding.backButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.backButton1.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_questionFillTheBlank_to_lessonList,
                bundleOf("language" to lang, "difficulty" to diff)
            )
        }

        return binding.root
    }
}