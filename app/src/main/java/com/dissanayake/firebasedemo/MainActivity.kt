package com.dissanayake.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
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
//        FirebaseDatabase.getInstance().getReference().child("employee").child("customer").setValue("John Doe")
    }
}