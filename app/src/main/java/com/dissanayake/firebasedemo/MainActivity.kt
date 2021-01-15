package com.dissanayake.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private lateinit var refUsers: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("Test Main 04 :", "OK")
        findViewById<Button>(R.id.button5).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this@MainActivity, "Login success", Toast.LENGTH_SHORT).show()
            val navToStartActivity = Intent(this@MainActivity, StartActivity::class.java)
            startActivity(navToStartActivity)
            finish()
        }

        findViewById<Button>(R.id.button6).setOnClickListener {
            val name = findViewById<EditText>(R.id.editTextTextPersonName5).text.toString()
            val age = findViewById<EditText>(R.id.editTextTextPersonName7).text.toString()
            refUsers = FirebaseDatabase.getInstance("https://androidapptest-8b7ac-default-rtdb.firebaseio.com/").reference.child("employee").push()

            val userHashMap = HashMap<String, Any>()
            userHashMap["Name"] = name
            userHashMap["Age"] = age

            refUsers.updateChildren(userHashMap)
                    .addOnCompleteListener { tasks ->
                if(tasks.isSuccessful) {
                    val refresh = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(refresh)
                    finish()
                    Toast.makeText(this@MainActivity, "Successfully updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "DataBase error", Toast.LENGTH_SHORT).show()
                }
            }

        }
        findViewById<Button>(R.id.button7).setOnClickListener {
            val navToAddItemActivity = Intent(this, AddItemActivity::class.java)
            startActivity(navToAddItemActivity)
            finish()
        }
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var city: HashMap<String, String> = HashMap<String, String>()
        city.put("name", "kanthale")
        city.put("state", "497 main street")
        city.put("Country", "Sri Lanka")

        val addOnCompleteListener =
            db.collection("cities").document("JSR").set(city).addOnCompleteListener {

                if (it.isSuccessful) {
                    ToastMessage("Values added successfully")
                }
            }

    }
    private fun ToastMessage(value: String) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }
}








