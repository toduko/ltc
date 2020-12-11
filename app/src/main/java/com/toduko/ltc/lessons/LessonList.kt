package com.toduko.ltc.lessons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.toduko.ltc.databinding.FragmentLessonListBinding

class LessonList : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLessonListBinding.inflate(inflater, container, false)
        val lang = arguments?.getString("language").toString()
        val diff = arguments?.getString("difficulty").toString()
        val db = FirebaseFirestore.getInstance()

        binding.lessonListTitle.text = lang.plus(" ").plus(diff)

        db.collection("lessons").document(lang).get().addOnSuccessListener { documentSnapshot ->
            val lessons = documentSnapshot.data?.get(diff) as List<HashMap<String, String>>
            var lessonDataForDb: HashMap<String, HashMap<String, Boolean>> = HashMap()

            val auth = Firebase.auth
            val user = auth.currentUser

            if (user != null) {
                db.collection("users")
                    .document(user.uid)
                    .get().addOnSuccessListener { documentSnapshot1 ->
                        if (documentSnapshot1.data != null)
                            lessonDataForDb =
                                documentSnapshot1.data as HashMap<String, HashMap<String,Boolean>>
                        binding.lessonList.adapter = LessonAdapter(lessons, lang, diff, lessonDataForDb)
                    }
            }

            binding.progressBar.visibility = View.GONE
            binding.lessonList.layoutManager = LinearLayoutManager(binding.root.context)
            binding.lessonList.adapter = LessonAdapter(lessons, lang, diff, lessonDataForDb)
            binding.lessonList.visibility = View.VISIBLE
        }

        return binding.root
    }


}