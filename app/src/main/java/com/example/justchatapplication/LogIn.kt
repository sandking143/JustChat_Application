package com.example.justchatapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LogIn : AppCompatActivity() {

    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnLogIn : Button
    private lateinit var btnSignUp : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var checkBox: CheckBox
    private lateinit var strName: String
    private lateinit var strPassword: String
    private lateinit var strCheckBox: String

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()



        supportActionBar?.hide()


        mAuth = FirebaseAuth.getInstance()


        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogIn = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        checkBox = findViewById(R.id.checkBox)


        btnSignUp.setOnClickListener {

            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        btnLogIn.setOnClickListener {

            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()


            if (checkBox.isChecked){
                editor.putString("check", "True")
                strName = edtEmail.text.toString()
                editor.putString(getString(R.string.edtEmail), strName)
                strPassword = edtPassword.text.toString()
                editor.putString("password", strPassword)
                editor.apply()

            }else {
                editor.putString(getString(R.string.checkBox), "False")
                editor.putString(getString(R.string.edtEmail), "")
                editor.putString(getString(R.string.edtPassword), "")
                editor.apply()

            }


            login(email,password)


        }
        checkSharedPreference()
    }

    private fun login(email: String, password: String) {

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {


                    val intent = Intent(this@LogIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@LogIn, "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }

    }
    private fun checkSharedPreference() {
            strCheckBox = sharedPreferences.getString("check", "False").toString()
            strName = sharedPreferences.getString(getString(R.string.edtEmail), "").toString()
            strPassword = sharedPreferences.getString("password", "").toString()
            edtEmail.setText(strName)
            edtPassword.setText(strPassword)
            checkBox.isChecked = strCheckBox == "True"


    }
}