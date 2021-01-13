package com.dissanayake.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<Button>(R.id.button3).setOnClickListener {
            var userEmail = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
            var userPassword = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
            auth = FirebaseAuth.getInstance()

            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
                Toast.makeText(this, "Empty credentials", Toast.LENGTH_SHORT).show()
            } else if (userPassword.length < 6){
                Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(userEmail, userPassword, it)
            }
        }

    }

    private fun registerUser(userEmail: String, userPassword: String, view: View) {
        auth?.createUserWithEmailAndPassword(userEmail, userPassword)?.addOnCompleteListener {
            if(it.isSuccessful) {
                Snackbar.make(view, "Registration success", Snackbar.LENGTH_SHORT)
                    .show()
                val navToMainActivity = Intent(this, MainActivity::class.java)
                startActivity(navToMainActivity)
                finish()
            } else {
                Snackbar.make(view, "Registration failed", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }
}