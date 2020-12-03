package com.toduko.ltc.lessons

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.toduko.ltc.R

class LessonAdapter(
    private val lessons: List<HashMap<String, String>>,
    private val language: String
) : RecyclerView.Adapter<LessonAdapter.ViewHolder>() {
    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private var auth: FirebaseAuth = Firebase.auth
        val lessonNumber: TextView = view.findViewById(R.id.lesson_number)
        val lessonTitle: TextView = view.findViewById(R.id.lesson_title)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val db = FirebaseFirestore.getInstance()

        val user = auth.currentUser
        lateinit var lesson: HashMap<String, String>
        lateinit var lang: String

        init {
            view.setOnClickListener {
                it.findNavController().navigate(
                    R.id.action_lessonList_to_lesson,
                    bundleOf(
                        "lesson" to lesson,
                        "language" to lang
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.lesson_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lessonNumber.text = (position + 1).toString()
        holder.lessonTitle.text = lessons[position]["title"].toString()
        holder.lesson = lessons[position]
        holder.lang = language
        holder.user?.let {
            var lessonDataForDb: HashMap<String,  Boolean> = HashMap()
            holder.db.collection("users")
                .document(it.uid)
                .get().addOnSuccessListener { documentSnapshot ->
                    lessonDataForDb = documentSnapshot.data?.get(language) as HashMap<String,  Boolean>
                    println()
                    if(lessonDataForDb.get(holder.lesson.get("title").toString()) == true) {
                        holder.checkBox.setChecked(true);
                    }
                }
        }
    }

    override fun getItemCount() = lessons.size
}