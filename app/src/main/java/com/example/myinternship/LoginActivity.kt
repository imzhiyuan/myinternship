package com.example.myinternship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val Shared:Shared= Shared(this)
        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.settings.javaScriptEnabled = true

        val readid = Shared.getValueString("id") as String

        val url = "https://script.google.com/macros/s/AKfycbzHVvfi0NTe4cg18QqNcBsitSI2_Xzdp-XeJy7lZIax26T6WXe9/exec?"+"action=npisreadall"+"&NPISID="+ readid
        myWebView.loadUrl( url)

        Refresh.setOnClickListener {

            myWebView.loadUrl( url)

        }

        Detailed.setOnClickListener{

            val queue = Volley.newRequestQueue(this)
            val script = "https://script.google.com/macros/s/AKfycbzHVvfi0NTe4cg18QqNcBsitSI2_Xzdp-XeJy7lZIax26T6WXe9/exec?"+"action=getstudentlist"+"&NPISID="+readid

            val stringRequest = StringRequest(Request.Method.GET, script,
                Response.Listener<String> { response ->
                    Shared.save("stdlist",response)
                    startActivity(Intent(this,DetailedActivity::class.java))
                },
                Response.ErrorListener {  })
            queue.add(stringRequest)



        }
    }
}
