package com.example.selltech

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth

class MainActivity() : SnackbarActivity() {
//
    var emailText: EditText? = null
    var passwordText: EditText? = null


     val mAuth = FirebaseAuth.getInstance()

    private fun login() {

        val intent = Intent(this,NaviationDrawerActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()



        emailText = findViewById(R.id.edit_text_email)
        passwordText = findViewById(R.id.edit_text_password)
        val register: Button = findViewById(R.id.button2)
        val button1: Button = findViewById(R.id.button_login)


        register.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (mAuth.currentUser != null){
            login()
        finish()
        }

        button1.setOnClickListener {

            if (validateEntries()) {
                showProressDialogue()
                mAuth.signInWithEmailAndPassword(
                    emailText?.text.toString(),
                    passwordText?.text.toString()
                ).addOnCompleteListener(this)
                { task ->
                    hideProgressDialogue()
                    if (task.isSuccessful) {
                        login()
                        finish()
                    } else {
                        Toast.makeText(
                            baseContext, "Account Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // ...
                }
            }
        }


    }

    private fun validateEntries(): Boolean {

        return when {


            TextUtils.isEmpty(emailText?.text.toString().trim() { it <= ' ' }) -> {
                showSnackBar("Enter an email address", true)
                false

            }

            TextUtils.isEmpty(passwordText?.text.toString().trim() { it <= ' ' }) -> {
                showSnackBar("Enter password", true)
                false

            }


            else -> {
                // showSnackBar( "validating account" + "", false)
                true
            }


        }

    }
}