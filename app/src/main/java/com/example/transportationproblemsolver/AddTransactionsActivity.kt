package com.example.transportationproblemsolver

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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

        repeat(suppCount ) { suppIndex1 ->
            repeat(consCount) { suppIndex2 ->
                val suppTextInputLayout = createTextInputLayout("Transaction ${suppIndex1 + 1}${suppIndex2 + 1}", "t${(suppIndex1 + 1)*(suppIndex2 + 1)}")
                valuesView.addView(suppTextInputLayout)
            }
        }

        val temp: Int = suppCount * consCount

        btnClac.setOnClickListener {
            for (i in 1..temp) {
                val t = findViewById<EditText>(resources.getIdentifier("t$i", "id", packageName))
                val value = t.text.toString().toIntOrNull() ?: 0
                editor.putInt("t$i", value)
            }
            editor.apply()
        }
    }

    private fun createTextInputLayout(hint: String, viewId: String): TextInputLayout {
        val textInputLayout = TextInputLayout(this)
        val textInputEditText = TextInputEditText(this)

        textInputEditText.hint = hint
        textInputEditText.inputType = android.text.InputType.TYPE_CLASS_NUMBER

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.width = 300.dpToPx()
        layoutParams.topMargin = 20.dpToPx()
        textInputLayout.layoutParams = layoutParams

        textInputEditText.id = resources.getIdentifier(viewId, "id", packageName)
        textInputLayout.addView(textInputEditText)

        return textInputLayout
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }
}