package com.toduko.ltc.lessons

import android.content.Context
import android.util.Log
import com.toduko.ltc.R
import com.toduko.ltc.questions.QuestionFillTheBlankData
import com.toduko.ltc.questions.QuestionMultipleChoiceData

class LessonData {
    companion object {
        fun getLessonTitles(context: Context, lang: String, diff: String): List<String> {
            return when(lang) {
                "JavaScript" -> when(diff) {
                    "Beginner" -> context.resources.getStringArray(R.array.javascript_beginner_lesson_titles).toList()
                    "Advanced" -> context.resources.getStringArray(R.array.javascript_advanced_lesson_titles).toList()
                    else -> listOf("No lessons found")
                }
                "Python" -> when(diff) {
                    "Beginner" -> context.resources.getStringArray(R.array.python_beginner_lesson_titles).toList()
                    "Advanced" -> context.resources.getStringArray(R.array.python_advanced_lesson_titles).toList()
                    else -> listOf("No lessons found")
                }
                else -> listOf("No lessons found")
            }
        }

        fun getLesson(context: Context, lang: String, diff: String, lessonNumber: String): String {
            val lessons = when(lang) {
                "JavaScript" -> when(diff) {
                    "Beginner" -> context.resources.getTextArray(R.array.javascript_beginner_lessons).toList()
                    "Advanced" -> context.resources.getTextArray(R.array.javascript_advanced_lessons).toList()
                    else -> listOf()
                }
                "Python" -> when(diff) {
                    "Beginner" -> context.resources.getTextArray(R.array.python_beginner_lessons).toList()
                    "Advanced" -> context.resources.getTextArray(R.array.python_advanced_lessons).toList()
                    else -> listOf()
                }
                else -> listOf()
            }
            if (lessons.size < lessonNumber.toInt()) return "Lesson not found"
            return lessons[lessonNumber.toInt() - 1].toString()
        }

        fun getLessonMultipleChoiceQuestion(context: Context, lang: String, diff: String, lessonNumber: String): QuestionMultipleChoiceData {
            // TODO - get the question from resource files
            return QuestionMultipleChoiceData("2+2?", "1", "2", "4", "4")
        }

        fun getLessonFillTheBlankQuestion(context: Context, lang: String, diff: String, lessonNumber: String): QuestionFillTheBlankData {
            // TODO - get the question from resource files
            return QuestionFillTheBlankData("What do you use to output to the console in JavaScript?", "console.log()")
        }
    }
}