package com.example.transportationproblemsolver

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class AddValuesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_values)

        val valuesView = findViewById<RecyclerView>(R.id.valuesView)
        val btnnext = findViewById<Button>(R.id.btnnext)

        val sharedPreferences = getSharedPreferences("data_cash", Context.MODE_PRIVATE)
        val suppCount = sharedPreferences.getInt("supp", 0)
        val consCount = sharedPreferences.getInt("cons", 0)
    }
}