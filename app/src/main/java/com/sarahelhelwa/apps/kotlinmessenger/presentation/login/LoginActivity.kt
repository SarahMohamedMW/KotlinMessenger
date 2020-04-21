package com.sarahelhelwa.apps.chacha.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.sarahelhelwa.apps.kotlinmessenger.R

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        setUpButtons()
    }

    private fun setUpButtons(){
        login_btn.setOnClickListener {performLogin()}
        back_to_register_textview.setOnClickListener { finish() }
    }

    private fun performLogin(){
        val email = email_edit_text.text.toString()
        val password = password_edit_text.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Firebase SignIn", "signInWithEmail:success")
                    val user = auth.currentUser
//                        updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Firebase SignIn", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                }

            }
    }
}
