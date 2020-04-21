package com.sarahelhelwa.apps.chacha.presentation.registration

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

import com.sarahelhelwa.apps.chacha.presentation.login.LoginActivity
import com.sarahelhelwa.apps.kotlinmessenger.R
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var selectedPhotoURI: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_registration)
        setUpButtons()
    }

    private fun setUpButtons() {
        select_photo_button_register.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(intent, 0)
        }
        register_btn.setOnClickListener {
            performRegistration(it)
        }
        already_have_an_account_text_view.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            selectedPhotoURI = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoURI)
            val bitmapDrawable = BitmapDrawable(bitmap)
            select_photo_button_register.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun performRegistration(view:View){
        val username = username_edit_text.text.toString()
        val email = email_edit_text.text.toString()
        val password = password_edit_text.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                baseContext, "Please enter email and password",
                Toast.LENGTH_LONG
            ).show()
            return@performRegistration
        }
        ContextExtensions.hideSoftKeyBoard(this@RegistrationActivity, view)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { it ->
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("Firebase:", "User created successfully  ${it.result?.user?.uid}")
            }
            .addOnFailureListener {
                Log.d("Firebase:", "Failed to create user  ${it.message}")
            }
    }

}
