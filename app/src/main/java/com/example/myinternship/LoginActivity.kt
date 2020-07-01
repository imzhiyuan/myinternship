package com.example.myinternship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var test = null

        val queue = Volley.newRequestQueue(this)
        val url = "https://script.google.com/macros/s/AKfycbyTqKi2dvNtM4G0mIE5_oya2_SO7gl5vUYEWxf16RkxJ5G-sNE/exec"

            val stringRequest = object: StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> {response ->
                    test = response
                },
                Response.ErrorListener {}) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["action"] = "testgraphs"

                    return params
                }
            }
            queue.add(stringRequest)



    }
}
