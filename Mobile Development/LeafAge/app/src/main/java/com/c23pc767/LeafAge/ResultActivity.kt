package com.c23pc767.LeafAge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import org.json.JSONObject

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val responseData = intent.getStringExtra("response")

        if (responseData != null) {
            val jsonObject = JSONObject(responseData)
            val predictedAge = jsonObject.getString("predicted_age")
            val category = jsonObject.getString("category")

            val textViewResult3 = findViewById<TextView>(R.id.textView3)
            textViewResult3.text = predictedAge + " Years"

            val textViewResult5 = findViewById<TextView>(R.id.textView5)
            val textViewResult7 = findViewById<TextView>(R.id.textView7)

            when (category) {
                "This tree has an EXCELLENT ratio" -> textViewResult7.text = "EXCELLENT Ratio"
                "This tree has an IDEAL ratio" -> textViewResult7.text = "IDEAL Ratio"
                else -> textViewResult7.text = "BAD Ratio"
            }

            when (category) {
                "This tree has an EXCELLENT ratio" -> {textViewResult5.text = "SAFE"; textViewResult5.setTextColor(Color.GREEN)}
                "This tree has an IDEAL ratio" -> {textViewResult5.text = "SAFE"; textViewResult5.setTextColor(Color.GREEN)}
                else -> {textViewResult5.text = "DANGEROUS"; textViewResult5.setTextColor(Color.RED)}
            }

            val button2 = findViewById<AppCompatButton>(R.id.button2)
            button2.text = "Okay!"

            button2.setOnClickListener {
                val intent = Intent(this, ThanksActivity::class.java)
                startActivity(intent)
            }
        }
    }
}