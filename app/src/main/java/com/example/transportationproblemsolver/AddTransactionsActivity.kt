package com.example.transportationproblemsolver

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputEditText

class AddTransactionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transactions)

        val valuesView = findViewById<LinearLayout>(R.id.valuesView)
        val btnClac = findViewById<Button>(R.id.btnClac)

        val sharedPreferences = getSharedPreferences("data_cash", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val suppCount = sharedPreferences.getInt("supp", 2)
        val consCount = sharedPreferences.getInt("cons", 2)

        repeat(suppCount) { suppIndex1 ->
            repeat(consCount) { suppIndex2 ->
                val suppTextInputLayout = createTextInputEditText("Transaction ${suppIndex1 + 1}${suppIndex2 + 1}")
                valuesView.addView(suppTextInputLayout)
            }
        }

        btnClac.setOnClickListener {
            for (i in 0 until valuesView.childCount) {
                val child = valuesView.getChildAt(i)
                if (child is TextInputEditText) {
                    val value = child.text.toString().toInt()
                    editor.putInt("t${i + 1}", value)
                }
            }
            editor.apply()
            val intent = Intent(this , ResultActivity::class.java)
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