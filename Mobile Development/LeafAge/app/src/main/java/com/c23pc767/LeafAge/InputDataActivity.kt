package com.c23pc767.LeafAge


import android.os.Bundle
import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class InputDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)

        val button2 = findViewById<AppCompatButton>(R.id.button2)
        button2.setOnClickListener {
            val editText1 = findViewById<EditText>(R.id.editText1)
            val editText2 = findViewById<EditText>(R.id.editText2)

            val input1 = editText1.text.toString()
            val input2 = editText2.text.toString()

            makeApiRequest(input1, input2)
        }
    }

    private fun makeApiRequest(input1: String, input2: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val requestBody = """
                {
                    "heights": "$input1",
                    "circumferences": "$input2"
                }
            """.trimIndent()
                .toRequestBody("application/json".toMediaTypeOrNull())

            val request = Request.Builder()
                .url("http://10.0.2.2:8000") // API from CC is not available
                .post(requestBody)
                .build()

            var responseBody: String? = null

            try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        responseBody = response.body?.string()
                    } else {
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val intent = Intent(this@InputDataActivity, ResultActivity::class.java)
            intent.putExtra("response", responseBody)
            startActivity(intent)
        }
    }
}

