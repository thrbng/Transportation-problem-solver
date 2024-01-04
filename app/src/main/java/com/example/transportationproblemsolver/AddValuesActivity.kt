package com.example.transportationproblemsolver

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddValuesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_values)

        val valuesView = findViewById<LinearLayout>(R.id.valuesView)
        val btnnext = findViewById<Button>(R.id.btnnext)

        val sharedPreferences = getSharedPreferences("data_cash", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val suppCount = sharedPreferences.getInt("supp", 2)
        val consCount = sharedPreferences.getInt("cons", 2)

        repeat(suppCount) { suppIndex ->
            val suppTextInputLayout = createTextInputLayout("Supplier ${suppIndex + 1}", "s${suppIndex + 1}")
            valuesView.addView(suppTextInputLayout)
        }

        repeat(consCount) { consIndex ->
            val consTextInputLayout = createTextInputLayout("Consumer ${consIndex + 1}", "c${consIndex + 1}")
            valuesView.addView(consTextInputLayout)
        }

        val temp1: Int = suppCount
        val temp2: Int = consCount

        btnnext.setOnClickListener {
            for (i in 1..temp1) {
                val ss = findViewById<EditText>(resources.getIdentifier("s$i", "id", packageName))
                val value = ss.text.toString().toIntOrNull() ?: 0
                editor.putInt("s$i", value)
            }
            for (j in 1 .. temp2) {
                val cc = findViewById<EditText>(resources.getIdentifier("c$j", "id", packageName))
                val value = cc.text.toString().toIntOrNull() ?: 0
                editor.putInt("c$j", value)
            }
            editor.apply()
            val intent = Intent(this , AddTransactionsActivity::class.java)
            startActivity(intent)
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