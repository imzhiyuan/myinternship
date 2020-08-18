package com.example.myinternship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_detailed.*

class DetailedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        lateinit var selected : String
        val Shared: Shared = Shared(this)

        val stdraw = Shared.getValueString("stdlist") as String
        val stdlist = stdraw.split(",")

        val spinnerlist = stdlist
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerlist)


        spinnerstdlist.adapter = arrayAdapter
        spinnerstdlist.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                webViewstd.settings.javaScriptEnabled = true
                Shared.save("stdid", spinnerlist[p2])
                val url = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?" + "action=studentdatacount" + "&STUDENTID=" + spinnerlist[p2]
                webViewstd.loadUrl(url)
                selected = spinnerlist[p2]

                val queue = Volley.newRequestQueue(baseContext)
                val script = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?"+"action=getreflectdata"+"&STUDENTID="+ spinnerlist[p2]

                val stringRequest = StringRequest(Request.Method.GET, script,
                    Response.Listener<String> { response ->
                        Shared.save("datelist",response)
                    },
                    Response.ErrorListener {
                        Toast.makeText(
                            applicationContext,
                            "error", Toast.LENGTH_SHORT)
                            .show()
                    })
                queue.add(stringRequest)

            }

        }
        readreflect.setOnClickListener {
            val queue = Volley.newRequestQueue(baseContext)
            val script = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?"+"action=getreflectdata"+"&STUDENTID="+ selected

            val stringRequest = StringRequest(Request.Method.GET, script,
                Response.Listener<String> { response ->
                    Shared.save("datelist",response)
                },
                Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Please enter all the blanks", Toast.LENGTH_SHORT)
                        .show()
                })
            queue.add(stringRequest)
            startActivity(Intent(this, ReadReflectActivity::class.java))
        }
        refreshbtn.setOnClickListener {
            val queue = Volley.newRequestQueue(baseContext)
            val script = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?"+"action=getreflectdata"+"&STUDENTID="+ selected

            val stringRequest = StringRequest(Request.Method.GET, script,
                Response.Listener<String> { response ->
                    Shared.save("datelist",response)
                },
                Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Please enter all the blanks", Toast.LENGTH_SHORT)
                        .show()
                })
            queue.add(stringRequest)
            val url = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?" + "action=studentdatacount" + "&STUDENTID=" + selected
            webViewstd.loadUrl(url)
        }
    }
}