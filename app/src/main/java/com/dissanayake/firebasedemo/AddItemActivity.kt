package com.dissanayake.firebasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dissanayake.firebasedemo.fragment.ListItemFragment

class AddItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val listItemFragment = ListItemFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, listItemFragment)
            addToBackStack(null)
            commit()
        }
    }
}