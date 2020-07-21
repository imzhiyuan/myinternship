package com.example.myinternship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var idcheck : String?
        val Shared:Shared= Shared(this)
        val loginid = findViewById<EditText>(R.id.LoginID)
        val loginpw = findViewById<EditText>(R.id.LoginPW)
        val txt = findViewById<TextView>(R.id.txtview)

        Login.setOnClickListener {
           val queue = Volley.newRequestQueue(this)
            val url = "https://script.google.com/macros/s/AKfycbzHVvfi0NTe4cg18QqNcBsitSI2_Xzdp-XeJy7lZIax26T6WXe9/exec"

            val stringRequest = object: StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> {response ->
                    // Display the first 500 characters of the response string.
                    idcheck = response.toString()
                    //txt2.text = idcheck (use for checking only)
                    //checking that pw and id if same login
                    if ( loginpw.text.toString() == Shared.getValueString("pw") && idcheck == "Id found " && loginid.text.toString() == Shared.getValueString("id")  ) {
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
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    Toast.makeText(applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        val biometricLoginButton =
            findViewById<Button>(R.id.biologin)
        biometricLoginButton.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
}

}



