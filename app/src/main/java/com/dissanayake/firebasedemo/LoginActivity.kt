package com.dissanayake.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.button4).setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextTextPersonName3).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPersonName4).text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this@LoginActivity, "Empty credentials", Toast.LENGTH_SHORT).show()
                Log.i("Test 01 :", "OK")
            } else {
                userLogin(email, password)
                Log.i("Test 02 :", "OK")
            }
        }
    }

    private fun userLogin(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Log.i("Test 03 :", "OK")
                    Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                    val navToMainActivity = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(navToMainActivity)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Login error", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.i("Error :", e.toString())
        }
    }
}