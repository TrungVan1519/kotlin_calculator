package com.example.caculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val e = View.OnClickListener { v ->
            if (v.id == btnClear.id) {
                txtResult.text = ""
                lastNumeric = false
                lastDot = false
                return@OnClickListener
            }

            if (v.id == btnDecimal.id) {
                if (lastNumeric && !lastDot) {
                    txtResult.append(".")
                    lastNumeric = false
                    lastDot = true
                }
                return@OnClickListener
            }

            if (v.id == btnAdd.id || v.id == btnSubtract.id || v.id == btnMultiply.id || v.id == btnDivide.id) {
                if (lastNumeric && !isOperatorAdded(txtResult.text.toString())) {
                    txtResult.append((v as Button).text)
                    lastNumeric = false
                    lastDot = false
                }
                return@OnClickListener
            }

            if (v.id == btnEqual.id) {
                if (lastNumeric) {
                    var value = txtResult.text.toString()
                    var prefix = ""
                    var delimiter = ""
                    var result = ""

                    try {
                        if (value.startsWith("-")) {
                            prefix = "-"
                            value = value.substring(1) // ex: "-12 - 1" -> "12 - 1"
                        }

                        if (value.contains("+")) {
                            delimiter = "+"
                        } else if (value.contains("-")) {
                            delimiter = "-"
                        } else if (value.contains("*")) {
                            delimiter = "*"
                        } else if (value.contains("/")) {
                            delimiter = "/"
                        }

                        val splitValues = value.split(delimiter)
                        var one = splitValues[0]
                        val two = splitValues[1]

                        if (!prefix.isEmpty()) {
                            one = prefix + one
                        }

                        if (delimiter.contentEquals("+")) {
                            result = (one.toDouble() + two.toDouble()).toString()
                        } else if (delimiter.contentEquals("-")) {
                            result = (one.toDouble() - two.toDouble()).toString()
                        } else if (delimiter.contentEquals("*")) {
                            result = (one.toDouble() * two.toDouble()).toString()
                        } else if (delimiter.contentEquals("/")) {
                            result = (one.toDouble() / two.toDouble()).toString()
                        }

                        txtResult.text = result
                    } catch (ex: ArithmeticException) {
                        ex.printStackTrace()
                    }
                }

                return@OnClickListener
            }

            txtResult.append((v as Button).text)
            lastNumeric = true
        }

        btn0.setOnClickListener(e)
        btn1.setOnClickListener(e)
        btn2.setOnClickListener(e)
        btn3.setOnClickListener(e)
        btn4.setOnClickListener(e)
        btn5.setOnClickListener(e)
        btn6.setOnClickListener(e)
        btn7.setOnClickListener(e)
        btn8.setOnClickListener(e)
        btn9.setOnClickListener(e)
        btnDecimal.setOnClickListener(e)
        btnAdd.setOnClickListener(e)
        btnSubtract.setOnClickListener(e)
        btnMultiply.setOnClickListener(e)
        btnDivide.setOnClickListener(e)
        btnEqual.setOnClickListener(e)
        btnClear.setOnClickListener(e)
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) false
        else value.contains("+")
                || value.contains("-")
                || value.contains("*")
                || value.contentEquals("/")
    }
}
