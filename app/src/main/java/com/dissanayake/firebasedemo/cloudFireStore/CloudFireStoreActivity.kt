package com.dissanayake.firebasedemo.cloudFireStore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dissanayake.firebasedemo.R
import com.google.firebase.firestore.*

class CloudFireStoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud_fire_strore)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        // add data
        val city: HashMap<String, Any> = HashMap()
        city["name"] = "kanthale"
        city["state"] = "497 main street"
        city["Country"] = "Sri Lanka"

        addFireStore(db, city)

        // merge data
        val data: HashMap<String, Any> = HashMap()
        data["Telephone"] = "0112321232"

        mergeFireStore(db, data)

        // add with uniq id
        val data2: HashMap<String, Any> = HashMap()
        data2["name"] = "Lahiru"
        data2["city"] = "Kandy"

        addWithUniId(db, data2)

        // update
        updateFireStore(db, "Telephone", "0711427041")

        findViewById<Button>(R.id.button8).setOnClickListener {
            var documentReference: DocumentReference = FirebaseFirestore.getInstance().collection("cities").document("JSR")
            documentReference.get().addOnCompleteListener {
                if(it.isSuccessful) {
                    val doc: DocumentSnapshot? = it.result
                    if (doc!!.exists()) {
                        Log.i("Data return", doc.data.toString())
                    } else {
                        Log.i("Data return", "No Data")
                    }
                }
            }
        }
        findViewById<Button>(R.id.button11).setOnClickListener {
            FirebaseFirestore.getInstance().collection("cities").whereEqualTo("city", "kandy")
                .get().addOnCompleteListener {
                    if(it.isSuccessful) {
                        for(doc: QueryDocumentSnapshot in it.result!!) {
                            Log.i("Document", doc.id +" =>" +doc.data)
                        }
                    }
                }
        }

    }

    private fun addFireStore(db: FirebaseFirestore, city: HashMap<String, Any>) {

        db.collection("cities").document("JSR").set(city).addOnCompleteListener {
            if (it.isSuccessful) {
                ToastMessage("Values added successfully")
            }
        }
    }

    private fun mergeFireStore(db: FirebaseFirestore, data: HashMap<String, Any>) {
        db.collection("cities").document("JSR").set(data, SetOptions.merge()).addOnCompleteListener {
            if(it.isSuccessful) {
                ToastMessage("Value merge successfully")
            }
        }
    }

    private fun addWithUniId(db: FirebaseFirestore, data: HashMap<String, Any>) {
        db.collection("cities").add(data).addOnCompleteListener {
            if(it.isSuccessful) {
                ToastMessage("Value add with uniq ID")
            }
        }
    }

    private fun updateFireStore(db: FirebaseFirestore, field: String, value: String) {
        var df: DocumentReference = db.collection("cities").document("JSR")
        df.update(field, value)
    }
    private fun ToastMessage(value: String) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }
}