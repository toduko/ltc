package com.toduko.ltc.lessons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.toduko.ltc.R

class LessonAdapter(
    private val lessons: List<HashMap<String, String>>,
) : RecyclerView.Adapter<LessonAdapter.ViewHolder>() {
    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val lessonNumber: TextView = view.findViewById(R.id.lesson_number)
        val lessonTitle: TextView = view.findViewById(R.id.lesson_title)
        lateinit var lesson: HashMap<String, String>

        init {
            view.setOnClickListener {
                it.findNavController().navigate(
                    R.id.action_lessonList_to_lesson,
                    bundleOf(
                        "lesson" to lesson
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
    }

    override fun getItemCount() = lessons.size
}