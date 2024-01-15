package com.example.transportationproblemsolver

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        val suppliers = getSuppValues()
        val consumers = getConsValues()
        val transactions = getTransValues()

        val allocations = solveTransportationProblem(suppliers, consumers)

        for (i in allocations.indices) {
            val row = layoutInflater.inflate(R.layout.row_item, null) as TableRow
            val supplierTextView: TextView = row.findViewById(R.id.supplierTextView)
            supplierTextView.text = "Supplier ${i + 1}"

            tableLayout.addView(row)

            for (j in 0 until allocations[i].size) {
                val consumerTextView = TextView(this)
                consumerTextView.text = "${allocations[i][j]} units"
                row.addView(consumerTextView)
            }
        }
    }

    private fun getSuppValues(): IntArray {
        val sharedPreferences = getSharedPreferences("data_cash", Context.MODE_PRIVATE)
        val suppValues = mutableListOf<Int>()

        var i = 1
        while (sharedPreferences.contains("s$i")) {
            suppValues.add(sharedPreferences.getInt("s$i", 0))
            i++
        }

        return suppValues.toIntArray()
    }

    private fun getConsValues(): IntArray {
        val sharedPreferences = getSharedPreferences("data_cash", Context.MODE_PRIVATE)
        val consValues = mutableListOf<Int>()

        var i = 1
        while (sharedPreferences.contains("c$i")) {
            consValues.add(sharedPreferences.getInt("c$i", 0))
            i++
        }

        return consValues.toIntArray()
    }

    private fun getTransValues(): IntArray {
        val sharedPreferences = getSharedPreferences("data_cash", Context.MODE_PRIVATE)
        val transValues = mutableListOf<Int>()

        var i = 1
        while (sharedPreferences.contains("t$i")) {
            transValues.add(sharedPreferences.getInt("t$i", 0))
            i++
        }

        return transValues.toIntArray()
    }

    private fun solveTransportationProblem(supply: IntArray, demand: IntArray): Array<IntArray> {
        val m = supply.size
        val n = demand.size
        val allocations = Array(m) { IntArray(n) }

        var i = 0
        var j = 0

        while (i < m && j < n) {
            val quantity = minOf(supply[i], demand[j])
            allocations[i][j] = quantity
            supply[i] -= quantity
            demand[j] -= quantity

            if (supply[i] == 0) {
                i++
            }

            if (demand[j] == 0) {
                j++
            }
        }

        return allocations
    }

    private fun calculateTotalCost(allocations: Array<IntArray>, costs: Array<IntArray>): Int {
        var totalCost = 0

        for (i in allocations.indices) {
            for (j in 0..<allocations[i].size) {
                totalCost += allocations[i][j] * costs[i][j]
            }
        }

        return totalCost
    }
}