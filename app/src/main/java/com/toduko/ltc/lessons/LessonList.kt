package com.toduko.ltc.lessons

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.toduko.ltc.databinding.FragmentLessonListBinding

class LessonList : Fragment() {
    private lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLessonListBinding.inflate(inflater, container, false)
        val lang = arguments?.getString("language").toString()
        val diff = arguments?.getString("difficulty").toString()
        val db = FirebaseFirestore.getInstance()
        var lessonDataForDb: HashMap<String,  Boolean> = HashMap()
        binding.lessonListTitle.text = lang.plus(" ").plus(diff)
        auth = Firebase.auth
        val user = auth.currentUser
        if (user != null) {
            println()
            db.collection("users")
                .document(user.uid)
                .get().addOnSuccessListener { documentSnapshot1 ->
                    lessonDataForDb = documentSnapshot1.data?.get(lang) as HashMap<String,  Boolean>
                }
        }
        db.collection("lessons").document(lang).get().addOnSuccessListener { documentSnapshot ->
            val lessons = documentSnapshot.data?.get(diff) as List<HashMap<String, String>>

            binding.progressBar.visibility = View.GONE
            binding.lessonList.layoutManager = LinearLayoutManager(binding.root.context)
            binding.lessonList.adapter = lessonDataForDb?.let { LessonAdapter(lessons, lang, it) }
            binding.lessonList.visibility = View.VISIBLE
        }

        return binding.root
    }



}