package com.webkingve.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        //Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show()
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View){
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View){
        if (lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0] //9
                    var two = splitValue[1] //1

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0] //9
                    var two = splitValue[1] //1

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0] //9
                    var two = splitValue[1] //1

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }else if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0] //9
                    var two = splitValue[1] //1

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }else if (tvValue.contains("$")){
                    val splitValue = tvValue.split("$")
                    var one = splitValue[0] //9
                    var two = splitValue[1] //1

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    try {
                        var resultSimple = (one.toDouble() + two.toDouble()).toString().split(".")
                        Toast.makeText(this, resultSimple.toString(), Toast.LENGTH_LONG).show()

                        var resultOne = resultSimple[0].toInt()
                        var resultTwo = resultSimple[1].toInt()
                        var resultDecimal = ""

                        if (resultTwo >= 60){
                            resultOne += 1
                            resultTwo -= 60
                            if(resultTwo < 10){
                                resultDecimal = "0$resultTwo"
                            }else{
                                resultDecimal = resultTwo.toString()
                            }
                        }else if(resultTwo == 6){
                            resultOne += 1
                            resultDecimal = "0"
                        }else{
                            resultDecimal = resultSimple[1].toString()
                        }
                        tvInput?.text = "$resultOne.$resultDecimal"
                    }catch(e: Exception) {
                        tvInput?.text = ""
                        e.printStackTrace()
                    }
                }
            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2)

        return value
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-") || value.contains("$")
        }
    }
}