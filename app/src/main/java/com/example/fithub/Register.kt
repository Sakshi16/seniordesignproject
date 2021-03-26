package com.example.fithub

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class Register : AppCompatActivity() {

    private val fAuth: FirebaseAuth? = null

//    @Override
//    override fun onStart() {
//        super.onStart()
//        // ...
//        val currentUser = fAuth!!.currentUser
//        //updateUI(currentUser)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val mName = findViewById<EditText>(R.id.name)
        val mEmail = findViewById<EditText>(R.id.email)
        val mUsername = findViewById<EditText>(R.id.email)
        val mPassword = findViewById<EditText>(R.id.password)
        val mConfirmPassword = findViewById<EditText>(R.id.confirmPassword)
        val mRegisterBtn = findViewById<Button>(R.id.registerBtn)
        val mLoginBtn = findViewById<TextView>(R.id.createText)

        //Initialize Firebase Auth
        val fAuth = FirebaseAuth.getInstance();

        if(fAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            // start main activity
            startActivity(intent)
            finish()
        }

        // set on-click listener
        mRegisterBtn.setOnClickListener {
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

            //register user
            fAuth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {
                    // Sign in: success
                    Toast.makeText(this,"User created.",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    // start main activity
                    startActivity(intent)

                    // update UI for current User
                    val user = fAuth!!.currentUser
                    //updateUI(user)
                    } else {
                        // Sign in: fail
                        Toast.makeText(this,"Error!" + task.exception!!.message,Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }

        }

        mLoginBtn.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            // start login activity
            startActivity(intent)
        }

    }
}