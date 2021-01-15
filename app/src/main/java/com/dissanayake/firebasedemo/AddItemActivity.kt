package com.dissanayake.firebasedemo

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.dissanayake.firebasedemo.R
import com.google.firebase.database.*

class AddItemActivity : AppCompatActivity() {

    private lateinit var refUsers: DatabaseReference
   // here
    override fun onCreate(savedInstanceState: Bundle?) {

//        var listItemActivity = openDialog.findViewById<ListView>(R.id.list_items)  // here
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

       var listItemActivity = findViewById<ListView>(R.id.list_items_show)  // here

        val listItem = ArrayList<String>()
        val ListAdapter: ArrayAdapter<String> = ArrayAdapter(this@AddItemActivity, android.R.layout.simple_list_item_1, listItem)
        listItemActivity?.adapter = ListAdapter      // Error = listItemActivity must not be null

        this.refUsers = FirebaseDatabase.getInstance("https://androidapptest-8b7ac-default-rtdb.firebaseio.com/").reference.child("products")
        this.refUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                Log.i("Test 01 :", "Done")
                listItem.clear()
                for (snapshot:DataSnapshot in dataSnapshot.children) {
                    listItem.add(snapshot.value.toString())
                    Log.i("Data Retrieve :",listItem.toString())
                }
                ListAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.i("Error :", "Failed to read value.", error.toException())
            }
        })
//        refUsers.addValueEventListener(getData)
//        refUsers.addListenerForSingleValueEvent(getData)

    }
}








