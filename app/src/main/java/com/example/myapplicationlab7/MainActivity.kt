package com.example.myapplicationlab7

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    var ed_height : EditText ?= null
    var ed_weight : EditText ?= null
    var btn_boy : RadioButton ?= null
    var tv_weight : TextView ?= null
    var tv_bmi : TextView ?= null
    var tv_progress : TextView ?= null
    var ll_progress : LinearLayout ?= null
    var progressBar2 : ProgressBar ?= null
    var p = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ed_height = findViewById(R.id.ed_height)
        ed_weight = findViewById(R.id.ed_weight)
        btn_boy = findViewById(R.id.btn_boy)
        tv_weight = findViewById(R.id.tv_weight)
        tv_bmi = findViewById(R.id.tv_bmi)
        tv_progress = findViewById(R.id.tv_progress)
        ll_progress = findViewById(R.id.ll_progress)
        progressBar2 = findViewById(R.id.progressBar2)
        findViewById<Button>(R.id.btn_calculate).setOnClickListener{
            if(ed_height!!.length() < 1){
                Toast.makeText(this@MainActivity, "請輸入身高", Toast.LENGTH_SHORT).show()
            }else if(ed_weight!!.length() < 1){
                Toast.makeText(this@MainActivity, "請輸入體重", Toast.LENGTH_SHORT).show()
            }else {
                ll_progress!!.visibility = View.VISIBLE
                GlobalScope.launch (Dispatchers.Main){
                    TaskA()
                }
            }
        }
    }

    private suspend fun TaskA(){

        p = 0
        while(p <= 100){
            delay(50)
            p ++
            val msg = Message()
            msg.what = 1
            mhandler.sendMessage(msg)
            progressBar2!!.progress = p
            tv_progress?.text = "$p%"
        }
    }


    private val mhandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg : Message){
            when(msg?.what){
                1 -> {
                    progressBar2!!.setProgress(p)
                    tv_progress!!.text = "$p%"
                }
            }
            if(p >= 100){
                ll_progress?.visibility = View.GONE
                var h = Integer.valueOf("${ ed_height!!.text }")
                var w = Integer.valueOf("${ ed_weight!!.text }")
                var standWeight : Double ?= 0.0
                var bodyFat : Double ?= 0.0
                if(btn_boy!!.isChecked){
                    standWeight = (h - 80) * 0.7
                    bodyFat = (w - 0.88 * standWeight) / w * 100
                }else{
                    standWeight = (h - 70) * 0.6
                    bodyFat = (w - 0.82 * standWeight) / w * 100
                }
                tv_weight!!.text = "標準體重 \n ${String.format("%.2f", standWeight)}"
                tv_bmi!!.text = "體脂肪 \n ${String.format("%.2f", bodyFat)}"
            }
        }
    }
}

