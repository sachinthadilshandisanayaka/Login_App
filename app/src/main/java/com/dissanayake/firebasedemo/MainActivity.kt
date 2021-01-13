package com.dissanayake.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button5).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
            val navToStartActivity = Intent(this, StartActivity::class.java)
            startActivity(navToStartActivity)
            finish()
        }
    }
}