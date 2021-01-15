package com.dissanayake.firebasedemo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView

import com.google.firebase.database.*
import java.lang.NullPointerException

class AddItemActivity : AppCompatActivity() {

    private lateinit var refUsers: DatabaseReference
    private lateinit var listItemActivity: ListView // here
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            listItemActivity = findViewById<ListView>(R.id.list_items)  // here
        } catch (e: NullPointerException) {
            Log.i("Error", e.toString())
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)


        val listItem = ArrayList<String>()
        val adapter:ArrayAdapter<String> = ArrayAdapter(this, R.layout.list_view, listItem)
        listItemActivity.adapter = adapter      // Error = listItemActivity must not be null

        refUsers = FirebaseDatabase.getInstance("https://androidapptest-8b7ac-default-rtdb.firebaseio.com/").reference.child("products")
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                listItem.clear()
                for (snapshot:DataSnapshot in dataSnapshot.children) {
                    listItem.add(snapshot.value.toString())
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Error :", "Failed to read value.", error.toException())
            }
        })

    }
}








