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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DatabaseRegistrar
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var fireBaseUserId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<Button>(R.id.button3).setOnClickListener {
            var userEmail = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
            var userPassword = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
            var username = findViewById<EditText>(R.id.editTextTextPersonName6).text.toString()
            auth = FirebaseAuth.getInstance()

            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(username)) {
                Toast.makeText(this, "Empty credentials", Toast.LENGTH_SHORT).show()
            } else if (userPassword.length < 6){
                Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(userEmail, userPassword, username)
            }
        }

    }

    private fun registerUser(userEmail: String, userPassword: String, username: String) {
        auth?.createUserWithEmailAndPassword(userEmail, userPassword)?.addOnCompleteListener {
            if(it.isSuccessful) {
                fireBaseUserId = auth.currentUser!!.uid
                refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(fireBaseUserId!!)

                val userHashMap = HashMap<String, Any>()
                userHashMap["uid"] = fireBaseUserId!!
                userHashMap["username"] = username

                refUsers.updateChildren(userHashMap)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful) {
                                Toast.makeText(this@RegisterActivity, "Register success", Toast.LENGTH_SHORT).show()
                                val navToMainActivity = Intent(this, MainActivity::class.java)
                                startActivity(navToMainActivity)
                                finish()
                            }
                        }
            } else {
                Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}