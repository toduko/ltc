package com.toduko.ltc.playground

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.toduko.ltc.databinding.ActivityMainBinding.bind
import com.toduko.ltc.databinding.FragmentCodingPlaygroundBinding
import com.toduko.ltc.databinding.FragmentCodingPlaygroundBinding.bind
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import javax.xml.bind.DatatypeConverter


class CodingPlayground : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val binding = FragmentCodingPlaygroundBinding.inflate(inflater, container, false)
        var id=0
        val lang = arguments?.getString("language")
        binding.languageText.text=lang

        id = if(lang=="Python") {
            71
        } else {
            63
        }
        binding.outputButton.setOnClickListener {
            var inputText=binding.input.text.toString()
            GlobalScope.async { getLanguages(inputText,id)}
            }

        return binding.root
    }
    private suspend fun getLanguages(input:String, id:Int)
    {
        println(input)
        try
        {
            val token = GlobalScope.async { createSubmission(input,id) }.await()
            getSubmission(token)
        }
        catch (e: Exception) {
            e.printStackTrace()
            println("Submission not created !")
        }
    }
    private fun createSubmission(input:String, id:Int):String?
    {
        val encodedInput = DatatypeConverter.printBase64Binary(input.toByteArray())
        println(encodedInput)
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(mediaType,"""{"language_id": "$id","source_code": "$encodedInput","stdin":null,"redirect_stderr_to_stdout":true}""" )
        val request = Request.Builder()
            .url("https://judge0.p.rapidapi.com/submissions/?base64_encoded=true")
            .post(body)
            .addHeader("x-rapidapi-key", "2fc4c2b03emshe1830761d55616ap1d1063jsn69a13bb39efe")
            .addHeader("x-rapidapi-host", "judge0.p.rapidapi.com")
            .build()
        val response = client.newCall(request).execute()
        val resBody =response.peekBody(2048).string()
        println(resBody)
        val jsonObj=JSONObject(resBody)
        return jsonObj.getString("token")
    }
    private fun getSubmission(token:String?)
    {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://judge0.p.rapidapi.com/submissions/$token")
            .get()
            .addHeader("x-rapidapi-key", "2fc4c2b03emshe1830761d55616ap1d1063jsn69a13bb39efe")
            .addHeader("x-rapidapi-host", "judge0.p.rapidapi.com")
            .build()

        val response = client.newCall(request).execute()
        val jsonObj=JSONObject(response.peekBody(2048).string())
        val status=JSONObject(jsonObj.getString("status"))
        if(status.getInt("id")==1)
        {
            Thread.sleep(1000)
            getSubmission(token)
        }
        else
        {
            println(jsonObj)
        }
    }

}