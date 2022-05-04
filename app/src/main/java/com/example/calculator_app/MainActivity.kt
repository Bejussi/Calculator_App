package com.example.calculator_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

enum class CalculatorMode {
    None, Add, Subtract, Multiply
}
class MainActivity : AppCompatActivity() {

    var lastButtonWasMode = false
    var cuttentMode = CalculatorMode.None
    var labelString = ""
    var savedNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCalculator()
    }

    fun setupCalculator() {
        val allButtons = arrayOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)
        for (i in allButtons.indices) {
            allButtons[i].setOnClickListener { didPressNumber(i) }
        }
        buttonClear.setOnClickListener { didPressClear() }
        buttonEquals.setOnClickListener { didPressEquals() }
        buttonMinus.setOnClickListener { changeMode(CalculatorMode.Subtract) }
        buttonPlus.setOnClickListener { changeMode(CalculatorMode.Add) }
        buttonMultiply.setOnClickListener { changeMode(CalculatorMode.Multiply) }
    }

    fun didPressEquals() {
        if(lastButtonWasMode) {
            return
        }

        val labelInt = labelString.toInt()

        when(cuttentMode) {
            CalculatorMode.Add -> savedNum += labelInt
            CalculatorMode.Subtract -> savedNum -= labelInt
            CalculatorMode.Multiply -> savedNum *= labelInt
            CalculatorMode.None -> return
        }

        cuttentMode = CalculatorMode.None
        labelString = "$savedNum"
        updateText()
        lastButtonWasMode = true
    }

    fun didPressClear() {
        lastButtonWasMode = false
        cuttentMode = CalculatorMode.None
        labelString = ""
        savedNum = 0
        textView.text = "0"
    }

    fun updateText() {
        if(labelString.length > 8) {
            didPressClear()
            textView.text = "Too Big"
            return
        }

        val labelInt = labelString.toInt()
        labelString = labelInt.toString()

        if(cuttentMode == CalculatorMode.None) {
            savedNum = labelInt
        }

        val df = DecimalFormat("#,###")
        textView.text = df.format(labelString)
    }

    fun changeMode(mode: CalculatorMode) {
        if(savedNum == 0) {
            return
        }

        cuttentMode = mode
        lastButtonWasMode = true
    }

    fun didPressNumber(num: Int) {
        val stringValue = num.toString()

        if(lastButtonWasMode) {
            lastButtonWasMode = false
            labelString = "0"
        }

        labelString = "$labelString$stringValue"
        updateText()
    }
}