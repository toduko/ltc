package com.toduko.ltc.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentSigninBinding

class Signin : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }
}