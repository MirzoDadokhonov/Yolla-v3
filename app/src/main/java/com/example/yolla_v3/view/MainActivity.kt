package com.example.yolla_v3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yolla_v3.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.container, MyContactsListFragment()).commit()
    }
}