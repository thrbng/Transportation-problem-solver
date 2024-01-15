package com.example.transportationproblemsolver

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class AddValuesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_values)

        val valuesView = findViewById<LinearLayout>(R.id.valuesView)
        val btnNext = findViewById<Button>(R.id.btnNext)

        val sharedPreferences = getSharedPreferences("data_cash", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val suppCount = sharedPreferences.getInt("supp", 2)
        val consCount = sharedPreferences.getInt("cons", 2)

        repeat(suppCount) { suppIndex ->
            val suppEditText = createTextInputEditText("Supplier ${suppIndex + 1}")
            valuesView.addView(suppEditText)
        }

        repeat(consCount) { consIndex ->
            val consEditText = createTextInputEditText("Consumer ${consIndex + 1}")
            valuesView.addView(consEditText)
        }

        val temp1: Int = suppCount

        btnNext.setOnClickListener {
            for (i in 0 until valuesView.childCount) {
                val child = valuesView.getChildAt(i)
                if (child is TextInputEditText) {
                    if (i < temp1) {
                        val value = child.text.toString().toInt()
                        editor.putInt("s${i + 1}", value)
                    } else {
                        val value = child.text.toString().toInt()
                        editor.putInt("c${i - temp1 + 1}", value)
                    }
                }
            }
            editor.apply()
            val intent = Intent(this , AddTransactionsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createTextInputEditText(hint: String): EditText {
        val editText = TextInputEditText(this)

        editText.hint = hint
        editText.inputType = android.text.InputType.TYPE_CLASS_NUMBER

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.width = 300.dpToPx()
        layoutParams.topMargin = 20.dpToPx()
        editText.layoutParams = layoutParams

        return editText
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }
}