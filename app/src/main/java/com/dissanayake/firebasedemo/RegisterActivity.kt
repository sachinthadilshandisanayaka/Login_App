package com.dissanayake.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
            Log.i("Message 04 :", "OK")
            var userEmail = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
            var userPassword = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
            var username = findViewById<EditText>(R.id.editTextTextPersonName6).text.toString()

            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(username)) {
                Log.i("Message 05 :", "OK")
                Toast.makeText(this, "Empty credentials", Toast.LENGTH_SHORT).show()
            } else if (userPassword.length < 6){
                Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show()
            } else {
                Log.i("Message 06 :", "OK")
                registerUser(userEmail, userPassword, username)
            }
        }

    }

    private fun registerUser(userEmail: String, userPassword: String, username: String) {
        try {
            auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Log.i("Message 01 :", "OK")
                    fireBaseUserId = auth.currentUser?.uid
                    refUsers = FirebaseDatabase.getInstance("https://androidapptest-8b7ac-default-rtdb.firebaseio.com/").reference.child("Users").child(fireBaseUserId!!)

                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = fireBaseUserId!!
                    userHashMap["username"] = username

                    refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener { tasks ->
                                Log.i("Message 02 :", "OK")
                                if(tasks.isSuccessful) {
                                    Log.i("Message 03 :", "OK")
                                    Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show()
                                    val navToMainActivity = Intent(this, MainActivity::class.java)
                                    startActivity(navToMainActivity)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Registration success but data input error", Toast.LENGTH_SHORT).show()
                                }
                            }
                } else {
                    Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e:Exception) {
            Log.i("Error :", e.toString())
        }
    }
}