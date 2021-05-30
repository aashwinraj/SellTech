package com.example.selltech

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.selltech.firestore.FirestoreClass
import com.example.selltech.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

open class RegisterActivity : SnackbarActivity() {


    private fun login() {

        val intent = Intent(this, NaviationDrawerActivity::class.java)
        startActivity(intent)
    }

    var nameText: EditText? = null
    var email_Text: EditText? = null
    var password_Text: EditText? = null
    var cnfPasswordText: EditText? = null
    var phone: EditText? = null
    var checkbox: CheckBox? = null
    val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()






        nameText = findViewById(R.id.edit_text_name)
        email_Text = findViewById(R.id.edit_text_email)
        password_Text = findViewById(R.id.edit_text_password)
        phone = findViewById(R.id.edit_text_phone)
        cnfPasswordText = findViewById(R.id.edit_text_Confirmpassword)
        checkbox = findViewById(R.id.checkbox)
        val registerButton: Button = findViewById(R.id.registration)


        registerButton.setOnClickListener {
            if (validateDetails()) {
                showProressDialogue()
                mAuth.createUserWithEmailAndPassword(
                    email_Text?.text.toString(), password_Text?.text.toString()
                ).addOnCompleteListener(this)
                { task ->

                    if (task.isSuccessful) {

                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val user =
                            Users(firebaseUser.uid, nameText?.text.toString() ,
                                email_Text?.text.toString() ,phone?.text.toString()
                                 )


                         FirestoreClass().registerUser(this,user)

                        login()
                    } else {
                        // If sign in fails, display a message to the user.
                        hideProgressDialogue()

                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_LONG)
                            .show()

                    }

                    // ...
                }
            }

        }


    }


    fun userRegistrationSuccess() {

        hideProgressDialogue()
        Toast.makeText(this, "registration successful", Toast.LENGTH_LONG).show()

    }

    //------------------------------------------------------------------------------------------
    private fun validateDetails(): Boolean {
        return when {


            TextUtils.isEmpty(nameText?.text.toString().trim() { it <= ' ' }) -> {
                showSnackBar("Enter name", true)
                false

            }

            TextUtils.isEmpty(email_Text?.text.toString().trim() { it <= ' ' }) -> {
                showSnackBar("Enter an email address", true)
                false

            }

            TextUtils.isEmpty(password_Text?.text.toString().trim() { it <= ' ' }) -> {
                showSnackBar("Enter password", true)
                false

            }

            TextUtils.isEmpty(cnfPasswordText?.text.toString().trim() { it <= ' ' }) -> {
                showSnackBar("Confirm password", true)
                false

            }
            password_Text?.text.toString().trim() != cnfPasswordText?.text.toString().trim() -> {
                showSnackBar("passwords don't match", true)
                false

            }

            !checkbox!!.isChecked -> {
                showSnackBar("agree to terms and conditions", true)
                false
            }
            else -> {
                //showSnackBar("all good", false)
                true
            }


        }
    }


}

