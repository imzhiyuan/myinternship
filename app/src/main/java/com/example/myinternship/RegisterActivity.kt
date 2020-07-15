package com.example.myinternship

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import java.io.File


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        lateinit var regcourse : String

        val regpw = findViewById<EditText>(R.id.RegPW)
        val regid = findViewById<EditText>(R.id.RegID)
        val regmail = findViewById<EditText>(R.id.RegMail)
        val mailpattern = Regex(pattern = "[a-zA-Z0-9]+@np.edu.sg")
        val tempfile = File.createTempFile("tempfilepw" ,null, cacheDir)

        //showing spinner items
        val spinner: Spinner = findViewById(R.id.RegCourse)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.course_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            // save spinner selected item
            spinner.onItemSelectedListener= object:
                AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    // An item was selected.
                    regcourse = parent.getItemAtPosition(pos).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }

        // sent data to sever when button pressed
        Register.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = "https://script.google.com/macros/s/AKfycbxYiyQINZs7xWTGIcF_ZQOaVuBNT46dQN_Q8NQ23xecMsMJCT4i/exec"

            if (regmail.text.toString().matches(mailpattern)) {
                txt3.text = regmail.text.toString()

                val stringRequest = object: StringRequest(
                    Request.Method.POST, url,
                    Response.Listener<String> {},
                    Response.ErrorListener {}) {
                    override fun getParams(): Map<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["action"] = "Register"
                        params["NPISID"] = regid.text.toString()
                        params["COURSE"] = regcourse
                        params["EMAIL"]  = regmail.text.toString()
                        return params
                    }
                }
                queue.add(stringRequest)
            } else {
                RegMail.setText("")
            }
    }
}
}
