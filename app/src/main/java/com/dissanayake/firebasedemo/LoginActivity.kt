package com.dissanayake.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.button4).setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextTextPersonName3).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPersonName4).text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Empty credentials", Toast.LENGTH_SHORT).show()
            } else {
                userLogin(email, password)
            }
        }
    }

    private fun userLogin(email: String, password: String) {
        auth?.signInWithEmailAndPassword(email, password)?.addOnSuccessListener {
            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
            val navToMainActivity = Intent(this, MainActivity::class.java)
            startActivity(navToMainActivity)
            finish()
        }
    }
}