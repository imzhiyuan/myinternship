package com.example.myinternship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var idcheck : String?
        val test = 1234.toString() // need to repalce it with the register password and need to add the search for npisid on jg
        val loginid = findViewById<EditText>(R.id.LoginID)
        val loginpw = findViewById<EditText>(R.id.LoginPW)
        val txt = findViewById<TextView>(R.id.txtview)

        Login.setOnClickListener {
           val queue = Volley.newRequestQueue(this)
            val url = "https://script.google.com/macros/s/AKfycbxYiyQINZs7xWTGIcF_ZQOaVuBNT46dQN_Q8NQ23xecMsMJCT4i/exec"

            val stringRequest = object: StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> {response ->
                    // Display the first 500 characters of the response string.
                    idcheck = response.toString()
                    //txt2.text = idcheck (use for checking only)
                    //checking that pw and id if same login
                    if ( loginpw.text.toString() == test && idcheck == "Id found "  ) {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }else(
                            txt.setText("Wrong password or npisid ")
                            )
                },
               Response.ErrorListener { /*textView.text = "That didn't work!"*/ }) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["action"] = "search"
                    params["NPISID"] = loginid.text.toString()
                    return params
                }
            }
            queue.add(stringRequest)
        }
        SignUP.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }
}



