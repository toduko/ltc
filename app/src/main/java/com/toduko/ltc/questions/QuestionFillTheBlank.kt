package com.toduko.ltc.questions

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.toduko.ltc.databinding.FragmentQuestionFillTheBlankBinding

class QuestionFillTheBlank : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentQuestionFillTheBlankBinding.inflate(inflater, container, false)

        val lesson =
            arguments?.getSerializable("lesson") as HashMap<String, HashMap<String, String>>
        val lang = arguments?.getString("language").toString()

        binding.fillTheBlankText.text = lesson["questionFillTheBlank"]?.get("text").toString()
        val missingWord = lesson["questionFillTheBlank"]?.get("missingWord").toString()

        //get db
        val db = FirebaseFirestore.getInstance()
        //get user
        auth = Firebase.auth
        val user = auth.currentUser
        val lessonDataForDb: HashMap<String, HashMap<String, Boolean>> = HashMap()
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

            val hashMap:HashMap<String,Boolean> = HashMap()
            val title = lesson.get("title").toString()
            hashMap.put(title, true)
            lessonDataForDb.put(lang, hashMap)
            if (user != null) {
                db.collection("users")
                    .document(user.uid)
                    .set(lessonDataForDb, SetOptions.merge())
            }
            it.findNavController().popBackStack()

        }

        return binding.root
    }
}