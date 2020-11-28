package com.toduko.ltc.menus

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentLanguageSelectBinding

class LanguageSelect : Fragment() {

    companion object {
        private const val  RC_SIGN_IN : Int = 1;
    }

    private lateinit var mAuth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentLanguageSelectBinding.inflate(inflater, container, false)
        binding.javascriptButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_languageSelect_to_difficultySelect, bundleOf("language" to "JavaScript"))
        }
        binding.pythonButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_languageSelect_to_difficultySelect, bundleOf("language" to "Python"))
        }

        // TODO: the code below should be navigated from a lesson
        binding.multipleChoiceButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_languageSelect_to_questionMultipleChoice)
        }
        binding.fillTheBlankButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_languageSelect_to_questionFillTheBlank)
        }

        binding.loginWithGoogle.setOnClickListener {
            // TODO: sign in with Google using Firebase
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
            mAuth = FirebaseAuth.getInstance()

            signIn()

        }
        return binding.root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("LanguageSelect", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("LanguageSelect", "Google sign in failed", e)
                }
            } else {
                Log.w("LanguageSelect", exception.toString())
            }

        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LanguageSelect", "signInWithCredential:success")
                    val user = mAuth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LanguageSelect", "signInWithCredential:failure", task.exception)
                }
            }
    }
}