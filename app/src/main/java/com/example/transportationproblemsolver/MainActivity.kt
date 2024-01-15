package com.example.transportationproblemsolver

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("data_cash", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val nofsupp = findViewById<EditText>(R.id.nofsupp)
        val nofcons = findViewById<EditText>(R.id.nofcons)
        val btnnext = findViewById<Button>(R.id.btnnext)

        btnnext.setOnClickListener {
            if (nofsupp.text.isEmpty() || nofcons.text.isEmpty()){
                Toast.makeText(this,"You must fill all inputs",Toast.LENGTH_SHORT).show()
            } else {
                val supp = nofsupp.text.toString().toInt()
                val cons = nofcons.text.toString().toInt()

                if (supp>=2) {
                    if (cons>=2) {
                        editor.clear()
                        editor.putInt("supp",supp)
                        editor.putInt("cons",cons)
                        editor.apply()

                        val intent= Intent(this, AddValuesActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this,"Number of suppliers must be >=2",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this,"Number of consumers must be >=2",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}