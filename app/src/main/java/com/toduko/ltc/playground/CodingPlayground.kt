package com.toduko.ltc.playground

//import com.daimajia.androidanimations.library.Techniques
//import com.daimajia.androidanimations.library.YoYo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.toduko.ltc.databinding.FragmentCodingPlaygroundBinding
import com.toduko.ltc.playground.syntax.Language
import com.toduko.ltc.playground.syntax.SyntaxManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import javax.xml.bind.DatatypeConverter


class CodingPlayground : Fragment() {
    private lateinit var binding: FragmentCodingPlaygroundBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCodingPlaygroundBinding.inflate(inflater, container, false)

        var mCurrentLanguage = Language.Python
        var id = 0
        val lang = arguments?.getString("language")
        binding.languageText.text = lang

        if (lang == "Python") {
            id =  71
            mCurrentLanguage = Language.Python
        } else {
            id =  63
            mCurrentLanguage = Language.JavaScript
        }
        val mCodeView = binding.codeView
        var cont = binding.view.context
        SyntaxManager.applyTheme(cont, mCodeView, mCurrentLanguage)

        binding.outputButton.setOnClickListener {
            var inputText = binding.codeView.text.toString()
            binding.outputLayout.visibility = View.VISIBLE
            binding.outputButton.visibility = View.GONE
            binding.output.text = "Loading output... This may take a few seconds"
            GlobalScope.async { getLanguages(inputText, id) }
        }

        binding.closeOutput.setOnClickListener {
            binding.outputButton.visibility = View.VISIBLE
            binding.outputLayout.visibility = View.GONE
        }

        return binding.root
    }

    private suspend fun getLanguages(input: String, id: Int) {
        try {
            val token = GlobalScope.async { createSubmission(input, id) }.await()
            getSubmission(token)
        } catch (e: Exception) {
            e.printStackTrace()
            println("Submission not created !")
        }
    }

    private fun createSubmission(input: String, id: Int): String? {
        val encodedInput = DatatypeConverter.printBase64Binary(input.toByteArray())
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
            mediaType,
            """{"language_id": "$id","source_code": "$encodedInput","stdin":null,"redirect_stderr_to_stdout":true}"""
        )
        val request = Request.Builder()
            .url("https://judge0-ce.p.rapidapi.com/submissions?fields=*&base64_encoded=true")
            .post(body)
            .addHeader("x-rapidapi-key", "2fc4c2b03emshe1830761d55616ap1d1063jsn69a13bb39efe")
            .addHeader("x-rapidapi-host", "judge0-ce.p.rapidapi.com")
            .build()
        val response = client.newCall(request).execute()
        val resBody = response.peekBody(2048).string()
        println(resBody)
        val jsonObj = JSONObject(resBody)
        return jsonObj.getString("token")
    }

    private fun getSubmission(token: String?) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://judge0-ce.p.rapidapi.com/submissions/$token")
            .get()
            .addHeader("x-rapidapi-key", "2fc4c2b03emshe1830761d55616ap1d1063jsn69a13bb39efe")
            .addHeader("x-rapidapi-host", "judge0-ce.p.rapidapi.com")
            .build()

        val response = client.newCall(request).execute()
        val jsonObj = JSONObject(response.peekBody(2048).string())
        val status = JSONObject(jsonObj.getString("status"))
        if (status.getInt("id") != 3) {
            Thread.sleep(1000)
            getSubmission(token)
        } else {
            println(jsonObj)
            binding.output.text = jsonObj.getString("stdout")
        }
    }
}