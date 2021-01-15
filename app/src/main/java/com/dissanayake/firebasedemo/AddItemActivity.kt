package com.dissanayake.firebasedemo

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.dissanayake.firebasedemo.R
import com.google.firebase.database.*

class AddItemActivity : AppCompatActivity() {

    private lateinit var refUsers: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        findViewById<Button>(R.id.button10).setOnClickListener {
            var inputData: String? = findViewById<EditText>(R.id.editTextNameAdd).text.toString().trim()
            if(TextUtils.isEmpty(inputData)) {
                Toast.makeText(this, "No value entered", Toast.LENGTH_SHORT).show()
            } else {
                addData(inputData!!)
            }

        }

       var listItemActivity = findViewById<ListView>(R.id.list_items_show)  // here

        val listItem = ArrayList<String>()
        val ListAdapter: ArrayAdapter<String> = ArrayAdapter(this@AddItemActivity, android.R.layout.simple_list_item_1, listItem)
        listItemActivity?.adapter = ListAdapter

        this.refUsers = FirebaseDatabase.getInstance("https://androidapptest-8b7ac-default-rtdb.firebaseio.com/").reference.child("products")
        this.refUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){

                listItem.clear()
                for (snapshot:DataSnapshot in dataSnapshot.children) {
                    listItem.add(snapshot.value.toString())
                }
                ListAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {

                Log.i("Error :", "Failed to read value.", error.toException())
            }
        })
//        refUsers.addValueEventListener(getData)
//        refUsers.addListenerForSingleValueEvent(getData)

    }
    private fun addData(input: String) {
        FirebaseDatabase.getInstance("https://androidapptest-8b7ac-default-rtdb.firebaseio.com/")
                .reference.child("products")
                .push()
                .setValue(input)
        findViewById<EditText>(R.id.editTextNameAdd).text = null
        ToastMessage("added $input")
    }
    private fun ToastMessage(show: String) {
        Toast.makeText(this, show, Toast.LENGTH_SHORT).show()
    }
}








