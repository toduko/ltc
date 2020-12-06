package com.toduko.ltc.menus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.toduko.ltc.R
import com.toduko.ltc.databinding.FragmentLanguageSelectBinding

class LanguageSelect : Fragment() {
    private var RC_SIGN_IN = 9001
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: FragmentLanguageSelectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLanguageSelectBinding.inflate(inflater, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = Firebase.auth

        val user = auth.currentUser
        updateUI(user)

        binding.javascriptButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_languageSelect_to_difficultySelect,
                bundleOf("language" to "JavaScript")
            )
        }

        binding.pythonButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_languageSelect_to_difficultySelect,
                bundleOf("language" to "Python")
            )
        }

        binding.signinButton.setOnClickListener {
            signIn()
        }

        binding.signoutButton.setOnClickListener {
            signOut()
        }

        return binding.root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        Firebase.auth.signOut()
        updateUI(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("onActivityResult", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("onActivityResult", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("firebaseAuthWithGoogle", "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("firebaseAuthWithGoogle", "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            binding.welcomeMessage.text = "Welcome ".plus(user.displayName)
            binding.signinButton.visibility = View.GONE
            binding.trackProgress.visibility = View.GONE
            binding.signoutButton.visibility = View.VISIBLE
            Glide.with(requireActivity())
                .load(user.photoUrl)
                .circleCrop()
                .into(binding.profileImage)
            binding.profileLayout.visibility = View.VISIBLE
        } else {
            binding.trackProgress.visibility = View.VISIBLE
            binding.signinButton.visibility = View.VISIBLE
            binding.signoutButton.visibility = View.GONE
            binding.profileLayout.visibility = View.GONE
        }
    }
}