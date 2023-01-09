package com.example.tippy

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import kotlin.properties.Delegates

private const val TAG = "MainActivity"
private var INITIAL_TIP  = 15
class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount : EditText
    private lateinit var tv_bar : TextView
    private lateinit var seekBar: SeekBar
    private lateinit var tvTipAmount : TextView
    private lateinit var tvTotalAmount : TextView
    private lateinit var barText :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBaseAmount = findViewById(R.id.etBaseAmount)
        tv_bar = findViewById(R.id.tv_bar)
        seekBar = findViewById(R.id.seekBar)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        barText = findViewById(R.id.barText)

        seekBar.progress = INITIAL_TIP
        tv_bar.text = "$INITIAL_TIP%"
        barText.setTextColor(Color.parseColor("#FF812F"))
        seekBar.setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //var temp = tv_bar.text
                tv_bar.text = "$progress%"
                Log.i(TAG , "seekBar = $progress")
                if (etBaseAmount.text.isNotEmpty()) {
                    computeFinal()
                }
                var percentage = progress
                var barDis =  when(percentage){
                    in 0..12 -> "poor"
                    in 12..15 -> "Acceptable"
                    in 15..20 -> "Good"
                    in 20..23 -> "Great"
                    else -> {"Amazing"}
                }
                barText.text = "$barDis"

                var barColor = when(barDis){
                    "poor" -> "#E30902"
                    "Acceptable" -> "#FF812F"
                    "Good" ->  "#A4FF1E"
                    "Great" ->  "#97FF22"
                    "Amazing" -> "#26FA3B"
                    else -> {}
                }

                barText.setTextColor(Color.parseColor("$barColor"))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        etBaseAmount.addTextChangedListener(object  : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (etBaseAmount.text.isEmpty()){
                    tvTipAmount.text = ""
                    tvTotalAmount.text = ""
                    return
                }
                var baseAmount  = etBaseAmount.text.toString().toDouble()
                Log.i(TAG , "the bill base = $baseAmount")
                computeFinal()

            }

        })
    }

    fun computeFinal (){
        var totalBase = etBaseAmount.text.toString().toDouble()
        var tipAmount =  ((totalBase* seekBar.progress) / 100)
        var total = tipAmount + totalBase
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text ="%.2f".format( total)
    }


}