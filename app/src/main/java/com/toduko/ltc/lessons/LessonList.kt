package com.toduko.ltc.lessons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.toduko.ltc.R
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

        binding.lessonListLanguage.text = lang
        binding.lessonListDifficulty.text = diff

        db.collection("lessons").document(lang).get().addOnSuccessListener { documentSnapshot ->
            val lessons = documentSnapshot.data?.get(diff) as List<HashMap<String, String>>
            var lessonDataForDb: HashMap<String, HashMap<String, Boolean>> = HashMap()

            val auth = Firebase.auth
            val user = auth.currentUser

            if (user != null) {
                Glide.with(requireActivity())
                    .load(user.photoUrl)
                    .circleCrop()
                    .into(binding.profilePicture)
                binding.profilePicture.visibility = View.VISIBLE
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
        binding.backButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_lessonList_to_difficultySelect,
                bundleOf("language" to lang)
            )
        }
        return binding.root
    }


}