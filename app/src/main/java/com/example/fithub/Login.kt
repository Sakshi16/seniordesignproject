package com.example.fithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mEmail = findViewById<EditText>(R.id.email)
        val mPassword = findViewById<EditText>(R.id.password)
        val mLoginBtn = findViewById<Button>(R.id.loginBtn)
        val mRegisterBtn = findViewById<TextView>(R.id.createText)


        //Initialize Firebase Auth
        val fAuth = FirebaseAuth.getInstance();

        if(fAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            // start main activity
            startActivity(intent)
            finish()
        }
        // set on-click listener
        mLoginBtn.setOnClickListener {
            val email = mEmail.text.toString().trim()
            val password = mPassword.text.toString().trim()

            //error handling
            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is required.")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is required.")
                return@setOnClickListener
            }
            if(password.length<6){
                mPassword.setError("Password must contain at least 6 characters.")
            }

            //authenticate user
            fAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in: success
                        Toast.makeText(this,"User logged in.",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        // start main activity
                        startActivity(intent)

                        // update UI for current User
                        val user = fAuth!!.currentUser
                        //updateUI(user)
                    } else {
                        // Sign in: fail
                        Toast.makeText(this,"Error!" + task.exception!!.message, Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }

                    // ...
                }
        }

        mRegisterBtn.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            // start login activity
            startActivity(intent)
        }


    }
}
